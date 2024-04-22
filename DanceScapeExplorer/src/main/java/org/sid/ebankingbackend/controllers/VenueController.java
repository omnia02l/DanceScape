package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.CompetitionWithVenueDTO;
import org.sid.ebankingbackend.entities.Team;
import org.sid.ebankingbackend.entities.Venue;
import org.sid.ebankingbackend.services.ICompetitionservice;
import org.sid.ebankingbackend.services.ITeamservice;
import org.sid.ebankingbackend.services.IVenueservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/venue")
@CrossOrigin("*")
public class VenueController {
    @Autowired
    IVenueservice serv;

    @GetMapping("/retrieve_all_venues")
    public List<Venue> getvenues() {
        List<Venue> list = serv.retrieveAllVenues();
        return list;
    }


    @GetMapping("/retrieve_venue/{id}")
    public Venue  getvenuebyid(@PathVariable Long id) {
        Venue ven = serv.retrieveVenue(id);
        return ven;
    }

    @DeleteMapping("/remove_venue/{id}")
    public void removevenue(@PathVariable Long id) {
        serv.removeVenue(id);
    }
    @GetMapping("/getVenuesByTown/{townId}")
    public Set<Venue> getVenuesByTown(@PathVariable Long townId) {
        Set<Venue> venues = serv.getVenuesByIdTown(townId);
        return venues;
    }

    @GetMapping("/{id}/competitionwithvenue")
    public CompetitionWithVenueDTO getCompetitionWithVenueById(@PathVariable Long id) {
        return serv.getCompetitionWithVenueById(id);
    }



}
