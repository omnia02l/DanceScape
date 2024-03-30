package org.sid.ebankingbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table( name = "Shoppingcart")

public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private double totalPrice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="shoppingcart")
    private Set<Product> Products;
    @OneToOne
    private Orders order;

}
