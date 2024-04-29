package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.TicketScanner;
import org.sid.ebankingbackend.services.Tickets.TicketScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/TicketScanner")
public class TicketScannerController  {
    @Autowired
    TicketScannerService ticketScannerService;

    @GetMapping("/GetAllTicketScanner")
    public List<TicketScanner> GetAllTicketScanner() {
        return ticketScannerService.GetAllTicketScanner();
    }

  @GetMapping("/GetTicketScanner/{name}")
    public TicketScanner GetTicketScanner(@PathVariable  String name) {
        return ticketScannerService.GetTicketScanner(name);
    }

   @PostMapping("/AddTicketScanner")
    public TicketScanner AddTicketScanner(@RequestBody TicketScanner ticketScanner) {
        return ticketScannerService.AddTicketScanner(ticketScanner);
    }

    @PutMapping("/ModifyTicketScanner")
    public TicketScanner ModifyTicketScanner(@RequestBody TicketScanner ticketScanner) {
        return ticketScannerService.ModifyTicketScanner(ticketScanner);
    }

    @DeleteMapping("/DeletTicketScanner/{id}")
    public void DeletTicketScanner(Long id) {
        ticketScannerService.DeletTicketScanner(id);

    }
}