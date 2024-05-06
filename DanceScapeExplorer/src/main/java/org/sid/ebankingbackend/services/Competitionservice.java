package org.sid.ebankingbackend.services;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.repository.*;
import org.sid.ebankingbackend.repository.Tickets.PlacesRepository;
import org.sid.ebankingbackend.repository.Tickets.TicketRepository;
import org.sid.ebankingbackend.services.Tickets.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
    @Autowired
    PlacesRepository placesRepository;
    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;


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
    @Override
    public Map<String, Long> getCompetitionCountByDanceStyle() {
        return comprepo.findAll().stream()
                .collect(Collectors.groupingBy(
                        competition -> competition.getStyle(),
                        Collectors.counting()
                ));
    }
////////////////////
@Override
public String getCountdown(Competition competition) {
    LocalDateTime currentDateTime = LocalDateTime.now();
    LocalDateTime startDateTime = convertToLocalDateTimeViaSqlDate(competition.getStartdate());
    LocalDateTime endDateTime = convertToLocalDateTimeViaSqlDate(competition.getEnddate());

    if (currentDateTime.isBefore(startDateTime)) {
        // If the current date is before the start date, calculate the difference
        Duration durationUntilStart = Duration.between(currentDateTime, startDateTime);
        long days = durationUntilStart.toDays();
        long hours = durationUntilStart.toHours() % 24;
        long minutes = durationUntilStart.toMinutes() % 60;
        long seconds = durationUntilStart.getSeconds() % 60;
        return String.format("%d days : %d hours: %d minutes: %d seconds \n until the competition starts", days, hours, minutes, seconds);
    } else if (currentDateTime.isEqual(startDateTime)) {
        // If the current date is equal to the start date, display "Day D"
        return "It's D-Day: The competition starts today!";
    } else if (currentDateTime.isBefore(endDateTime)) {
        // If the current date is after the start date but before the end date, calculate the difference
        Duration durationUntilEnd = Duration.between(currentDateTime, endDateTime);
        long days = durationUntilEnd.toDays();
        long hours = durationUntilEnd.toHours() % 24;
        long minutes = durationUntilEnd.toMinutes() % 60;
        long seconds = durationUntilEnd.getSeconds() % 60;
        return String.format(" %d days : %d hours : %d minutes : %d seconds left \n until the end of the competition", days, hours, minutes, seconds);
    } else {
        // If the current date is after the end date, display an appropriate message
        return "The competition has ended. Thank you for participating!";
    }
}
    private LocalDateTime convertToLocalDateTimeViaSqlDate(Date dateToConvert) {
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }
//////////////////////
@Scheduled(fixedRate = 1000) // Exécuter toutes les secondes
public void updateCompetitionStatus() {
    List<Competition> competitions = comprepo.findAll();
    LocalDateTime currentDateTime = LocalDateTime.now();

    for (Competition competition : competitions) {
        updateStatus(competition, currentDateTime);
        comprepo.save(competition);
    }
    System.out.println("Competition status updated.");
}

    private void updateStatus(Competition competition, LocalDateTime currentDateTime) {
        LocalDateTime startDateTime = convertToLocalDateTimeViaSqlDate(competition.getStartdate());
        LocalDateTime endDateTime = convertToLocalDateTimeViaSqlDate(competition.getEnddate());

        if (currentDateTime.isBefore(startDateTime)) {
            competition.setCompstatus(Compstatus.Upccoming);
        } else if (currentDateTime.isEqual(startDateTime) || (currentDateTime.isAfter(startDateTime) && currentDateTime.isBefore(endDateTime))) {
            competition.setCompstatus(Compstatus.Ongoing);
        } else {
            competition.setCompstatus(Compstatus.Concluded);
        }
    }






    //////////////////

    public Long getVenuePlanIdByCompetitionId(Long competitionId) {
        return comprepo.findVenuePlanIdByCompetitionId(competitionId)
                .orElseThrow(() -> new RuntimeException("Competition or Venue Plan not found"));
    }





    //ibtihel sprint2
    //@Scheduled(cron = "0 0 0 * * ?")
    // @Scheduled(cron = "0 */3 * * * *")
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
    public List<TicketKpiDTO> getAllCompetitionStats() {
        return comprepo.findAll().stream().map(comp -> {
            int totalTicketsSold = ticketService.getTotalTicketsSold(comp.getIdcomp());
            float totalRevenue = ticketService.getTotalRevenue(comp.getIdcomp());
            int occupiedSeats = ticketRepository.countOccupiedSeatsByCompetitionId(comp.getIdcomp());
            int totalSeats = ticketRepository.totalSeatsByCompetitionId(comp.getIdcomp());
            double occupancyRate = (double) occupiedSeats / totalSeats * 100;  // Calculate percentage

            return new TicketKpiDTO(
                    comp.getIdcomp(),
                    comp.getVenue().getIdvenue(),
                    comp.getCompname(),
                    comp.getVenue().getVname(),
                    totalTicketsSold,
                    totalRevenue,
                    occupancyRate,
                    occupiedSeats,
                    totalSeats
            );
        }).collect(Collectors.toList());
    }


}






