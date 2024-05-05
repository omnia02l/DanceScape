package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String title;
    private String description;
    private Float price;
    @Enumerated(EnumType.STRING)

    private SizeType size;
    private int quantity;
    private String status;
    private Float discountPercentage;
    private Float discountPrice;
      @OneToOne(cascade = CascadeType.ALL)
   private ImageStore imagestore;
    private int totalSalesQuantity;
    private double totalRevenue;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSoldDate;
    @ManyToOne
    @JsonIgnore
    private ShoppingCart shoppingCart;
    @ManyToOne
    @JsonIgnore
    Category category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnore
    private Set<Review> Reviews;
    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Orders> orders;

}