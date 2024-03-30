package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table( name = "Purchasetransaction")
public class PurchaseTransaction  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPTransaction")
    private float totalTr;
    private String eticket;
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
    private  Long nbPlace;
    @ManyToOne(cascade = CascadeType.ALL)
    private Ticketcart ticketcart;

}
