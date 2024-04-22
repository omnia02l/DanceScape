package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Map;


public interface TeamRepository extends JpaRepository<Team,Long> {


    @Query(value = "SELECT t.*, d.*, c.* FROM team t " +
            "LEFT JOIN dancer d ON t.idteam = d.team_id " +
            "LEFT JOIN competition_teams ct ON t.idteam = ct.teams_idteam " +
            "LEFT JOIN competition c ON ct.competitions_idcomp = c.idcomp",
            nativeQuery = true)
    List<Object[]> findAllTeamsWithDetails();

    @Query("SELECT t FROM Team t JOIN t.competitions c WHERE c.idcomp = :competitionId")
    List<Team> findAllByCompetitionId(@Param("competitionId") Long competitionId);

}