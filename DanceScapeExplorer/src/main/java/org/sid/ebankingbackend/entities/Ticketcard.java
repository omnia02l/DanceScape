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
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "Ticketcard")
public class Ticketcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idCardT")
    private Long idCardT ;
    private float Total;
    private Long userid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Ticket> tickets;


    @OneToMany(cascade = CascadeType.ALL)
    private Set<PurchaseTransaction> purchaseTransactions ;

    @JsonIgnore
    @ManyToOne
    private User user;








}