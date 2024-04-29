package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table( name = "Ticket")
//@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTicket")
    private Long idTicket ;
    private String refTicket;
    private boolean disponibility;
    private boolean scanned;
    @Lob
    @Column(name = "qr_code", columnDefinition = "MEDIUMBLOB")
    private byte[] qrCode;

    @Transient // Ce champ n'a pas besoin d'être persisté en base de données
    private String qrCodeBase64;

    @JsonIgnore
    private Date expireDate ;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private Long userid;
    @Enumerated(EnumType.STRING)
    private TypeTicket typeTicket;

    @ManyToOne(cascade = CascadeType.ALL)
    Places places;


    @ManyToOne(cascade = CascadeType.ALL)
    Price price;




}

