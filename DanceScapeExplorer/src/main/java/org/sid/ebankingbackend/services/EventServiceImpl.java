package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.models.Event;
import org.sid.ebankingbackend.models.EventParticipant;
import org.sid.ebankingbackend.models.EventStats;
import org.sid.ebankingbackend.payload.response.MyEventResponse;
import org.sid.ebankingbackend.payload.response.UserDTO;
import org.sid.ebankingbackend.repository.EventParticipantRepo;
import org.sid.ebankingbackend.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepo eventRepo;
    @Autowired
    EventParticipantRepo eventParticipantRepo;

    @Override
    public String createEvent(Event event) {
        this.eventRepo.save(event);
        return "Event created";
    }

    @Override
    public List<Event> listEvents() {
        return this.eventRepo.findAll();
    }

    @Override
    public String updateParticipants(Long eventId, List<UserDTO> userDTOS) {
        this.eventParticipantRepo.deleteOldParticipants(eventId);
        List<EventParticipant> eventParticipants = new ArrayList<>();
        userDTOS.forEach(user -> {
            EventParticipant eventParticipant = new EventParticipant();
            eventParticipant.setEventId(eventId);
            eventParticipant.setParticipantName(user.getUserName());
            eventParticipants.add(eventParticipant);
        });
        this.eventParticipantRepo.saveAll(eventParticipants);
        return "Participants updated";
    }

    @Override
    public List<EventParticipant> getEventParticipants(Long eventId) {
        return this.eventParticipantRepo.getEventParticipants(eventId);
    }

    @Override
    public String deleteEvent(Long eventId) {
        this.eventParticipantRepo.deleteOldParticipants(eventId);
        this.eventRepo.deleteById(eventId);
        return "Event deleted";
    }

    @Override
    public List<MyEventResponse> myEventList(String participantName) {
        List<Event> events = this.eventRepo.myEventsList(participantName);
        List<MyEventResponse> myEventResponses = new ArrayList<>();
        events.forEach(event -> {
            MyEventResponse myEventResponse = new MyEventResponse();
            myEventResponse.setEvent(event);
            myEventResponse.setStatus(this.eventParticipantRepo.getEventStatus(event.getId(), participantName));
            myEventResponses.add(myEventResponse);
        });
        return myEventResponses;
    }

    @Override
    public String acceptEvent(Long eventId, String participantName) {
        this.eventParticipantRepo.acceptEvent(eventId,participantName);
        return "Event accepted";
    }

    @Override
    public String rejectEvent(Long eventId, String participantName) {
        this.eventParticipantRepo.rejectEvent(eventId,participantName);
        return "Event rejected";
    }

    public EventStats getEventStats() {
        return new EventStats(this.eventRepo.totalEventNumber(),this.eventParticipantRepo.getStatusNumber(0),
                this.eventParticipantRepo.getStatusNumber(1));
    }

    @Override
    public String eventRate(Long eventId , int rate) {
        Event event = this.eventRepo.findById(eventId).get();
        event.setRateNumber(event.getRateNumber() +1 );
        event.setRate(event.getRate() + rate);
        event.setRateX(event.getRate() / event.getRateNumber());
        this.eventRepo.save(event);
        return "Thank you";
    }

    @Override
    public EventStats getEventStatsById(Long id) {
        return new EventStats(null, this.eventParticipantRepo.xxx(id,0),
                this.eventParticipantRepo.xxx(id,1));
    }
}
