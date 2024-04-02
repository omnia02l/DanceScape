package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;
import java.util.Date;


@Entity
// @Table(name = "Claim")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Claim implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idclaim;
    private String subject;
    private Claimtype claimtype;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date dateclaim;
    private boolean resolved;
    @ManyToOne
    @JsonIgnore
    private User user;


}


