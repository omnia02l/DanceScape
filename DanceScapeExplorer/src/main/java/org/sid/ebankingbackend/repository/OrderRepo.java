package org.sid.ebankingbackend.repository;

import org.sid.ebankingbackend.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepo extends JpaRepository<Orders, Long> {
    List<Orders> findByCustomerEmail(String email);

    List<Orders> findByDateCreationBetween(Date startDate, Date endDate);

    List<Orders> findByOrderId(Long orderId);

    @Query("SELECT p.title, COUNT(o) AS salesCount " +
            "FROM Orders o JOIN o.products p " +
            "WHERE MONTH(o.dateCreation) = :month AND YEAR(o.dateCreation) = :year " +
            "GROUP BY p.title " +
            "ORDER BY COUNT(o) DESC")
    List<Object[]> findSalesCountByProductAndMonth(int month, int year);
    //  List<Orders> findByUserId(Long userId);
    // List<Orders> findByUserEmail(String email);

}