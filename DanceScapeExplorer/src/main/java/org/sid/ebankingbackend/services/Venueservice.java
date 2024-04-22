package org.sid.ebankingbackend.services;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.CompetitionWithVenueDTO;
import org.sid.ebankingbackend.entities.Town;
import org.sid.ebankingbackend.entities.Venue;
import org.sid.ebankingbackend.repository.CompetitionRepository;
import org.sid.ebankingbackend.repository.TownRepository;
import org.sid.ebankingbackend.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class Venueservice implements IVenueservice {
    @Autowired
    VenueRepository venuerepo;
    @Autowired
    TownRepository townrepo;
    @Autowired
    CompetitionRepository comprepo;

    @Override
    public List<Venue> retrieveAllVenues() {
        return venuerepo.findAll();
    }



    @Override
    public Venue retrieveVenue(Long id) {
        return venuerepo.findById(id).get();
    }

    @Override
    public void removeVenue(Long id) {
        venuerepo.deleteById(id);
    }

    @Override
    public Set<Venue> getVenuesByIdTown(Long townId) {
        Town town = townrepo.findById(townId).get();

        return town.getVenues();
    }
    @Override
    public CompetitionWithVenueDTO getCompetitionWithVenueById(Long id) {
        Competition competition = comprepo.findById(id).get();
        Venue venue = venuerepo.findById(competition.getVenue().getIdvenue()).get();
        Town town = townrepo.findById(venue.getTown().getIdtown()).get();

        CompetitionWithVenueDTO dto = new CompetitionWithVenueDTO();

        dto.setCompetitionId(competition.getIdcomp());
        dto.setCompetitionName(competition.getCompname());
        dto.setCompetitionDescription(competition.getCdescreption());

        dto.setVenueId(venue.getIdvenue());
        dto.setVenueName(venue.getVname());
        dto.setVenueAddress(venue.getVaddress());

        dto.setTownId(town.getIdtown());
        dto.setTownName(town.getTownname());

        return dto;
    }


}



