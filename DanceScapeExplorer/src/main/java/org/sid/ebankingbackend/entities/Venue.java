package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;





    @Entity
    //@Table(name = "Venue")
    @Getter
    @Setter
    public class Venue implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idvenue;
        private String vaddress;
        private Long capacity;
        private String contact_person;
        private String vemail;
        private int phone;
        private String facilities;
        @Lob
        @Column(name = "venueimage", columnDefinition = "BLOB")
        private byte[] venueimage;
        @ManyToOne
        private Town town;


    }


