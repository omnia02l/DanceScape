package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.CompetitionPerformanceDTO;
import org.sid.ebankingbackend.entities.Dancestyle;
import org.sid.ebankingbackend.entities.Performance;
import org.sid.ebankingbackend.services.IDancestyleservice;
import org.sid.ebankingbackend.services.IPerformanceservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/perf")
@CrossOrigin("*")
public class Performancecontroller {
    @Autowired
    IPerformanceservice perfserv;

    @PostMapping("/add/{competitionId}/{teamId}")
    public Performance addPerformanceToTeamInCompetition(
            @PathVariable Long competitionId,
            @PathVariable Long teamId,
            @RequestBody Performance performance) {

        return perfserv.addPerformanceToTeamInCompetition(competitionId, teamId, performance);
    }
    @GetMapping("/performances")
    public List<CompetitionPerformanceDTO> getPerformancesByCompetition() {
        Map<Competition, Set<Performance>> resultMap = perfserv.getPerformancesByCompetition();

        List<CompetitionPerformanceDTO> resultList = new ArrayList<>();
        for (Map.Entry<Competition, Set<Performance>> entry : resultMap.entrySet()) {
            Competition competition = entry.getKey();

            CompetitionPerformanceDTO dto = new CompetitionPerformanceDTO();
            dto.setCompname(competition.getCompname());
            dto.setStartdate(competition.getStartdate());
            dto.setEnddate(competition.getEnddate());
            dto.setPerformances(entry.getValue());

            resultList.add(dto);
        }

        return resultList;
    }


    @GetMapping("/performance/{id}")
    public Performance getPerformanceById(@PathVariable Long id) {
        return perfserv.retrieveperformance(id);
    }

}
