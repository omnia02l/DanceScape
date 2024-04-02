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
    private Time starttime;
    private Time endtime;
    private String pdescreption;
    private String perftitle;
    @Lob
    @Column(name = "teamimage", columnDefinition = "BLOB",nullable = true)
    private byte[] teamimage;

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



