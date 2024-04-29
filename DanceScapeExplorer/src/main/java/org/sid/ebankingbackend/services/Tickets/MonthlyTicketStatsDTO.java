package org.sid.ebankingbackend.services.Tickets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor


public class MonthlyTicketStatsDTO implements Serializable {
    private int month;
    private long ticketCount;


}
