package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Ticket;
import org.sid.ebankingbackend.entities.TrancheAge;
import org.sid.ebankingbackend.repository.Tickets.TicketCardRepository;
import org.sid.ebankingbackend.services.Tickets.TicketCardService;
import org.sid.ebankingbackend.services.Tickets.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Ticket")
public class TicketController  {
    @Autowired
    TicketService ticketService;
    TicketCardService ticketCardService;
    TicketCardRepository ticketCardRepository;

    @GetMapping("/GetAllTickets")
    public List<Ticket> GetAllTickets() {
        return ticketService.GetAllTickets();
    }

    @GetMapping("/GetTicket/{ref}")
    public Ticket GetTicket(@PathVariable String ref) {
        return ticketService.GetTicket(ref);
    }


    @PutMapping("/ModifyTicket")
    public Ticket ModifyTicket(@RequestBody Ticket ticket) {
        return ticketService.ModifyTicket(ticket);
    }

    @DeleteMapping("/DeletTicket/{id}")
    public void DeletTicket(@PathVariable Long id) {
        ticketService.DeletTicket(id);
    }


    @PutMapping("/updatePriceForAgeGroup")
    public void updateTicketPriceForAgeGroup(
            @RequestBody Long ticketId,
            @RequestBody String trancheage) {
        TrancheAge trancheAge = TrancheAge.valueOf(trancheage.toUpperCase());
        ticketService.updateTicketPriceForAgeGroup(ticketId, trancheAge);
    }

    @PutMapping("/updateTicketAgeGroup/{ticketId}/{trancheAge}")
    public Ticket updateTicketAgeGroup(
            @PathVariable Long ticketId,
            @PathVariable TrancheAge trancheAge) {

            Ticket updatedTicket = ticketService.updateTicketPriceForAgeGroup(ticketId, trancheAge);
            return updatedTicket;
        }
    @PutMapping("/sendDiscountCode/{userId}/{discountCode}")
    public void sendDiscountCode(@PathVariable Long userId, @PathVariable String discountCode){
        ticketService.sendDiscountCode(userId,discountCode);
    }

    }
