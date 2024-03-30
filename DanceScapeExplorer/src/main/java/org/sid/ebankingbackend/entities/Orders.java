package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;



    private Float totalAmount;
    private String statusOrder;
    private Date dateCreation;
    private String shippingAddress;
    private String paymentStatus;
    @OneToOne(mappedBy="order")
    @JsonIgnore
    private ShoppingCart shoppingcart;
    @OneToMany
    @JsonIgnore
    private List<Product> products;
    @OneToOne
    @JsonIgnore
    private Delivery delivery;
    @ManyToOne
    @JsonIgnore
    User user;

}
