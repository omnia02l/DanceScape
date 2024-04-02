package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Map;


public interface CompetitionRepository  extends JpaRepository<Competition,Long> {
    @Query("SELECT c.compname, t.teamname, t.dancers FROM Competition c JOIN c.teams t WHERE c.idcomp = :competitionId")
    List<Map<String, Object>> getCompetitionTeamsAndDancers(Long competitionId);
}
