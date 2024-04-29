package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Venue;
import org.springframework.data.jpa.repository.JpaRepository;



public interface VenueRepository extends JpaRepository<Venue,Long> {
}
