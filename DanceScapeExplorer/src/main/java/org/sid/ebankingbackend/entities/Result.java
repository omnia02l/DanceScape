package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultat ;
    private String commentaire ;
    @Transient
    private float resultat ;
    private Integer nombresvotes;
    @Temporal(TemporalType.DATE)
    private Date dateR ;
    @OneToMany(mappedBy = "result")
    private List<ResultComment> resultatcommentaires ;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="result")
    private List<Vote> votes ;
    public void calculerResultat() {
        if (votes != null && !votes.isEmpty()) {
            float totalScore = 0;
            int totalVotes = 0;

            for (Vote vote : votes) {
                totalScore += vote.getScore().getValue();
                totalVotes++;
            }

            resultat = totalScore / totalVotes;
            nombresvotes = totalVotes;
        } else {
            resultat = 0;
            nombresvotes = 0;
        }


    }}
