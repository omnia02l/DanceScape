package org.sid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CompetitionPerformanceDTO {
    private String compname;
    private Date startdate;
    private Date enddate;
    private Set<Performance> performances;


    public CompetitionPerformanceDTO() {

    }
}
