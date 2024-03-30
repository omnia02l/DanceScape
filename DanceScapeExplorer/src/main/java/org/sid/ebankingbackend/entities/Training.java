package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table( name = "Training")
public class Training implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTr")
    private Long idTr;
    private String nameTr;
    private String descriptionTr;
    @Temporal(TemporalType.DATE)
    private Date dateStartTr;
    @Temporal(TemporalType.DATE)
    private Date dateEndTr;
    @Enumerated(EnumType.STRING)
    private LevelType levelType;
    private float priceTr;
    @Enumerated(EnumType.STRING)
    private StatusTraining statusTr;
    private String imageTr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="training")
    private Set<Booking> Bookings;

    @ManyToOne
    User user;
    @ManyToOne
    DanceHall dancehall;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="training")
    private Set<Planning> Plannings;

    @ManyToOne
    Dancecategory dancecategory;

}
