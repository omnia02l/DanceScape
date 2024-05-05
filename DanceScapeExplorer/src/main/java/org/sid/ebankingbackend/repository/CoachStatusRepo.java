package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.CoachStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachStatusRepo extends JpaRepository<CoachStatus,Long> {
}
