package org.sid.ebankingbackend.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikesDislikesDTO {

    @Id
    @GeneratedValue
    private Long id ;
    private int totalLikes;
    private int totalDislikes;

    public LikesDislikesDTO(int totalLikes, int totalDislikes) {
        this.totalLikes = totalLikes;
        this.totalDislikes = totalDislikes;
    }

}
