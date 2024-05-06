package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Ticket;
import org.sid.ebankingbackend.entities.TrancheAge;
import org.sid.ebankingbackend.repository.Tickets.TicketCardRepository;
import org.sid.ebankingbackend.services.Tickets.MonthlyTicketStatsDTO;
import org.sid.ebankingbackend.services.Tickets.TicketCardService;
import org.sid.ebankingbackend.services.Tickets.TicketService;
import org.sid.ebankingbackend.services.Tickets.TicketStatisticsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    @PostMapping("/process/{ref}")
    public ResponseEntity<String> processTicket(@PathVariable String ref) {
        try {
            ticketService.processTicket(ref);
            return ResponseEntity.ok("Ticket processed successfully and email sent.");
        } catch (IllegalStateException e) {
            // Return a custom message if the ticket has already been scanned
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ticket already scanned.");
        } catch (Exception e) {
            // Other exceptions, e.g., ticket not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/ticketsByStyleAndYear")
    public List<Object[]> getTicketsByStyleAndYearRange(
            @RequestParam String styleName,
            @RequestParam int startYear,
            @RequestParam int endYear) {
        return ticketService.getTicketCountsByStyleAndYearRange(styleName, startYear, endYear);
    }
    @GetMapping("/ticketCountsForAllStyles/{year}")
    public ResponseEntity<Map<Integer, Map<String, List<MonthlyTicketStatsDTO>>>> getTicketCountsForAllStyles(@PathVariable int year) {
        Map<Integer, Map<String, List<MonthlyTicketStatsDTO>>> stats = ticketService.getTicketCountsByYearForAllStyles(year);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<TicketStatisticsDTO>> getTicketSalesStats(@RequestParam Long competitionId) {
        return ResponseEntity.ok(ticketService.getTicketStatistics(competitionId));
    }

    @GetMapping("/total-sold/{competitionId}")
    public ResponseEntity<Integer> getTotalTicketsSold(@PathVariable Long competitionId) {
        int totalTicketsSold = ticketService.getTotalTicketsSold(competitionId);
        return ResponseEntity.ok(totalTicketsSold);
    }

    @GetMapping("/total-revenue/{competitionId}")
    public ResponseEntity<Float> getTotalRevenue(@PathVariable Long competitionId) {
        float totalRevenue = ticketService.getTotalRevenue(competitionId);
        return ResponseEntity.ok(totalRevenue);
    }

    @GetMapping("/occupancy-rate/{competitionId}")
    public ResponseEntity<Double> getOccupancyRate(@PathVariable Long competitionId) {
        double occupancyRate = ticketService.getOccupancyRate(competitionId);
        return ResponseEntity.ok(occupancyRate);
    }
}

