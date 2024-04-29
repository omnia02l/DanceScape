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
   /* @Query("SELECT tc FROM Ticketcard tc " +
            "JOIN tc.tickets t " +
            "JOIN t.places p " +
            "JOIN p.venuePlan vp " +
            "JOIN vp.venue v " +
            "JOIN v.competitions comp " +
            "WHERE tc.userid = :userId " +
            "AND comp.idcomp = :competitionId " +
            "ORDER BY tc.date DESC")
    List<Ticketcard> findByUserIdAndCompetition(@Param("userId") Long userId, @Param("competitionId") Long competitionId);
}*/
   @Query("SELECT tc FROM Ticketcard tc " +
           "JOIN tc.tickets t " +
           "JOIN t.places p " +
           "JOIN p.venuePlan vp " +
           "JOIN vp.venue v " +
           "JOIN v.competitions c " +
           "WHERE tc.userid = :userId AND c.idcomp = :competitionId " +
           "ORDER BY tc.date DESC")
   List<Ticketcard> findFirstByUseridAndCompetitionOrderByDateDesc(@Param("userId") Long userId, @Param("competitionId") Long competitionId);
}

