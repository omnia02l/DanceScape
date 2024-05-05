package org.sid.ebankingbackend.services.Store;

import org.sid.ebankingbackend.entities.DeliveryDetail;

public interface IDeliveryDetailService {
    DeliveryDetail saveDeliveryDetail(DeliveryDetail deliveryDetail);
    DeliveryDetail getDeliveryDetailById(Long deliveryDetailId);
}
