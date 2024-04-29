package org.sid.ebankingbackend.entities;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class TownsandVenuesDTO {
    private Town town;
    private Set<Venue> venues;

    public TownsandVenuesDTO() {}
}