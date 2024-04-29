package org.sid.ebankingbackend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.repository.CompetitionRepository;
import org.sid.ebankingbackend.repository.PerformanceRepository;
import org.sid.ebankingbackend.repository.RegistrationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor


public class Performanceservice  implements IPerformanceservice{
    @Autowired
    PerformanceRepository perfrepo;
    @Autowired
    CompetitionRepository comprepo;


    @Override
    @Transactional
    public Performance addPerformanceToTeamInCompetition(Long competitionId, Long teamId, Performance performance) {
        // 1. Récupérer la compétition
        Competition competition = comprepo.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Compétition non trouvée avec l'ID: " + competitionId));

        // 2. Vérifier si l'équipe appartient à la compétition
        Team team = competition.getTeams().stream()
                .filter(t -> t.getIdteam().equals(teamId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Équipe non trouvée dans la compétition avec l'ID: " + teamId));

        // 3. Associer l'ID de l'équipe trouvée à la performance
        performance.setIdTeam(teamId);

        // 4. Associer la performance à la compétition
        performance.setCompetition(competition);

        // 5. Enregistrer la performance
        return perfrepo.save(performance);
    }
    @Override
    public Map<Competition, Set<Performance>> getPerformancesByCompetition() {
        List<Competition> competitions = comprepo.findAllWithPerformances();

        Map<Competition, Set<Performance>> performancesByCompetition = new HashMap<>();

        for (Competition competition : competitions) {
            performancesByCompetition.put(competition, competition.getPerformances());
        }

        return performancesByCompetition;
    }



    @Override
    public Performance retrieveperformance(Long id) {
        return perfrepo.findById(id).get();
    }

}




