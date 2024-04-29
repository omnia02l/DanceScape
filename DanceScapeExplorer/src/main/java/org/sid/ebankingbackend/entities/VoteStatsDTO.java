package org.sid.ebankingbackend.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VoteStatsDTO {
    @Id
    @GeneratedValue
    private Long id ;
    private double averageScore;
    private long totalVotes;

    public VoteStatsDTO(double averageScore, long totalVotes) {
        this.averageScore = averageScore;
        this.totalVotes = totalVotes;
    }

    // Getters and setters
}
