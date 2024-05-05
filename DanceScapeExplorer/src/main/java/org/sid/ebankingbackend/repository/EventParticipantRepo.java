package org.sid.ebankingbackend.repository;

import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.models.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventParticipantRepo extends JpaRepository<EventParticipant, Long> {
    @Modifying
    @Transactional
    @Query(value = "Delete from event_participant where event_id =?1",nativeQuery = true)
    void deleteOldParticipants(Long eventId);

    @Query(value = "SELECT * from event_participant where event_id=?1",nativeQuery = true)
    List<EventParticipant> getEventParticipants(Long eventId);

    @Query(value = "select status from event_participant where event_id=?1 AND participant_name=?2",nativeQuery = true)
    Boolean getEventStatus(Long eventId, String participantName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE event_participant SET status=false where event_id=?1 AND participant_name=?2",nativeQuery = true)
    void rejectEvent(Long eventId, String participantName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE event_participant SET status=true where event_id=?1 AND participant_name=?2",nativeQuery = true)
    void acceptEvent(Long eventId, String participantName);

    @Query(value = "select count(*) from event_participant where status=?1", nativeQuery = true)
    Long getStatusNumber(int status);

    @Query(value = "select count(*) from event_participant where event_id=?1 AND status=?2", nativeQuery = true)
    Long xxx(Long id,int status);
}
