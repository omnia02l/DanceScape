package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.Role;
import org.sid.ebankingbackend.models.User;

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
    private Long idVote;
    private int weight;
    private int score;


    private String com;

    @Temporal(TemporalType.TIMESTAMP)
    private Date voteDate;



    public int getWeightedScore() {
        return this.score * this.weight;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Result result ;

    @ManyToOne
    @JsonIgnore
    private Performance performance ;

    @ManyToOne
    private Role role;

    @ManyToOne
    private User user;



}
