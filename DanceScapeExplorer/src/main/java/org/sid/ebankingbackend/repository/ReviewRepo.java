package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review,Long> {
}
