package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TownRepository extends JpaRepository<Town,Long> {
}
