package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Dancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface DancerRepository extends JpaRepository<Dancer,Long> {
    @Query("SELECT COUNT(d) FROM Dancer d JOIN d.team t JOIN t.competitions c WHERE c.idcomp = :competitionId AND d.dgender = 'man'")
    int countMaleDancersInTeamsInCompetition(@Param("competitionId") Long competitionId);

    @Query("SELECT COUNT(d) FROM Dancer d JOIN d.team t JOIN t.competitions c WHERE c.idcomp = :competitionId AND d.dgender = 'women'")
    int countFemaleDancersInTeamsInCompetition(@Param("competitionId") Long competitionId);

}
