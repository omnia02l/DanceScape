package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.TicketScanner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketScannerRepository extends JpaRepository<TicketScanner,Long> {

    TicketScanner findByAgentName(String name);
}
