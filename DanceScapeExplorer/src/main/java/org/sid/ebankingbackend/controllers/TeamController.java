package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Team;
import org.sid.ebankingbackend.services.ITeamservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/team")
@CrossOrigin("*")
public class TeamController {
    @Autowired
    ITeamservice teamserv;
    @GetMapping("/retrieve_all_teams")
    public List<Team> getTeams() {
        List<Team> listteams = teamserv.retrieveAllTeams();
        return listteams;
    }

    @PostMapping("/add_dancer")
    public Team addDancer(@RequestBody Team t) {
        Team team = teamserv.addTeam(t);
        return team;
    }

    @PutMapping("/update_team/{id}")
    public Team  updateTeam(@PathVariable Long id, @RequestBody Team t) {

        t.setIdteam(id);
        Team team = teamserv.updateTeam(t);
        return team;
    }
    @GetMapping("/retrieve_team/{id}")
    public Team  retrieveTeam(@PathVariable Long id) {
        Team team = teamserv.retrieveTeam(id);
        return team;
    }

    @DeleteMapping("/remove_team/{id}")
    public void removeTeam(@PathVariable Long id) {
        teamserv.removeTeam(id);
    }



    @GetMapping("/competitions-with-teams")
    public Map<String, List<String>> getCompetitionsWithTeams() {
        return teamserv.getCompetitionsWithTeams();
    }

    @GetMapping("/teams-with-dancers")
    public Map<String, List<String>> getTeamsWithDancers() {
        return teamserv.getTeamsWithDancers();
    }


    @GetMapping("/by-competition/{competitionId}")
    public List<Team> getTeamsByCompetitionId(@PathVariable Long competitionId) {
        return teamserv.getTeamsByCompetitionId(competitionId);
    }



}
