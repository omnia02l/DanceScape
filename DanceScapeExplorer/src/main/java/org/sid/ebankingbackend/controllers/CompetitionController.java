package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.GenderstatDTO;
import org.sid.ebankingbackend.services.ICompetitionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;





import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/competition")
@CrossOrigin("*")
public class CompetitionController {
    @Autowired
    ICompetitionservice compserv;
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

    @PostMapping("/addcompetition/{categoryId}/{styleId}")
    public Competition addCompetitionWithCategoryAndStyle(@RequestBody Competition competition,
                                                          @PathVariable Long categoryId,
                                                          @PathVariable Long styleId) {
        return compserv.addCompetition(competition, categoryId, styleId);
    }
    @PutMapping("/updatecomp/{id}/{categoryId}/{styleId}")
    public Competition updateCompetitionWithCategoryAndStyle(@RequestBody Competition competition,
                                                          @PathVariable Long categoryId,
                                                          @PathVariable Long styleId,
                                                             @PathVariable Long id) {
        competition.setIdcomp(id);
        return compserv.addCompetition(competition, categoryId, styleId);
    }

    @PutMapping("/GenderStatsForCompetition/{competitionId}")
    public GenderstatDTO GenderStatsForCompetition(@PathVariable Long competitionId) {
        return compserv.getGenderStatsForCompetition(competitionId);
    }



}
