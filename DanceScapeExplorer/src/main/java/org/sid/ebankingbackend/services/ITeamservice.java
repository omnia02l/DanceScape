package org.sid.ebankingbackend.services;


import org.sid.ebankingbackend.entities.Team;


import java.util.List;
import java.util.Map;

public interface ITeamservice {
    List<Team> retrieveAllTeams();

    Team addTeam(Team t);

    Team updateTeam(Team t);

    Team retrieveTeam(Long idteam);

    void removeTeam(Long idteam);

    Map<String, List<String>> getCompetitionsWithTeams();
    Map<String, List<String>> getTeamsWithDancers();
    List<Team> getTeamsByCompetitionId(Long competitionId);
}
