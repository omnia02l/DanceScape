package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Set;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Table( name = "Performance")


public class Performance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idperf;

    @Temporal(TemporalType.DATE)
    private Date perfdate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;

    private String pdescreption;
    private String perftitle;
    @Lob
    @Column(name = "teamimage", columnDefinition = "BLOB",nullable = true)
    private byte[] teamimage;
    @Column(nullable = true)
    private Long idTeam;

    @ManyToOne
    @JsonIgnore
    private Competition competition;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Music music;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "performance")
    @JsonIgnore
    private Set<Vote> votes;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reward> rewards;










}



