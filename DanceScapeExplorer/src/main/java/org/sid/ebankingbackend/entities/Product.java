package org.sid.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Product  implements Serializable {
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
    private String image;
    @ManyToOne
    @JsonIgnore
    ShoppingCart shoppingcart;
    @ManyToOne
    @JsonIgnore
    Category category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="product")
    @JsonIgnore
    private Set<Review> Reviews;
}
