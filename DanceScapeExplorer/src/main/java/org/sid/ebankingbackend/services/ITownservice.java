package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Town;

import org.sid.ebankingbackend.entities.TownWithVenuesDTO;
import org.sid.ebankingbackend.entities.TownsandVenuesDTO;
import org.sid.ebankingbackend.entities.Venue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ITownservice {


    List<Town> retrieveAllTowns();

    TownsandVenuesDTO addTownWithVenues(Town town, List<Venue> venues);



    Town retrieveTown(Long id);

    void removeTown(Long id);
    List<TownWithVenuesDTO> getAllTownsWithVenues();


}