package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.VenuePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VenuePlanRepository extends JpaRepository<VenuePlan, Long> {

    VenuePlan findByVenue_Idvenue(Long venueId);
}
