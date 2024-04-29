package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

//@Table( name = "Competition")


public class Competition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcomp;

    @Temporal(TemporalType.DATE)

    private Date startdate;

    @Temporal(TemporalType.DATE)

    private Date enddate;


    @Column(nullable = true)
    private String comprules;

    private Long nbdancers;

    private String compname;

    private String cdescreption;
    @Column(nullable = true)
    private String style;

    @Temporal(TemporalType.DATE)
    private Date regisdeadline;

    @Enumerated(EnumType.STRING)
    private Compstatus compstatus =Compstatus.Ongoing;

    private float feesperparticipant;

    @Enumerated(EnumType.STRING)
    private Agegroup ageg;
    @Column(nullable = true)
    private boolean full;


    @Column(nullable = true)
    private String compimage;
    @Column(nullable = true)
    private String result;
    @Column(nullable = true)
    private Integer totalMaleDancers;
    @Column(nullable = true)
    private Integer totalFemaleDancers;
    @ManyToMany
    @JsonIgnore
    private Set<Team> teams;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competition")

    private Set<Performance> performances;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Dancecategory dancecateg;

    @ManyToOne
    private Town town;



}