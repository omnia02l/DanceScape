package org.sid.ebankingbackend.services;



import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.Team;
import org.sid.ebankingbackend.repository.DancerRepository;
import org.sid.ebankingbackend.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service


public class Teamservice implements ITeamservice {
    @Autowired
    TeamRepository teamrepo;
    @Autowired
    DancerRepository dancerrepo;

    @Override
    public List<Team> retrieveAllTeams() {return teamrepo.findAll();}

    @Override
    public Team addTeam(Team t) {return teamrepo.save(t);}

    @Override
    public Team updateTeam(Team t) {return teamrepo.save(t);}

    @Override
    public Team retrieveTeam(Long idteam) {return teamrepo.findById(idteam).get();}

    @Override
    public void removeTeam(Long idteam) { teamrepo.deleteById(idteam);}



    @Override
    public Map<String, List<String>> getCompetitionsWithTeams() {
        List<Team> teams = teamrepo.findAll();
        Map<String, List<String>> competitionTeamsMap = new HashMap<>();

        for (Team team : teams) {
            for (Competition competition : team.getCompetitions()) {
                String competitionName = competition.getCompname();
                competitionTeamsMap.computeIfAbsent(competitionName, k -> new ArrayList<>())
                        .add(team.getTeamname());
            }
        }

        return competitionTeamsMap;
    }

    @Override
    public Map<String, List<String>> getTeamsWithDancers() {
        List<Team> teams = teamrepo.findAll();
        Map<String, List<String>> teamDancersMap = new HashMap<>();

        for (Team team : teams) {
            List<String> dancerNames = team.getDancers().stream()
                    .map(dancer -> dancer.getDfirstname() + " " + dancer.getDlastname())
                    .collect(Collectors.toList());
            teamDancersMap.put(team.getTeamname(), dancerNames);
        }

        return teamDancersMap;
    }
    @Override
    public List<Team> getTeamsByCompetitionId(Long competitionId) {
        return teamrepo.findAllByCompetitionId(competitionId);
    }

}




