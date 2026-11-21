package io.dajzavrsi.dajzavrsi.review;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findBySubmissionId(UUID submissionId);

    List<Review> findByReviewerId(UUID reviewerId);

    boolean existsBySubmissionIdAndReviewerId(UUID submissionId, UUID reviewerId);
}
