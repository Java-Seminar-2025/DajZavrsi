package io.dajzavrsi.dajzavrsi.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Autowired
    private UserRepository users;

    @Autowired
    private AssignmentRepository assignments;

    @Autowired
    private SubmissionRepository submissions;

    @Autowired
    private ReviewRepository reviews;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void savesAndLoadsUser() {
        byte[] password = passwordHash();
        users.save(new User("ana", password, Role.STUDENT));
        flushAndClear();

        User loaded = users.findByUsername("ana").orElseThrow();

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
        User student = users.save(user("student", Role.STUDENT));
        User mentor = users.save(user("mentor", Role.FACULTY));
        User member = users.save(user("member", Role.FACULTY));
        Assignment assignment = new Assignment(student, mentor, "Thesis validation", AssignmentStatus.IN_PROGRESS);
        assignment.setCommittee1(member);
        Integer id = assignments.save(assignment).getId();
        flushAndClear();

        Assignment loaded = assignments.findById(id).orElseThrow();

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
        User studentA = users.save(user("student-a", Role.STUDENT));
        User studentB = users.save(user("student-b", Role.STUDENT));
        User mentorX = users.save(user("mentor-x", Role.FACULTY));
        User mentorY = users.save(user("mentor-y", Role.FACULTY));
        User uninvolved = users.save(user("uninvolved", Role.FACULTY));
        Integer aWithX = assignments.save(new Assignment(studentA, mentorX, "A-X", AssignmentStatus.IN_PROGRESS)).getId();
        Integer aWithY = assignments.save(new Assignment(studentA, mentorY, "A-Y", AssignmentStatus.IN_PROGRESS)).getId();
        Integer bWithX = assignments.save(new Assignment(studentB, mentorX, "B-X", AssignmentStatus.IN_PROGRESS)).getId();
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

    @ParameterizedTest
    @CsvSource({ "STUDENT, student", "FACULTY, faculty" })
    void mapsRoleToDatabaseValue(Role role, String dbValue) {
        Integer id = users.save(user("someone", role)).getId();
        flushAndClear();

        assertThat(rawValue("SELECT `role` FROM `user` WHERE `user_id` = ?", id)).isEqualTo(dbValue);
        assertThat(users.findById(id).orElseThrow().getRole()).isEqualTo(role);
    }

    @ParameterizedTest
    @CsvSource({ "IN_PROGRESS, in_progress", "PASSED, passed", "FAILED, failed" })
    void mapsAssignmentStatusToDatabaseValue(AssignmentStatus status, String dbValue) {
        Assignment assignment = assignment();
        assignment.setStatus(status);
        Integer id = assignment.getId();
        flushAndClear();

        assertThat(rawValue("SELECT `status` FROM `assignment` WHERE `assignment_id` = ?", id)).isEqualTo(dbValue);
        assertThat(assignments.findById(id).orElseThrow().getStatus()).isEqualTo(status);
    }

    @ParameterizedTest
    @CsvSource({
            "PENDING_VALIDATION, pending_validation",
            "PENDING_REVIEW, pending_review",
            "APPROVED, approved",
            "DENIED, denied"
    })
    void mapsSubmissionStatusToDatabaseValue(SubmissionStatus status, String dbValue) {
        Integer id = submissions.save(new Submission(assignment(), 1, "doc-1", status)).getId();
        flushAndClear();

        assertThat(rawValue("SELECT `review_status` FROM `submission` WHERE `submission_id` = ?", id))
                .isEqualTo(dbValue);
        assertThat(submissions.findById(id).orElseThrow().getReviewStatus()).isEqualTo(status);
        assertThat(submissions.findByReviewStatus(status)).extracting(Submission::getId).containsExactly(id);
    }

    @ParameterizedTest
    @CsvSource({ "APPROVED, approved", "DENIED, denied" })
    void mapsReviewStatusToDatabaseValue(ReviewStatus status, String dbValue) {
        Assignment assignment = assignment();
        Submission submission = submissions.save(
                new Submission(assignment, 1, "doc-1", SubmissionStatus.PENDING_REVIEW));
        Integer id = reviews.save(new Review(submission, assignment.getMentor(), status, null)).getId();
        flushAndClear();

        assertThat(rawValue("SELECT `status` FROM `review` WHERE `review_id` = ?", id)).isEqualTo(dbValue);
        assertThat(reviews.findById(id).orElseThrow().getStatus()).isEqualTo(status);
    }

    @Test
    void findsAssignmentsByEitherCommitteeSlot() {
        User student = users.save(user("student", Role.STUDENT));
        User mentor = users.save(user("mentor", Role.FACULTY));
        User member = users.save(user("member", Role.FACULTY));
        Assignment first = new Assignment(student, mentor, "First", AssignmentStatus.IN_PROGRESS);
        first.setCommittee1(member);
        Assignment second = new Assignment(student, mentor, "Second", AssignmentStatus.IN_PROGRESS);
        second.setCommittee2(member);
        assignments.save(first);
        assignments.save(second);
        assignments.save(new Assignment(student, mentor, "Unrelated", AssignmentStatus.IN_PROGRESS));
        flushAndClear();

        assertThat(assignments.findByCommitteeMemberId(member.getId()))
                .extracting(Assignment::getSubject)
                .containsExactlyInAnyOrder("First", "Second");
    }

    @Test
    void returnsSubmissionVersionsNewestFirst() {
        Assignment assignment = assignment();
        submissions.save(new Submission(assignment, 1, "doc-1", SubmissionStatus.DENIED));
        submissions.save(new Submission(assignment, 3, "doc-3", SubmissionStatus.PENDING_VALIDATION));
        submissions.save(new Submission(assignment, 2, "doc-2", SubmissionStatus.DENIED));
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
        Assignment assignment = assignment();
        Submission submission = submissions.save(
                new Submission(assignment, 1, "doc-1", SubmissionStatus.PENDING_REVIEW));
        User mentor = assignment.getMentor();
        User otherReviewer = users.save(user("other", Role.FACULTY));
        reviews.save(new Review(submission, mentor, ReviewStatus.DENIED, "Fix the references."));
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
        Assignment assignment = assignment();
        Submission first = submissions.save(new Submission(assignment, 1, "doc-1", SubmissionStatus.DENIED));
        Submission second = submissions.save(new Submission(assignment, 2, "doc-2", SubmissionStatus.PENDING_REVIEW));
        User mentor = assignment.getMentor();
        User member = users.save(user("member", Role.FACULTY));
        Integer mentorOnFirst = reviews.save(new Review(first, mentor, ReviewStatus.DENIED, null)).getId();
        Integer mentorOnSecond = reviews.save(new Review(second, mentor, ReviewStatus.APPROVED, null)).getId();
        Integer memberOnFirst = reviews.save(new Review(first, member, ReviewStatus.APPROVED, null)).getId();
        flushAndClear();

        assertThat(reviews.findByReviewerId(mentor.getId())).extracting(Review::getId)
                .containsExactlyInAnyOrder(mentorOnFirst, mentorOnSecond);
        assertThat(reviews.findByReviewerId(member.getId())).extracting(Review::getId)
                .containsExactly(memberOnFirst);
    }

    private Assignment assignment() {
        User student = users.save(user("student", Role.STUDENT));
        User mentor = users.save(user("mentor", Role.FACULTY));
        return assignments.save(new Assignment(student, mentor, "Subject", AssignmentStatus.IN_PROGRESS));
    }

    private static User user(String username, Role role) {
        return new User(username, passwordHash(), role);
    }

    private static byte[] passwordHash() {
        byte[] hash = new byte[64];
        Arrays.fill(hash, (byte) 7);
        return hash;
    }

    private String rawValue(String sql, Integer id) {
        return jdbc.queryForObject(sql, String.class, id);
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
