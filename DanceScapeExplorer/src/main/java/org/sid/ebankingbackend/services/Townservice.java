package org.sid.ebankingbackend.services;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.*;

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
    public TownsandVenuesDTO addTownWithVenues(Town town, List<Venue> venues) {
        // Sauvegarder la ville
        Town savedTown = townrepo.save(town);

        // Associer la ville à chaque lieu et sauvegarder les lieux
        for (Venue venue : venues) {
            venue.setTown(savedTown);
            venuerepo.save(venue);
        }

        // Retourner la ville avec les lieux associés
        return new TownsandVenuesDTO(savedTown, new HashSet<>(venues));
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