package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.models.Event;
import org.sid.ebankingbackend.models.EventParticipant;
import org.sid.ebankingbackend.models.EventStats;
import org.sid.ebankingbackend.payload.response.MyEventResponse;
import org.sid.ebankingbackend.payload.response.UserDTO;
import org.sid.ebankingbackend.services.EmailService;
import org.sid.ebankingbackend.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EmailService emailService;
    @Autowired
    EventService eventService;

    @PostMapping("/create")
    private String createEvent(@RequestBody Event event){
        return this.eventService.createEvent(event);
    }

    @GetMapping("/list-events")
    private List<Event> listEvents(){
       return this.eventService.listEvents();
    }

    /*@PostMapping("/update-participants")
    private String updateParticipants(@RequestParam Long eventId, @RequestBody List<UserDTO> userDTOS){

        return this.eventService.updateParticipants(eventId, userDTOS);
    }*/
    @PostMapping("/update-participants")
    private String updateParticipants(@RequestParam Long eventId, @RequestBody List<UserDTO> userDTOS) {
        for (UserDTO userDTO : userDTOS) {
            String emailAddress = userDTO.getEmail(); // Supposons que l'email de l'utilisateur soit stocké dans le champ "email" de UserDTO
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(emailAddress);
            simpleMailMessage.setSubject("Invitation");
            simpleMailMessage.setFrom("clasherwin59@gmail.com");
            //set
            simpleMailMessage.setText("Dear "+ userDTO.getFirsName() +", You have been invited to an event, go and check for more details. ");
            emailService.send(simpleMailMessage);
        }

        return this.eventService.updateParticipants(eventId, userDTOS);
    }

    @GetMapping("/list-participants")
    private List<EventParticipant> listEventParticipants(@RequestParam Long eventId){
        return this.eventService.getEventParticipants(eventId);
    }

    @DeleteMapping("/delete-event")
    private String deleteEvent(@RequestParam Long eventId){
        return this.eventService.deleteEvent(eventId);
    }

    @GetMapping("/list-my-events")
    private List<MyEventResponse> listMyEvents(Principal principal){
        return this.eventService.myEventList(principal.getName());
    }

    @PostMapping("/reject-event")
    private String rejectEvent(Principal principal, @RequestParam Long eventId){
        return this.eventService.rejectEvent(eventId,principal.getName());
    }

    @PostMapping("/accept-event")
    private String acceptEvent(Principal principal, @RequestParam Long eventId){
        return this.eventService.acceptEvent(eventId,principal.getName());
    }

    @GetMapping("/get-event-stats")
    private EventStats getEventStats(){
        return this.eventService.getEventStats();
    }

    @PostMapping("/rate-event")
    private String rateEvent(@RequestParam Long eventId, @RequestParam int rate){
        return this.eventService.eventRate(eventId,rate);
    }
    @GetMapping("/get-event-stats-id/{id}")
    private EventStats getEventStatsById(@PathVariable Long id){
        return this.eventService.getEventStatsById(id);
    }
}
