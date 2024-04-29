package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.Places;
import org.sid.ebankingbackend.entities.RowLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Places,Long> {


    Places findBySeatNumber(Long nb);



    List<Places> findByVenuePlan_IdPlan(Long planId);



    Places findBySeatNumberAndRowLabel(Long seatNumber, RowLabel row);


    @Query("SELECT p FROM Places p WHERE p.idPlace IN :ids")
    List<Places> findAllByIdPlace(@Param("ids") List<Long> ids);

    List<Places> findAllByIsSelectedTrueAndIsOccupiedFalse();

    Places findBySeatNumberAndRowLabelAndVenuePlan_IdPlan(Long seatNumber, RowLabel row, Long venuePlanId);


}
