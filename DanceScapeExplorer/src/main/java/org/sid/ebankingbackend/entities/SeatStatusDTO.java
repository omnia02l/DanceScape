package org.sid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SeatStatusDTO {
    private Long idPlace;
    private String status;
}
