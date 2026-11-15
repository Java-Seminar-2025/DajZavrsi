package io.dajzavrsi.dajzavrsi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import io.dajzavrsi.dajzavrsi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySubmissionId(Long submissionId);

    List<Review> findByReviewerId(Long reviewerId);

    boolean existsBySubmissionIdAndReviewerId(Long submissionId, Long reviewerId);
}
