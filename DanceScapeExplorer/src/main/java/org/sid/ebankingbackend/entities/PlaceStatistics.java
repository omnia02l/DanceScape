package org.sid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PlaceStatistics {
    private List<Places> neverBooked;
    private List<Places> bookedOnce;
    private List<Places> bookedMoreThanOnce;

}
