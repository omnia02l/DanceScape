package org.sid.ebankingbackend.services;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class Competitionservice implements ICompetitionservice {
    @Autowired
    CompetitionRepository comprepo;
    @Autowired
    DancestyleRepository stylrepo;
    @Autowired
    DancecategRepository categrepo;
    @Autowired
    DancerRepository dancerepo;
    @Autowired
    VenueRepository venrepo;


    @Override
    public List<Competition> retrieveAllCompetitions() {
        return comprepo.findAll();
    }

    @Override
    public Competition addCompetition(Competition c) {
        return comprepo.save(c);
    }

    @Override
    public Competition updateCompetition(Competition c) {
        return comprepo.save(c);
    }

    @Override
    public Competition retrieveCompetition(Long idcomp) {
        return comprepo.findById(idcomp).get();
    }

    @Override
    public void removeCompetition(Long idcomp) {
        comprepo.deleteById(idcomp);
    }

    @Override
    public Competition addCompetition(Competition c, Long categoryId, Long styleId,Long venueId) {
        // Récupération de la catégorie de danse sélectionnée
        Dancecategory category = categrepo.findById(categoryId).get();

        // Récupération du style de danse sélectionné
        Dancestyle style = stylrepo.findById(styleId).get();
        // Récupération de la venue sélectionnée
        Venue venue = venrepo.findById(venueId).get();
        // Assignation de la catégorie et du style à la compétition
        c.setDancecateg(category);
        c.setStyle(style.getStyledname());
        c.setVenue(venue);
        // Enregistrement de la compétition
        return comprepo.save(c);
    }

    @Override
    public GenderstatDTO getGenderStatsForCompetition(Long competitionId) {
        // Récupérer le nombre total d'hommes pour la compétition
        int totalMaleDancers = dancerepo.countMaleDancersInTeamsInCompetition(competitionId);

        // Récupérer le nombre total de femmes pour la compétition
        int totalFemaleDancers = dancerepo.countFemaleDancersInTeamsInCompetition(competitionId);

        // Créer un objet GenderstatDTO avec les statistiques obtenues
        GenderstatDTO genderStats = new GenderstatDTO(totalMaleDancers, totalFemaleDancers);

        return genderStats;
    }

    @Override
    public Map<String, Long> getCompetitionCountByDanceStyle() {
        return comprepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        competition -> competition.getStyle(),
                        Collectors.counting()
                ));
    }
    @Override
    public Map<String, Long> getNumberOfParticipantsPerCompetition() {
        Map<String, Long> participantsPerCompetition = new HashMap<>();

        List<Competition> competitions = comprepo.findAll();

        for (Competition competition : competitions) {
            long totalParticipants = competition.getTeams().stream()
                    .flatMap(team -> team.getDancers().stream())
                    .count();

            participantsPerCompetition.put(competition.getCompname(), totalParticipants);
        }

        return participantsPerCompetition;
    }

}






