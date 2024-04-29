package org.sid.ebankingbackend.services.Tickets;


import org.sid.ebankingbackend.entities.TicketScanner;

import java.util.List;

public interface ITicketScannerService {
    public List<TicketScanner> GetAllTicketScanner();
    public TicketScanner GetTicketScanner(String name);
    public TicketScanner AddTicketScanner(TicketScanner ticketScanner);
    public TicketScanner ModifyTicketScanner(TicketScanner ticketScanner);
    public void DeletTicketScanner(Long id);

;
}
