package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.entities.Ticketcard;
import org.sid.ebankingbackend.services.Tickets.TicketCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/TicketCard")
public class TicketCardController {

    TicketCardService ticketCardService;


    @GetMapping("/GetAllTicketCard")
    public List<Ticketcard> GetAllTicketCard() {
        return ticketCardService.GetAllTicketCard();
    }

    @GetMapping("/GetTicketCard/{nb}")
    public Ticketcard GetTicketCard(@PathVariable Long nb) {
        return ticketCardService.GetTicketCard(nb);
    }

  @PostMapping("/AddTicketcard")
    public Ticketcard AddTicketcard(@RequestBody Ticketcard ticketcard) {
        return ticketCardService.AddTicketcard(ticketcard);
    }

    @PutMapping("/ModifyTicketCard")
    public Ticketcard ModifyTicketCard(@RequestBody Ticketcard ticketcard) {
        return ticketCardService.ModifyTicketCard(ticketcard);
    }

   @DeleteMapping("/DeletTicketCard/{id}")
    public void DeletTicketCard(@PathVariable Long id) {
        ticketCardService.DeletTicketCard(id);

    }
    @PostMapping("/createticketCard/{userId}")
    public Ticketcard createAndAssignTicketCardToUser(@PathVariable Long userId) {
        return  ticketCardService.createAndAssignTicketCardToUser(userId);
    }
    @PutMapping("/applyDiscountIfValid/{userId}")
    public Ticketcard applyDiscountToMostRecentTicketCard(@PathVariable Long userId, @RequestBody String discountCode) {

        log.info("Tentative d'application du code de réduction '{}' à l'utilisateur ID: {}", discountCode, userId);

         return  ticketCardService.applyDiscountToMostRecentTicketCard(userId, discountCode);

    }
    @GetMapping("/getTicketCardByUserIdAndDateAndCompteition/{userId}/{competitionId}")
    public Ticketcard getTicketCardByUserIdAndDate(@PathVariable Long userId,@PathVariable Long competitionId){
        return ticketCardService.getTicketCardByUserIdAndCompetitionAndDate(userId,competitionId);

    }


    @GetMapping("/getLastTicketCardDetails/{userId}")
    public Map<String, Object> getLastTicketCardDetails(@PathVariable Long userId) {

            Map<String, Object> ticketCardDetails = ticketCardService.getLastTicketCardDetails(userId);

        return ticketCardDetails;
    }
}