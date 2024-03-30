package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;


    @Entity
   // @Table(name = "Paymentgateway")
    @Getter
    @Setter
    public class PaymentGateway implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "gateway_name")
        private String gatewayName;

        @Column(name = "api_key")
        private String apiKey;

        @Column(name = "api_secret")
        private String apiSecret;

        @Column(name = "is_active")
        private boolean isActive;

        @OneToOne
        private User user;

    }

