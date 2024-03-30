package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DanceHall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idHall")
    private Long idHall;
    private String nameHall;
    private Long capacityHall;
    @Enumerated(EnumType.STRING)
    private StatusHall statusHall;
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="dancehall")
    private Set<Training> Trainings;


}
