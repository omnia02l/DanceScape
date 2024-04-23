package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Dancestyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface DancestyleRepository extends JpaRepository<Dancestyle,Long> {

    @Query("SELECT distinct d.styledname FROM Dancestyle d")
    List<String> findAllDistinctStyledNames();
}
