package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//@Table( name = "Ticket")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTicket")
    private Long idTicket ;
    private String refTicket;
    private boolean disponibility;
    private Date expireDate ;
    @Enumerated(EnumType.STRING)
    private TypeTicket typeTicket;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Places places;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    Price price;





}
