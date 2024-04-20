package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.Ticketcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TicketCardRepository extends JpaRepository<Ticketcard,Long> {


    @Query("SELECT tc FROM Ticketcard tc JOIN tc.tickets t WHERE t.idTicket = :ticketId")
    Optional<Ticketcard> findTicketCardByTicketId(@Param("ticketId") Long ticketId);

    List<Ticketcard> findByUserid(Long userId);

    Ticketcard findFirstByUseridOrderByDateDesc(Long userId);
}
