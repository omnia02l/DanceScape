package org.sid.ebankingbackend.repository;

import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product , Long> {
//    @Modifying
//    @Transactional
//    @Query("update Product p set p.quantity = p.quantity - ?2 where p.productId = ?1 and p.quantity >= ?2")
//    int updateProductQuantity(Long productId, int quantitySold);
    @Modifying
    @Transactional
    @Query("update Product p set p.totalSalesQuantity = p.totalSalesQuantity + ?2, p.totalRevenue = p.totalRevenue + ?3, p.lastSoldDate = ?4 where p.productId = ?1")
    int updateSalesHistory(Long productId, int quantitySold, double revenue, Date lastSoldDate);
    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId ORDER BY p.totalSalesQuantity DESC")
    List<Product> findTop5SaleProductsByCategory(@Param("categoryId") Long categoryId);
    @Query("SELECT SUM(p.totalSalesQuantity) FROM Product p")
    int getTotalSales();
    @Query("SELECT DATE_FORMAT(p.lastSoldDate, '%Y-%m') AS month, SUM(p.totalSalesQuantity) AS sales FROM Product p GROUP BY DATE_FORMAT(p.lastSoldDate, '%Y-%m')")
    List<Object[]> getTotalSalesPerMonth();
    /* @Query("SELECT p FROM Product p " +
             "JOIN order_products op " +
             "JOIN op.order o " +
             "WHERE o.dateCreation BETWEEN :weekStartDate AND :weekEndDate " +
             "GROUP BY p.productId " +
             "ORDER BY SUM(p.totalSalesQuantity) DESC")
     List<Product> findTopSellingProductsForWeek(LocalDate weekStartDate, LocalDate weekEndDate);
 */
    @Query("SELECT p FROM Product p " +
            "JOIN p.orders o " +
            "WHERE o.dateCreation BETWEEN :weekStartDate AND :weekEndDate " +
            "GROUP BY p " +
            "ORDER BY SUM(p.totalSalesQuantity) DESC")
    List<Product> findTopSellingProductsForWeek(Date weekStartDate, Date weekEndDate);




/*
SELECT p.* FROM Product p JOIN order_products op ON p.product_id = op.product_id JOIN Orders o ON op.order_id = o.order_id WHERE o.date_creation BETWEEN '2024-03-25' AND '2024-03-31' GROUP BY p.product_id ORDER BY SUM(p.total_sales_quantity) DESC LIMIT 25;
SELECT p.* FROM Product p JOIN order_products op ON p.product_id = op.product_id JOIN Orders o ON op.order_id = o.order_id WHERE o.date_creation BETWEEN '2024-03-25' AND '2024-03-31' GROUP BY p.product_id ORDER BY SUM(p.total_sales_quantity) DESC LIMIT 25;
 */
}


