package org.sid.ebankingbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class RegistrationDTO {
    private Registration registration;
    private Team team;
    private List<Dancer> dancers;


}

