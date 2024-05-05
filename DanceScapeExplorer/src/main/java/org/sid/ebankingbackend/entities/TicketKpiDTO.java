package org.sid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TicketKpiDTO {
    private Long competitionId;
    private Long venueId;
    private String competitionName;
    private String venueName;
    private int totalTicketsSold;
    private float totalRevenue;
    private double occupancyRate;
    private int occupiedSeats;
    private int totalSeats;
}
