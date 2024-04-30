package org.sid.ebankingbackend.services.Store;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Delivery;
import org.sid.ebankingbackend.repository.DeleveryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class DeliveryServiceImp implements IDeliveryService{
    private DeleveryRepo deliveryRepository;


    @Override
    public Delivery createDelivery(Delivery delivery) {
        if (delivery.getDeliveryForm() == null || delivery.getShippingType() == null) {
            throw new IllegalArgumentException("Delivery form and shipping type are required.");
        }
        // Save the delivery
        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery getDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElse(null);
    }

    @Override
    public void updateDelivery(Delivery delivery) {
        // Implement update logic
    }

    @Override
    public void deleteDelivery(Long deliveryId) {
        // Implement delete logic
    }

    @Override
    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }
}