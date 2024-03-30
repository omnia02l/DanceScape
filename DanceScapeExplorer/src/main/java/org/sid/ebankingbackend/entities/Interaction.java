package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Interaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idInter")
    private Integer idInter;
    private InteractionType interactionType;

    @JsonIgnore
    @ManyToOne
    Post post;

    @JsonIgnore
    @ManyToOne
    private Comment comment;

    @JsonIgnore
    @ManyToOne
    private User user;
}

