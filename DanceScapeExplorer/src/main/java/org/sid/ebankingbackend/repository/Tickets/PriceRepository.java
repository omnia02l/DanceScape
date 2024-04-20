package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.Price;
import org.sid.ebankingbackend.entities.TrancheAge;
import org.sid.ebankingbackend.entities.TypeTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price,Long> {

    Price findByTypeTicketAndTrancheAge(TypeTicket typeTicket, TrancheAge trancheAge);

 


    Price findByTrancheAgeAndTypeTicket(TrancheAge adult, TypeTicket typeTicket);

    @Query("SELECT p FROM Price p WHERE p.trancheAge = :trancheAge AND p.typeTicket = :typeTicket")
    Optional<Price> findOptionalByTrancheAgeAndTypeTicket(
            @Param("trancheAge") TrancheAge trancheAge,
            @Param("typeTicket") TypeTicket typeTicket);
}
