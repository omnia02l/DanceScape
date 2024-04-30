package org.sid.ebankingbackend.services.Store;

import org.sid.ebankingbackend.entities.Delivery;

import java.util.List;

public interface IDeliveryService {
    Delivery createDelivery(Delivery delivery);
    Delivery getDeliveryById(Long deliveryId);
    void updateDelivery(Delivery delivery);
    void deleteDelivery(Long deliveryId);
    List<Delivery> getAllDeliveries();
}
