package org.sid.ebankingbackend.services.Tickets;


import org.sid.ebankingbackend.entities.Ticket;

import java.util.List;

public interface ITicketService {
    public List<Ticket> GetAllTickets();
    public Ticket GetTicket(String ref);
    public Ticket ModifyTicket(Ticket ticket);
    public void DeletTicket(Long id);

}
