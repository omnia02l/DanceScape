package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Vote implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVote ;
    @Enumerated(EnumType.STRING)
    private Typevotes score ;
    private String com ;
    @Temporal(TemporalType.DATE)
    private Date StartDate ;
    @Temporal(TemporalType.DATE)
    private Date EndDate ;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Result result ;

    @ManyToOne
    private Performance performance ;
    @OneToOne

    private Jury jury ;

}
