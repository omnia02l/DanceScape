package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM event WHERE id IN(SELECT event_id FROM event_participant WHERE participant_name=?1)",nativeQuery = true)
    List<Event> myEventsList(String participantName);

    @Query(value = "select count(*) from event", nativeQuery = true)
    Long totalEventNumber();
}
