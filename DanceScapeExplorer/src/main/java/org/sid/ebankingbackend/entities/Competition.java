package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Entity
@Setter
@Getter
//@Table( name = "Competition")


public class Competition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcomp;
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String comprules;
    private Long nbdancers;
    private String compname;
    private String cdescreption;
    @Temporal(TemporalType.DATE)
    private Date Regis_deadline;
    @Enumerated(EnumType.STRING)
    private Compstatus compstatus;
    private float Fees_per_participant;
    @Enumerated(EnumType.STRING)
    private Agegroup ageg;
    @Lob
    @Column(name = "compimage", columnDefinition = "BLOB")
      private byte[] compimage;
    private String result;
    @ManyToMany
    @JsonIgnore
    private Set<Team> teams;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "competition")
    @JsonIgnore
    private Set<Performance> performance;
    @ManyToOne(cascade = CascadeType.ALL)
    private Dancecategory dancecateg;
    @ManyToOne
    private Town town;













}