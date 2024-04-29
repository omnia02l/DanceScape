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
//@Table(name = "Venue")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idvenue;
    private String vname;
    private String vaddress;
    private Long capacity;
    private String contact_person;
    private String vemail;
    private int phone;
    private String facilities;

    @Column(nullable = true)
    private String venueimage;
    @ManyToOne
    private Town town;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venue")
    @JsonIgnore
    private Set<Competition> competitions;
    public Venue(Long id) {
        this.idvenue = id;
    }

    @OneToOne
    private VenuePlan venuePlan ;



}


