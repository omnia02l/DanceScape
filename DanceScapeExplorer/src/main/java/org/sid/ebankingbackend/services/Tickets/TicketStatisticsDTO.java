package org.sid.ebankingbackend.services.Tickets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TicketStatisticsDTO {

    private String competitionName;
    private Date date;
    private long ticketCount;
}
