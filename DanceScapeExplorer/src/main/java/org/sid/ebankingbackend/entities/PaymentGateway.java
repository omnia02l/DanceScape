package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;
import java.time.LocalDate;


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
    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String cvc;

    @Column(nullable = false)
    private String TypePayment;

    @Column(nullable = false)
    private String cardholderName;

    @Column
    private LocalDate datepaiement;
}



