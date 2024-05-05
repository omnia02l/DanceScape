package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {
}
