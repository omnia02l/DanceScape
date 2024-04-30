package org.sid.ebankingbackend.services.Store;


import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Review;
import org.sid.ebankingbackend.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ReviewServiceImp implements IReviewService{
    @Autowired
    private ReviewRepo reviewRepo;

    @Override
    public Review getReviewById(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepo.findById(reviewId);
        return reviewOptional.orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepo.findAll();
    }

    @Override
    public Review addReview(Review review) {
        try {
            return reviewRepo.save(review);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add review: " + e.getMessage());
        }
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepo.deleteById(reviewId);
    }
}