package io.dajzavrsi.dajzavrsi.submission;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByAssignmentIdOrderByVersionDesc(Long assignmentId);

    Optional<Submission> findFirstByAssignmentIdOrderByVersionDesc(Long assignmentId);

    List<Submission> findByReviewStatus(SubmissionStatus reviewStatus);
}
