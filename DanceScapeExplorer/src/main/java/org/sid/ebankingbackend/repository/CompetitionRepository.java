package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface CompetitionRepository  extends JpaRepository<Competition,Long> {
    @Query("SELECT c.compname, t.teamname, t.dancers FROM Competition c JOIN c.teams t WHERE c.idcomp = :competitionId")
    List<Map<String, Object>> getCompetitionTeamsAndDancers(Long competitionId);

    @Query("SELECT c FROM Competition c LEFT JOIN FETCH c.performances")
    List<Competition> findAllWithPerformances();

    @Query("SELECT c.venue.venuePlan.idPlan FROM Competition c WHERE c.idcomp = :competitionId")
    Optional<Long> findVenuePlanIdByCompetitionId(@Param("competitionId") Long competitionId);
}
