package org.sid.ebankingbackend.services;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.repository.*;
import org.sid.ebankingbackend.repository.Tickets.PlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    PlacesRepository placesRepository;


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


    public Long getVenuePlanIdByCompetitionId(Long competitionId) {
        return comprepo.findVenuePlanIdByCompetitionId(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition or Venue Plan not found"));
    }





    //ibtihel sprint2
    //@Scheduled(cron = "0 0 0 * * ?")
     @Scheduled(cron = "0 */3 * * * *")
    @Transactional
    public void checkCompetitionEndDate() {
        Date today = new Date();
        List<Competition> competitions = comprepo.findAll();
        System.out.println("Running scheduled task to check competition end dates.");

        for (Competition competition : competitions) {
            if (competition.getEnddate() != null && !competition.getEnddate().after(today)) {
                VenuePlan venuePlan = competition.getVenue().getVenuePlan();
                if (venuePlan != null) {
                    List<Places> placesList = venuePlan.getPlaces();
                    for (Places place : placesList) {
                        place.setOccupied(false);


                        placesRepository.save(place);
                    }
                }
            }
        }
    }

}






