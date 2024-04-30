package org.sid.ebankingbackend.controllers;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.Orders;
import org.sid.ebankingbackend.services.Store.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Component
@Aspect
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/between")
    public ResponseEntity<List<Orders>> getOrdersBetweenDates(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        List<Orders> orders = orderService.findOrdersBetweenDates(startDate, endDate);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/mostsoldproducts")
    public ResponseEntity<List<Object[]>> getMostSoldProductsByMonthAndYear(
            @RequestParam("month") int month,
            @RequestParam("year") int year) {
        List<Object[]> products = orderService.findMostSoldProductsByMonthAndYear(month, year);
        return ResponseEntity.ok(products);
    }
     /* @GetMapping("/history/{userId}")
    public ResponseEntity<List<Orders>> getOrderHistoryByUserId(@PathVariable Long userId) {
        List<Orders> orderHistory = orderService.getOrderHistoryByUserId(userId);
        if (orderHistory != null && !orderHistory.isEmpty()) {
            return ResponseEntity.ok(orderHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    // Alternatively, you can use email instead of userId
    @GetMapping("/historyByEmail/{email}")
    public ResponseEntity<List<Orders>> getOrderHistoryByEmail(@PathVariable String email) {
        List<Orders> orderHistory = orderService.getOrderHistoryByEmail(email);
        if (orderHistory != null && !orderHistory.isEmpty()) {
            return ResponseEntity.ok(orderHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
