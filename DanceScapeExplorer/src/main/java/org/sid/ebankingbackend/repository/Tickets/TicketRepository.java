package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.Ticket;
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
    }


