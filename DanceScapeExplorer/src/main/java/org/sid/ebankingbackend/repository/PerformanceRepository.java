package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Performance;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PerformanceRepository extends JpaRepository<Performance,Long> {
}
