package io.dajzavrsi.dajzavrsi.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySubmissionId(Long submissionId);

    List<Review> findByReviewerId(Long reviewerId);

    boolean existsBySubmissionIdAndReviewerId(Long submissionId, Long reviewerId);
}
