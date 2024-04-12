package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepo extends JpaRepository<ShoppingCart,Long> {
}
