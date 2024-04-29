package org.sid.ebankingbackend.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
//@Table(name = "Town")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Town implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtown;
    private String townname;
    private String country;
    private Long population;
    private String landmarks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "town")
    @JsonIgnore
    private Set<Venue> venues;

}
