package org.sid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompetitionWithVenueDTO {
    private Long competitionId;
    private String competitionName;
    private String competitionDescription;

    private Long venueId;
    private String venueName;
    private String venueAddress;

    private Long townId;
    private String townName;

    public CompetitionWithVenueDTO() {}
}

