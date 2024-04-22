package org.sid.ebankingbackend.services;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Town;
import org.sid.ebankingbackend.entities.TownWithVenuesDTO;
import org.sid.ebankingbackend.entities.Venue;
import org.sid.ebankingbackend.repository.DancecategRepository;
import org.sid.ebankingbackend.repository.DancestyleRepository;
import org.sid.ebankingbackend.repository.TownRepository;
import org.sid.ebankingbackend.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class Townservice implements ITownservice {
    @Autowired
    VenueRepository venuerepo;
    @Autowired
    TownRepository townrepo;

    @Override
    public List<Town> retrieveAllTowns() {
        return townrepo.findAll();
    }

    @Override
    public Town addTown(Town town) {
        // Vérifier si getVenues() renvoie null
        if (town.getVenues() == null) {
            town.setVenues(new HashSet<>()); // Ou une autre initialisation selon votre logique
        } else {
            for (Venue venue : town.getVenues()) {
                venue.setTown(town);
            }
        }
        return townrepo.save(town);
    }





    @Override
    public Town retrieveTown(Long id) {
        return townrepo.findById(id).get();
    }

    @Override
    public void removeTown(Long id) {
        townrepo.deleteById(id);
    }

    @Override
    public List<TownWithVenuesDTO> getAllTownsWithVenues() {
        List<Town> towns = townrepo.findAlltownsWithVenues();
        List<TownWithVenuesDTO> townWithVenuesList = new ArrayList<>();

        towns.forEach(town -> {
            TownWithVenuesDTO townWithVenues = new TownWithVenuesDTO();
            townWithVenues.setTownName(town.getTownname());
            townWithVenues.setVenues(town.getVenues());
            townWithVenuesList.add(townWithVenues);
        });

        return townWithVenuesList;
    }

}



