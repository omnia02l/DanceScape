package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sid.ebankingbackend.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String orderDescription;

    private Float totalAmount; // Total amount for the order

    private String statusOrder;

    @Temporal(TemporalType.DATE)
    private Date dateCreation;

    private String shippingAddress;
    private String paymentStatus;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = ShoppingCart.class)
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private List<ShoppingCart> cartItems;
    @JsonIgnore

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>(); // Initialize the list here

    @OneToOne
    @JsonIgnore
    private Delivery delivery;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id")
    private User customer;

    public Orders(String orderDescription, User customer, List<ShoppingCart> cartItems) {
        this.orderDescription = orderDescription;
        this.customer = customer;
        this.cartItems = cartItems;
        calculateTotalAmount(); // Calculate total amount when creating the order
    }

    // Method to calculate the total amount based on cart items
    private void calculateTotalAmount() {
        if (cartItems != null && !cartItems.isEmpty()) {
            float total = 0f;
            for (ShoppingCart cart : cartItems) {
                total += cart.getAmount(); // Assuming each cart item has an 'amount' field
            }
            this.totalAmount = total;
        }
    }}
