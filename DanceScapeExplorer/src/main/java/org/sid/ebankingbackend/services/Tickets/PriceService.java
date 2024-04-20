package org.sid.ebankingbackend.services.Tickets;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.Price;
import org.sid.ebankingbackend.entities.TrancheAge;
import org.sid.ebankingbackend.entities.TypeTicket;
import org.sid.ebankingbackend.repository.Tickets.PriceRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class PriceService implements IPriceService {

   PriceRepository priceRepository;

    @Override
    public List<Price> GetAllPrice() {
        return priceRepository.findAll();
    }

    @Override
    public Price GetPrice(TypeTicket typeTicket, TrancheAge trancheAge) {
        return priceRepository.findByTypeTicketAndTrancheAge(typeTicket,trancheAge);
    }

    @Override
    public Price GetPricebyID(Long id) {
        return priceRepository.findById(id).get();
    }

    @Override
    public Price AddPrice(Price price) {
        return priceRepository.save(price);
    }

    @Override
    public Price ModifyPrice(Price price) {
        return priceRepository.save(price);
    }

    @Override
    public void DeletPrice(Long id) {
        priceRepository.deleteById(id);

    }
    public Price findPriceForAgeGroupAndType(TrancheAge trancheAge, TypeTicket typeTicket) {
        return priceRepository.findOptionalByTrancheAgeAndTypeTicket(trancheAge, typeTicket)
                .orElseThrow(() -> new EntityNotFoundException("Price not found for the given age group and ticket type"));
    }

    /*
    private static final float DISCOUNT_RATE = 0.25f; // 25% pour réduction ou augmentation

    public float calculatePriceBasedOnAgeChange(Price currentPrice, TrancheAge currentAgeGroup, TrancheAge targetAgeGroup) {
        float basePrice = currentPrice.getPrice();

        // Si le ticket est actuellement pour un enfant et qu'il est changé pour un adulte, augmentez le prix
        if (currentAgeGroup == TrancheAge.ENFANT && targetAgeGroup == TrancheAge.ADULT) {
            return basePrice * (1 + DISCOUNT_RATE); // Ajouter 25%
        }
        // Si le ticket est actuellement pour un adulte et qu'il est changé pour un enfant, réduisez le prix
        else if (currentAgeGroup == TrancheAge.ADULT && targetAgeGroup == TrancheAge.ENFANT) {
            return basePrice * (1 - DISCOUNT_RATE); // Réduire de 25%
        }
        // Pas de changement de prix si la tranche d'âge ne change pas
        return basePrice;
    }*/
}


