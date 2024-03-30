package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agreement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idAgr")
    private Long idAgr;
    private String nameSchool;
    private String addrSchool;
    private String emailSchool;
    private String telSchool;
    private StatusArg statusArg;

    @OneToOne(mappedBy="agreement")
    private User user;

}