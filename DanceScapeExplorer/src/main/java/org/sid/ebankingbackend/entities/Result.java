package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long idResultat;
    private float resultat;
    private Integer nombresvotes;
    @Temporal(TemporalType.DATE)
    private Date dateR;
    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    private List<ResultComment> resultatcommentaires;


    private int totalScore;
    private int score;
    private int likes;
    private int dislikes;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="result")
    @JsonIgnore
    private List<Vote> votes ;

    public void updateResult() {
        int totalScore = 0;
        for (Vote vote : performance.getVotes()) {
            totalScore += vote.getScore();
        }
        // Mise à jour du résultat
        if (!performance.getVotes().isEmpty()) {
            resultat = (float) totalScore / performance.getVotes().size();
        } else {
            resultat = 0; // Gérer le cas où il n'y a pas de votes
        }
    }
}
