package org.sid.ebankingbackend.services.Store;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.DeliveryDetail;
import org.sid.ebankingbackend.repository.DeliveryDetailRepo;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryDetailServiceImp implements IDeliveryDetailService {
    private DeliveryDetailRepo deliveryDetailRepo;


    @Override
    public DeliveryDetail saveDeliveryDetail(DeliveryDetail deliveryDetail) {
        if (deliveryDetail.getNomClient() == null || deliveryDetail.getZipCode() == null) {
            throw new IllegalArgumentException("Client name and zip code are required.");
        }
        // Save the delivery detail
        return deliveryDetailRepo.save(deliveryDetail);
    }

    @Override
    public DeliveryDetail getDeliveryDetailById(Long deliveryDetailId) {
        return deliveryDetailRepo.findById(deliveryDetailId).orElse(null);
    }
}