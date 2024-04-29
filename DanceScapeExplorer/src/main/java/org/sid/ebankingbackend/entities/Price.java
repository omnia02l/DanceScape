package org.sid.ebankingbackend.entities;

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
@Table( name = "Price")
public class Price  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPrice")
    private Long idPrice ;
    private float price;

    @Enumerated(EnumType.STRING)
    private TrancheAge trancheAge;

    @Enumerated(EnumType.STRING)
    private TypeTicket typeTicket;






}
