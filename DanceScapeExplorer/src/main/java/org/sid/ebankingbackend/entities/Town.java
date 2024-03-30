package org.sid.ebankingbackend.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
//@Table(name = "Town")
@Getter
@Setter
public class Town implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtown;
    private String townname;
    private String country;
    private Long population;
    private String landmarks;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "town")
    private Set<Competition> competitions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "town")
    private Set<Venue> venues;

}
