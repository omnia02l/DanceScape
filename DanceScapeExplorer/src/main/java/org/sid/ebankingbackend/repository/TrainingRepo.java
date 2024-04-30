package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepo extends JpaRepository<Training, Long> {
    @Query(value = "select COUNT(*) from training",nativeQuery = true)
    Long getTotal();
    @Query(value = "select COUNT(*) from training where capacity = 0",nativeQuery = true)
    Long getFull();
    @Query(value = "select COUNT(*) from training where capacity !=0",nativeQuery = true)
    Long getAvailable();

    @Query(value = "select COUNT(*) from training where training_category =?1",nativeQuery = true)
    Long getStatsWithCateg(String c);
}
