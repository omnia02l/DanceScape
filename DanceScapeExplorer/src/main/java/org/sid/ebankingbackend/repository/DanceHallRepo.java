package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.DanceHall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceHallRepo extends JpaRepository<DanceHall, Long> {

    Boolean existsByHallName(String hallName);

    DanceHall findByHallName(String hallName);
}
