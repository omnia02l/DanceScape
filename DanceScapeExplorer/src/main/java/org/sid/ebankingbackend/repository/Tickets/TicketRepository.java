package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.Ticket;
import org.sid.ebankingbackend.services.Tickets.TicketStatisticsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {


    Ticket findByRefTicket(String ref);

    @Transactional
    @Modifying
    @Query("UPDATE Ticket t SET t.disponibility = false ")
    void updateDisponibilityForTicketsBefore(Date date);

    //List<Ticket> findAllByUseridAndDisponibility(Long userId, boolean b);


    @Query("SELECT MAX(t.creationDate) FROM Ticket t WHERE t.userid = :userId")
    Date findLatestCreationDateByUserId(@Param("userId") Long userId);


    List<Ticket> findAllByUseridAndCreationDate(Long userId, Date latestCreationDate);


    @Query("SELECT t.userid, COUNT(t) as ticketCount " +
            "FROM Ticket t WHERE t.creationDate >= :startOfDay " +
            "AND t.creationDate <= :endOfDay " +
            "GROUP BY t.userid ORDER BY ticketCount DESC")
    Page<Object[]> findTopBuyersForToday(@Param("startOfDay") Date startOfDay,
                                         @Param("endOfDay") Date endOfDay,
                                         Pageable pageable);


    @Query("SELECT YEAR(t.creationDate) AS year, COUNT(t) AS ticketCount FROM Ticket t " +
            "JOIN t.places p " +
            "JOIN p.venuePlan vp " +
            "JOIN vp.venue v " +
            "JOIN v.competitions comp " +
            "WHERE comp.style = :styleName AND " +
            "YEAR(comp.startdate) BETWEEN :startYear AND :endYear " +
            "GROUP BY year")
    List<Object[]> countTicketsByStyleAndYear(String styleName, int startYear, int endYear);
    @Query("SELECT comp.style AS styleName, FUNCTION('MONTH', t.creationDate) AS month, COUNT(t) AS ticketCount " +
            "FROM Ticket t " +
            "JOIN t.places p " +
            "JOIN p.venuePlan vp " +
            "JOIN vp.venue v " +
            "JOIN v.competitions comp " +
            "WHERE YEAR(t.creationDate) = :year " +
            "GROUP BY comp.style, month " +
            "ORDER BY comp.style, month")
    List<Object[]> countTicketsByMonthForAllStyles(@Param("year") int year);



    @Query("SELECT new org.sid.ebankingbackend.services.Tickets.TicketStatisticsDTO(c.compname, date(t.creationDate), count(t)) " +
            "FROM Ticket t JOIN t.places p JOIN p.venuePlan vp JOIN vp.venue v JOIN v.competitions c " +
            "WHERE c.idcomp = :competitionId AND t.creationDate BETWEEN (c.startdate - 7) AND c.enddate " +
            "GROUP BY date(t.creationDate), c.compname")
    List<TicketStatisticsDTO> findTicketCountsByCompetition(Long competitionId);


    // Total des Tickets Vendus pour une compétition
    @Query("SELECT count(t) FROM Ticket t " +
            "JOIN t.places p " +
            "JOIN p.venuePlan vp " +
            "JOIN vp.venue v " +
            "JOIN v.competitions c " +
            "WHERE c.idcomp = :competitionId")
    int countTicketsByCompetitionId(@Param("competitionId") Long competitionId);

    // Revenu Total généré par les ventes de tickets pour une compétition
    @Query("SELECT sum(t.price.price) FROM Ticket t " +
            "JOIN t.places p " +
            "JOIN p.venuePlan vp " +
            "JOIN vp.venue v " +
            "JOIN v.competitions c " +
            "WHERE c.idcomp = :competitionId")
    float calculateTotalRevenueByCompetitionId(@Param("competitionId") Long competitionId);

    // Taux d'Occupation des Places pour une compétition
    @Query("SELECT count(t) FROM Ticket t " +
            "JOIN t.places p " +
            "JOIN p.venuePlan vp " +
            "JOIN vp.venue v " +
            "JOIN v.competitions c " +
            "WHERE c.idcomp = :competitionId")
    int countOccupiedSeatsByCompetitionId(@Param("competitionId") Long competitionId);

    // Total des sièges disponibles pour une compétition
    @Query("SELECT vp.totalSeats FROM VenuePlan vp " +
            "JOIN vp.venue v " +
            "JOIN v.competitions c " +
            "WHERE c.idcomp = :competitionId")
    int totalSeatsByCompetitionId(@Param("competitionId") Long competitionId);
}

