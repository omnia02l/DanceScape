package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    //@Table(name = "Venueplan")
    public class Venueplan implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_venue_plan")
        private Long idVenuePlan;

        @Lob
        @Column(name = "plan_image", columnDefinition = "BLOB")
        private byte[] planImage;
        private String description;
        @Enumerated(EnumType.STRING)
        private Zone zone;
        @Enumerated(EnumType.STRING)
        private Row row;
        @OneToOne
        private Venue venue ;
        @OneToMany(cascade = CascadeType.ALL, mappedBy="venueplan")
        @JsonIgnore
        private Set<Places> places;
}
