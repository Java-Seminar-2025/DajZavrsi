package io.dajzavrsi.dajzavrsi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import io.dajzavrsi.dajzavrsi.entity.Assignment;
import io.dajzavrsi.dajzavrsi.entity.AssignmentStatus;
import io.dajzavrsi.dajzavrsi.entity.Review;
import io.dajzavrsi.dajzavrsi.entity.ReviewStatus;
import io.dajzavrsi.dajzavrsi.entity.Role;
import io.dajzavrsi.dajzavrsi.entity.Submission;
import io.dajzavrsi.dajzavrsi.entity.SubmissionStatus;
import io.dajzavrsi.dajzavrsi.entity.User;
import jakarta.persistence.EntityManager;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:persistence-tests;MODE=MySQL;DATABASE_TO_LOWER=TRUE",
        "spring.jpa.hibernate.ddl-auto=none"
})
@ActiveProfiles("test")
@Transactional
class PersistenceIntegrationTests {
    @Autowired private UserRepository users;
    @Autowired private AssignmentRepository assignments;
    @Autowired private SubmissionRepository submissions;
    @Autowired private ReviewRepository reviews;
    @Autowired private EntityManager entityManager;
    @Autowired private JdbcTemplate jdbc;

    @Test
    void savesAndLoadsUser() {
        var password = passwordHash();
        users.save(new User(null, "ana", password, Role.STUDENT));
        flushAndClear();

        var loaded = users.findByUsername("ana").orElseThrow();

        assertThat(loaded.getId()).isNotNull();
        assertThat(loaded.getPassword()).isEqualTo(password);
        assertThat(loaded.getRole()).isEqualTo(Role.STUDENT);
        assertThat(users.existsByUsername("ana")).isTrue();
        assertThat(users.existsByUsername("nobody")).isFalse();
    }

    @Test
    void rejectsDuplicateUsername() {
        users.saveAndFlush(user("ana", Role.STUDENT));

        assertThatThrownBy(() -> users.saveAndFlush(user("ana", Role.FACULTY)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void savesAssignmentWithRelationshipsAndOptionalCommittee() {
        var student = users.save(user("student", Role.STUDENT));
        var mentor = users.save(user("mentor", Role.FACULTY));
        var member = users.save(user("member", Role.FACULTY));
        var assignment = new Assignment(null, student, mentor, member, null, "Thesis validation", null, AssignmentStatus.IN_PROGRESS);
        var id = assignments.save(assignment).getId();
        flushAndClear();

        var loaded = assignments.findById(id).orElseThrow();

        assertThat(loaded.getStudent().getUsername()).isEqualTo("student");
        assertThat(loaded.getMentor().getUsername()).isEqualTo("mentor");
        assertThat(loaded.getCommittee1().getUsername()).isEqualTo("member");
        assertThat(loaded.getCommittee2()).isNull();
        assertThat(loaded.getStatus()).isEqualTo(AssignmentStatus.IN_PROGRESS);
        assertThat(assignments.findByStudentId(student.getId())).extracting(Assignment::getId).containsExactly(id);
        assertThat(assignments.findByMentorId(mentor.getId())).extracting(Assignment::getId).containsExactly(id);
    }

    @Test
    void findsAssignmentsByStudentAndMentor() {
        var studentA = users.save(user("student-a", Role.STUDENT));
        var studentB = users.save(user("student-b", Role.STUDENT));
        var mentorX = users.save(user("mentor-x", Role.FACULTY));
        var mentorY = users.save(user("mentor-y", Role.FACULTY));
        var uninvolved = users.save(user("uninvolved", Role.FACULTY));
        var aWithX = assignments.save(new Assignment(null, studentA, mentorX, null, null, "A-X", null, AssignmentStatus.IN_PROGRESS)).getId();
        var aWithY = assignments.save(new Assignment(null, studentA, mentorY, null, null, "A-Y", null, AssignmentStatus.IN_PROGRESS)).getId();
        var bWithX = assignments.save(new Assignment(null, studentB, mentorX, null, null, "B-X", null, AssignmentStatus.IN_PROGRESS)).getId();
        flushAndClear();

        assertThat(assignments.findByStudentId(studentA.getId())).extracting(Assignment::getId)
                .containsExactlyInAnyOrder(aWithX, aWithY);
        assertThat(assignments.findByStudentId(studentB.getId())).extracting(Assignment::getId)
                .containsExactly(bWithX);
        assertThat(assignments.findByStudentId(uninvolved.getId())).isEmpty();

        assertThat(assignments.findByMentorId(mentorX.getId())).extracting(Assignment::getId)
                .containsExactlyInAnyOrder(aWithX, bWithX);
        assertThat(assignments.findByMentorId(mentorY.getId())).extracting(Assignment::getId)
                .containsExactly(aWithY);
        assertThat(assignments.findByMentorId(uninvolved.getId())).isEmpty();
    }

    @Test
    void findsAssignmentsByEitherCommitteeSlot() {
        var student = users.save(user("student", Role.STUDENT));
        var mentor = users.save(user("mentor", Role.FACULTY));
        var member = users.save(user("member", Role.FACULTY));
        var first = new Assignment(null, student, mentor, member, null, "First", null, AssignmentStatus.IN_PROGRESS);
        var second = new Assignment(null, student, mentor, null, member, "Second", null, AssignmentStatus.IN_PROGRESS);
        assignments.save(first);
        assignments.save(second);
        assignments.save(new Assignment(null, student, mentor, null, null, "Unrelated", null, AssignmentStatus.IN_PROGRESS));
        flushAndClear();

        assertThat(assignments.findByCommitteeMemberId(member.getId()))
                .extracting(Assignment::getSubject)
                .containsExactlyInAnyOrder("First", "Second");
    }

    @Test
    void returnsSubmissionVersionsNewestFirst() {
        var assignment = assignment();
        submissions.save(new Submission(null, assignment, 1, "doc-1", null, SubmissionStatus.DENIED));
        submissions.save(new Submission(null, assignment, 3, "doc-3", null, SubmissionStatus.PENDING_VALIDATION));
        submissions.save(new Submission(null, assignment, 2, "doc-2", null, SubmissionStatus.DENIED));
        flushAndClear();

        assertThat(submissions.findByAssignmentIdOrderByVersionDesc(assignment.getId()))
                .extracting(Submission::getVersion)
                .containsExactly(3, 2, 1);
        assertThat(submissions.findFirstByAssignmentIdOrderByVersionDesc(assignment.getId()))
                .get()
                .extracting(Submission::getDocumentUrl)
                .isEqualTo("doc-3");
        assertThat(submissions.findByReviewStatus(SubmissionStatus.PENDING_VALIDATION))
                .extracting(Submission::getVersion)
                .containsExactly(3);
    }

    @Test
    void returnsNoLatestSubmissionWhenAssignmentHasNone() {
        Assignment assignment = assignment();
        flushAndClear();

        assertThat(submissions.findFirstByAssignmentIdOrderByVersionDesc(assignment.getId())).isEmpty();
    }

    @Test
    void savesReviewsAndFindsThemBySubmissionAndReviewer() {
        var assignment = assignment();
        var submission = submissions.save(
                new Submission(null, assignment, 1, "doc-1", null, SubmissionStatus.PENDING_REVIEW));
        var mentor = assignment.getMentor();
        var otherReviewer = users.save(user("other", Role.FACULTY));
        reviews.save(new Review(null, submission, mentor, ReviewStatus.DENIED, "Fix the references."));
        flushAndClear();

        assertThat(reviews.findBySubmissionId(submission.getId()))
                .singleElement()
                .satisfies(review -> {
                    assertThat(review.getStatus()).isEqualTo(ReviewStatus.DENIED);
                    assertThat(review.getNotes()).isEqualTo("Fix the references.");
                    assertThat(review.getReviewer().getUsername()).isEqualTo("mentor");
                });
        assertThat(reviews.findByReviewerId(mentor.getId())).hasSize(1);
        assertThat(reviews.existsBySubmissionIdAndReviewerId(submission.getId(), mentor.getId())).isTrue();
        assertThat(reviews.existsBySubmissionIdAndReviewerId(submission.getId(), otherReviewer.getId())).isFalse();
    }

    @Test
    void findsReviewsByReviewer() {
        var assignment = assignment();
        var first = submissions.save(new Submission(null, assignment, 1, "doc-1", null, SubmissionStatus.DENIED));
        var second = submissions.save(new Submission(null, assignment, 2, "doc-2", null, SubmissionStatus.PENDING_REVIEW));
        var mentor = assignment.getMentor();
        var member = users.save(user("member", Role.FACULTY));
        var mentorOnFirst = reviews.save(new Review(null, first, mentor, ReviewStatus.DENIED, null)).getId();
        var mentorOnSecond = reviews.save(new Review(null, second, mentor, ReviewStatus.APPROVED, null)).getId();
        var memberOnFirst = reviews.save(new Review(null, first, member, ReviewStatus.APPROVED, null)).getId();
        flushAndClear();

        assertThat(reviews.findByReviewerId(mentor.getId())).extracting(Review::getId)
                .containsExactlyInAnyOrder(mentorOnFirst, mentorOnSecond);
        assertThat(reviews.findByReviewerId(member.getId())).extracting(Review::getId)
                .containsExactly(memberOnFirst);
    }

    private Assignment assignment() {
        var student = users.save(user("student", Role.STUDENT));
        var mentor = users.save(user("mentor", Role.FACULTY));
        return assignments.save(new Assignment(null, student, mentor, null, null, "Subject", null, AssignmentStatus.IN_PROGRESS));
    }

    private User user(String username, Role role) {
        return new User(null, username, passwordHash(), role);
    }

    private String passwordHash() {
        return "passwordhash";
    }

    private String rawValue(String sql, Integer id) {
        return jdbc.queryForObject(sql, String.class, id);
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
