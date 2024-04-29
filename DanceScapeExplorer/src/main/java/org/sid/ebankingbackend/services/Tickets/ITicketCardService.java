package org.sid.ebankingbackend.services.Tickets;


import org.sid.ebankingbackend.entities.Ticketcard;

import java.util.List;

public interface ITicketCardService {
    public List<Ticketcard> GetAllTicketCard();
    public Ticketcard GetTicketCard(Long nb);
    public Ticketcard AddTicketcard(Ticketcard ticketcard);
    public Ticketcard ModifyTicketCard(Ticketcard ticketcard);
    public void DeletTicketCard(Long id);

}
