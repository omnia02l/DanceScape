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
//@Table( name = "Ticketscanner")
public class TicketScanner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idScanner")
    private Long idScanner ;
    private String AgentName;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<PurchaseTransaction> purchaseTransactions ;


}
