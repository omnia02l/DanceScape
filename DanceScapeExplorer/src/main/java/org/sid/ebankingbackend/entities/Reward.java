package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;



    @Entity
    //@Table( name = "Reward")
    @Getter
    @Setter
    public class Reward implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long idreward;

        @Temporal(TemporalType.DATE)

        private Date dateofreward;

        private String descreption;
        @Enumerated(EnumType.STRING)
        private Rewardcategory rewardcateg;

    }

