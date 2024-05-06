package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Places")
public class Places {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPlace")
    private Long idPlace;
    private Long seatNumber; // Changé en String pour des numéros de sièges comme "1", "A1", etc.
    @Enumerated(EnumType.STRING)
    private RowLabel rowLabel;
    private int bookingCount;
    private  boolean isOccupied;
    private  boolean isSelected;
    private Long userid;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plan_id") // Ajout de JoinColumn pour plus de clarté dans la relation
    private VenuePlan venuePlan;

}
