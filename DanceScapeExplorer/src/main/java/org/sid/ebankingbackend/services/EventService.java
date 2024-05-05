package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.models.Event;
import org.sid.ebankingbackend.models.EventParticipant;
import org.sid.ebankingbackend.models.EventStats;
import org.sid.ebankingbackend.payload.response.MyEventResponse;
import org.sid.ebankingbackend.payload.response.UserDTO;

import java.util.List;

public interface EventService {
    String createEvent(Event event);

    List<Event> listEvents();

    String updateParticipants(Long eventId,List<UserDTO> userDTOS);

    List<EventParticipant> getEventParticipants(Long eventId);

    String deleteEvent(Long eventId);

    List<MyEventResponse> myEventList(String participantName);

    String acceptEvent(Long eventId, String participantName);

    String rejectEvent(Long eventId, String participantName);

    EventStats getEventStats();

    String eventRate(Long eventId,int rate);
    EventStats getEventStatsById(Long id);

}
