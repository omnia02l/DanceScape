package org.sid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
public class TownWithVenuesDTO {
    private String townName;
    private Set<Venue> venues;

public TownWithVenuesDTO() {}

}

