package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeleveryRepo extends JpaRepository<Delivery,Long> {
    List<Delivery> findByDeliveryStatus(String status);

}
