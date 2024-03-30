package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
//@Table( name = "Team")


public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idteam;
    private String teamname;
    private Long nbdancers;
    private String Leadername ;
    private String tdescreption;
    @Enumerated(EnumType.STRING)
    private Teamtype teamtype;
    @Enumerated(EnumType.STRING)
    private Skilllevel  skilllevel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="team")
    @JsonIgnore
    private Set<Registration> registrations;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="team")
    @JsonIgnore
    private Set<Dancer> dancers;
    @ManyToMany(mappedBy="teams", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Competition> competitions;


}