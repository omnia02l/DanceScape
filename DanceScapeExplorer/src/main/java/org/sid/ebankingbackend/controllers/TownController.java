package org.sid.ebankingbackend.controllers;


import org.sid.ebankingbackend.entities.Town;

import org.sid.ebankingbackend.entities.TownWithVenuesDTO;
import org.sid.ebankingbackend.entities.TownsandVenuesDTO;
import org.sid.ebankingbackend.entities.Venue;
import org.sid.ebankingbackend.services.ITownservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/town")
@CrossOrigin("*")
public class TownController {
    @Autowired
    ITownservice townserv;

    @GetMapping("/retrieve_all_towns")
    public List<Town> gettowns() {
        List<Town> list = townserv.retrieveAllTowns();
        return list;
    }

    @PostMapping("/addWithVenues")
    public TownsandVenuesDTO addTownWithVenues(@RequestBody TownsandVenuesDTO townWithVenuesDTO) {
        Town town = townWithVenuesDTO.getTown();
        List<Venue> venues = new ArrayList<>(townWithVenuesDTO.getVenues());

        return townserv.addTownWithVenues(town, venues);
    }

    /* @PutMapping("/update_town/{id}")
     public TownsandVenuesDTO  updateTown(@PathVariable Long id, @RequestBody TownsandVenuesDTO townWithVenuesDTO) {

         townWithVenuesDTO.getTown().setIdtown(id);
         Town town = townWithVenuesDTO.getTown();
         List<Venue> venues = new ArrayList<>(townWithVenuesDTO.getVenues());
         TownsandVenuesDTO dto = townserv.addTownWithVenues(town, venues);
         return dto;
     }*/
    @GetMapping("/retrieve_town/{id}")
    public Town retrieveTown(@PathVariable Long id) {
        Town town = townserv.retrieveTown(id);
        return town;
    }

    @DeleteMapping("/remove_town/{id}")
    public void removetown(@PathVariable Long id) {
        townserv.removeTown(id);
    }

    @GetMapping("/townswithvenues")
    public List<TownWithVenuesDTO> getAllTownsWithVenues() {
        return townserv.getAllTownsWithVenues();
    }



}