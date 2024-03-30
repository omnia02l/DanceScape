package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(name = "Deliverydetail")
public class DeliveryDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryDetailId;

    private String nomClient;
    private String zipCode;
    private String adresseLine;
    private String state;
    private String ville;
    @OneToOne
    @JsonIgnore
    private Delivery delivery ;


}
