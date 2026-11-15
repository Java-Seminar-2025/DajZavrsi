package io.dajzavrsi.dajzavrsi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import io.dajzavrsi.dajzavrsi.entity.Submission;
import io.dajzavrsi.dajzavrsi.entity.SubmissionStatus;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByAssignmentIdOrderByVersionDesc(Long assignmentId);

    Optional<Submission> findFirstByAssignmentIdOrderByVersionDesc(Long assignmentId);

    List<Submission> findByReviewStatus(SubmissionStatus reviewStatus);
}
