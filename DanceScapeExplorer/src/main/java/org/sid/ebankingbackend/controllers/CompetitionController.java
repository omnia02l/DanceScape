package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.GenderstatDTO;
import org.sid.ebankingbackend.entities.TicketKpiDTO;
import org.sid.ebankingbackend.services.Competitionservice;
import org.sid.ebankingbackend.services.ICompetitionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/competition")
@CrossOrigin("*")
public class CompetitionController {
    @Autowired
    ICompetitionservice compserv;
    @Autowired
    Competitionservice competitionservice;
    @GetMapping("/retrieve_all_comps")
    public List<Competition> getCompetitions() {
        List<Competition> listcomps = compserv.retrieveAllCompetitions();
        return listcomps;
    }



    @PutMapping("/update_comp/{id}")
    public Competition  updateCompetition(@PathVariable Long id, @RequestBody Competition c) {

        c.setIdcomp(id);
        Competition comp = compserv.updateCompetition(c);
        return comp;
    }
    @GetMapping("/retrieve_comp/{id}")
    public Competition retrieveCompetition(@PathVariable Long id) {
        Competition comp = compserv.retrieveCompetition(id);
        return comp;
    }

    @DeleteMapping("/remove_comp/{id}")
    public void removeCompetition(@PathVariable Long id) {
        compserv.removeCompetition(id);
    }

    @PostMapping("/addcompetition/{categoryId}/{styleId}/{venueId}")
    public Competition addCompetitionWithCategoryAndStyle(@RequestBody Competition competition,
                                                          @PathVariable Long categoryId,
                                                          @PathVariable Long styleId,
                                                          @PathVariable Long venueId) {
        return compserv.addCompetition(competition, categoryId, styleId,venueId);
    }
    @PutMapping("/updatecomp/{id}/{categoryId}/{styleId}/{venueId}")
    public Competition updateCompetitionWithCategoryAndStyle(@RequestBody Competition competition,
                                                          @PathVariable Long categoryId,
                                                          @PathVariable Long styleId,
                                                             @PathVariable Long id,
                                                             @PathVariable Long venueId){
        competition.setIdcomp(id);
        return compserv.addCompetition(competition, categoryId, styleId,venueId);
    }

    @PutMapping("/GenderStatsForCompetition/{competitionId}")
    public GenderstatDTO GenderStatsForCompetition(@PathVariable Long competitionId) {
        return compserv.getGenderStatsForCompetition(competitionId);
    }

    @GetMapping("/statcountByDanceStyle")
    public Map<String, Long> getCompetitionCountByDanceStyle() {
        return compserv.getCompetitionCountByDanceStyle();
    }
    @GetMapping("/statparticipants")
    public Map<String, Long> getNumberOfParticipantsPerCompetition() {
        return compserv.getNumberOfParticipantsPerCompetition();
    }

    @GetMapping("/venue-plan-id/{id}")
    public Long getVenuePlanIdByCompetitionId(@PathVariable("id") Long competitionId) {
        return competitionservice.getVenuePlanIdByCompetitionId(competitionId);
    }

    @GetMapping("/statsKpiTicket")
    public ResponseEntity<List<TicketKpiDTO>> getAllCompetitionStats() {
        List<TicketKpiDTO> stats = competitionservice.getAllCompetitionStats();
        return ResponseEntity.ok(stats);
    }


    @GetMapping("/countdown/{competitionId}")
    public Map<String, String> getDayCountdown(@PathVariable Long competitionId) {
        Competition competition = compserv.retrieveCompetition(competitionId);
        String countdown = compserv.getCountdown(competition);

        Map<String, String> response = new HashMap<>();
        response.put("countdown", countdown);

        return response;
    }
}
