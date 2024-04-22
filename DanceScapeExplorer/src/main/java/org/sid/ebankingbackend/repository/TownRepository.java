package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TownRepository extends JpaRepository<Town,Long> {
    @Query("SELECT DISTINCT t FROM Town t LEFT JOIN FETCH t.venues")
    List<Town> findAlltownsWithVenues();



}
