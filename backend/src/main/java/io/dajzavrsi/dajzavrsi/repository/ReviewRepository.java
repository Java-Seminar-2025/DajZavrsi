package io.dajzavrsi.dajzavrsi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import io.dajzavrsi.dajzavrsi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findBySubmissionId(Integer submissionId);

    List<Review> findByReviewerId(Integer reviewerId);

    boolean existsBySubmissionIdAndReviewerId(Integer submissionId, Integer reviewerId);
}
