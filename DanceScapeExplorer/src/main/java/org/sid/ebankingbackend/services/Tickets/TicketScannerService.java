package org.sid.ebankingbackend.services.Tickets;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.TicketScanner;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.repository.Tickets.TicketScannerRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class TicketScannerService implements ITicketScannerService{

    TicketScannerRepository ticketScannerRepository;

    @Override
    public List<TicketScanner> GetAllTicketScanner() {
        return ticketScannerRepository.findAll();
    }

    @Override
    public TicketScanner GetTicketScanner(String name) {
        return ticketScannerRepository.findByAgentName(name);
    }

    @Override
    public TicketScanner AddTicketScanner(TicketScanner ticketScanner) {
        return ticketScannerRepository.save(ticketScanner);
    }

    @Override
    public TicketScanner ModifyTicketScanner(TicketScanner ticketScanner) {
        return ticketScannerRepository.save(ticketScanner);
    }

    @Override
    public void DeletTicketScanner(Long id) {
        ticketScannerRepository.deleteById(id);

    }




}


