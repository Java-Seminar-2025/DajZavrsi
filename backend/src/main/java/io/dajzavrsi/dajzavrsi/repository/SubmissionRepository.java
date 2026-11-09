package io.dajzavrsi.dajzavrsi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import io.dajzavrsi.dajzavrsi.entity.Submission;
import io.dajzavrsi.dajzavrsi.entity.SubmissionStatus;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    List<Submission> findByAssignmentIdOrderByVersionDesc(Integer assignmentId);

    Optional<Submission> findFirstByAssignmentIdOrderByVersionDesc(Integer assignmentId);

    List<Submission> findByReviewStatus(SubmissionStatus reviewStatus);
}
