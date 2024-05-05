package org.sid.ebankingbackend.services.Tickets;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.repository.Tickets.PlacesRepository;
import org.sid.ebankingbackend.repository.Tickets.TicketRepository;
import org.sid.ebankingbackend.repository.Tickets.VenuePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class PlacesServices implements IPlacesServices {
    @Autowired
    PlacesRepository placesRepository;
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    TicketService ticketService;
    @Autowired
    VenuePlanRepository venuePlanRepository;

    @Override
    public List<Places> GetAllPlaces() {
       return  placesRepository.findAll();

    }

    @Override
    public Places GetPlaces(Long nb) {

        return placesRepository.findBySeatNumber(nb);
    }


    public Places updatePlaceOccupation(Long id, boolean isOccupied) {
        Places place = placesRepository.findById(id).orElseThrow(() -> new RuntimeException("Place not found"));
        place.setOccupied(isOccupied);
        return placesRepository.save(place);
    }


    @Override
    public Places ModifyPlaces(Places places) {
        return placesRepository.save(places);
    }

    @Override
    public void DeletPlaces(Long id) {
        placesRepository.deleteById(id);

    }

    public List<Places> GetAllPlacesRanger() {
        // Use Sort to sort by rowLabel and then by seatNumber
        Sort sort = Sort.by("rowLabel").ascending().and(Sort.by("seatNumber").ascending());
        return placesRepository.findAll(sort);
    }

    @Scheduled(fixedRate = 60000) // Exécuter cette méthode toutes les 60 secondes
    public void resetUnconfirmedSelections() {
        List<Places> placesList = placesRepository.findAllByIsSelectedTrueAndIsOccupiedFalse();
        if (!placesList.isEmpty()) {
            for (Places place : placesList) {
                place.setSelected(false);
                placesRepository.save(place);
            }
            log.info("Unconfirmed selections have been reset");
        }
    }


    @Transactional
    public List<Places> confirmPlaces(Long venuePlanId,Long userid,List<Long> ids) {
        List<Places> confirmedPlaces = new ArrayList<>();

        for (Long id : ids) {
            Optional<Places> optionalPlace = placesRepository.findById(id);
            optionalPlace.ifPresent(place -> {
                if (place.getVenuePlan().getIdPlan().equals(venuePlanId) && place.isSelected()) {
                    place.setOccupied(true);
                    confirmedPlaces.add(place);

                    // Générer un ticket si la place est occupée
                    Ticket ticket = ticketService.generateTicket(userid,place);
                    ticketRepository.save(ticket);
                }
            });
        }

        return placesRepository.saveAll(confirmedPlaces);
    }

    public Places togglePlaceSelection(Long id) {
        Places place = placesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Place not found with id " + id));

        // Inverser uniquement l'état 'isSelected' pour la sélection/désélection
        place.setSelected(!place.isSelected());
        //place.setOccupied(!place.isOccupied());
        return placesRepository.save(place); // Retourner la place mise à jour
    }



    public Places findPlaceBySeatAndRowAndVenuePlan(Long seatNumber, RowLabel row, Long venuePlanId) {
        return placesRepository.findBySeatNumberAndRowLabelAndVenuePlan_IdPlan(seatNumber, row, venuePlanId);
    }


    public Map<String, List<Map<String, Object>>> getSeatNumbersByRow(Long venueId) {
        Map<String, List<Map<String, Object>>> seatNumbersByRow = new HashMap<>();
        VenuePlan venuePlan = venuePlanRepository.findByVenue_Idvenue(venueId);
        if (venuePlan == null) {
            // Handle the null case appropriately
            throw new EntityNotFoundException("No VenuePlan found for venue ID: " + venueId);
        }
        // Récupérer les places associées à ce plan à partir de la base de données
        List<Places> places = placesRepository.findByVenuePlan_IdPlan(venuePlan.getIdPlan());

        for (Places place : places) {
            String row = String.valueOf(place.getRowLabel());
            Map<String, Object> seatInfo = new HashMap<>();
            seatInfo.put("seatNumber", String.valueOf(place.getSeatNumber()));
            boolean isOccupied = place.isOccupied() || (place.isOccupied() && place.isSelected());
            boolean isSelected = !place.isOccupied() && place.isSelected();
            seatInfo.put("isOccupied", isOccupied);
            seatInfo.put("isSelected", isSelected);


            seatNumbersByRow.computeIfAbsent(row, k -> new ArrayList<>()).add(seatInfo);
        }

        return seatNumbersByRow;
    }

    public List<SeatStatusDTO> getPlaceStatistics(Long venuePlanId) {
        List<Places> allPlaces = placesRepository.findByVenuePlan_IdPlan(venuePlanId);
        List<SeatStatusDTO> seatStatuses = new ArrayList<>();

        for (Places place : allPlaces) {
            String status;
            switch (place.getBookingCount()) {
                case 0:
                    status = "never-booked";
                    break;
                case 1:
                    status = "booked-once";
                    break;
                default:
                    status = "booked-more-than-once";
                    break;
            }
            seatStatuses.add(new SeatStatusDTO(place.getIdPlace(), status));
        }

        return seatStatuses;
    }

}


