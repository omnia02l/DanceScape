package org.sid.ebankingbackend.services.Store;


import org.sid.ebankingbackend.entities.Review;

import java.util.List;

public interface IReviewService {

    Review getReviewById(Long reviewId);
    List<Review> getAllReviews();
    Review addReview(Review review);
    void deleteReview(Long reviewId);
}
