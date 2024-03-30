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
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    private String deliveryStatus;
    private String deliveryForm;
    @Enumerated(EnumType.STRING)
    private ShippingType shippingType;
    private String trackingNumber;
    @OneToOne(mappedBy="delivery")
    @JsonIgnore
    private Orders order;
}
