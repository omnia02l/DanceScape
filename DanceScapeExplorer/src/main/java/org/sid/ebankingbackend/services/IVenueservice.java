package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.CompetitionWithVenueDTO;
import org.sid.ebankingbackend.entities.Town;
import org.sid.ebankingbackend.entities.Venue;

import java.util.List;
import java.util.Set;

public interface IVenueservice {



    List<Venue> retrieveAllVenues();

    Venue retrieveVenue(Long id);

    void removeVenue(Long id);
    Set<Venue> getVenuesByIdTown(Long townId);
    CompetitionWithVenueDTO getCompetitionWithVenueById(Long competitionId);

}
