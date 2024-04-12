package org.sid.ebankingbackend.controllers;


import org.sid.ebankingbackend.DTO.OrderDTO;
import org.sid.ebankingbackend.DTO.ResponseOrderDTO;
import org.sid.ebankingbackend.Outil.DateUtil;
import org.sid.ebankingbackend.entities.Orders;
import org.sid.ebankingbackend.entities.Product;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.services.Store.OrderService;
import org.sid.ebankingbackend.services.Store.ProductServiceImp;
import org.sid.ebankingbackend.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class ShoppingCartRestController {

    private final OrderService orderService;
    private final ProductServiceImp productService;
    private final UserDetailsServiceImpl customerService;

    private final Logger logger = LoggerFactory.getLogger(ShoppingCartRestController.class);

    public ShoppingCartRestController(OrderService orderService, ProductServiceImp productService, UserDetailsServiceImpl customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping(value = "/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping(value = "/getOrder/{orderId}")
    public ResponseEntity<Orders> getOrderDetails(@PathVariable Long orderId) {
        Orders order = orderService.getOrderDetail(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<ResponseOrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("Received order request: {}", orderDTO);
      //  ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

        float amount = orderService.getCartAmount(orderDTO.getCartItems());

        User customer = new User(orderDTO.getCustomerName(), orderDTO.getCustomerEmail());
        Long customerIdFromDb = customerService.isCustomerPresent(customer);

        if (customerIdFromDb != null) {
            customer.setId(customerIdFromDb);
            logger.info("Customer already exists in the database with ID: {}", customerIdFromDb);
        } else {
            customer = customerService.saveCustomer(customer);
            logger.info("New customer saved with ID: {}", customer.getId());
        }

        Orders order = new Orders(orderDTO.getOrderDescription(), customer, orderDTO.getCartItems());
        try {
            order = orderService.saveOrder(order);
            logger.info("Order processed successfully");
            orderService.updateProductSales(orderDTO.getCartItems());
            orderService.populateOrderProducts(order, orderDTO.getCartItems());

        } catch (Exception e) {
            logger.error("Failed to process order: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

        responseOrderDTO.setAmount(amount);
        responseOrderDTO.setDate(DateUtil.getCurrentDateTime());
        responseOrderDTO.setInvoiceNumber(new Random().nextInt(1000));
        responseOrderDTO.setOrderId(order.getOrderId());
        responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());

        logger.info("Order placed successfully: {}", responseOrderDTO);
        return ResponseEntity.ok(responseOrderDTO);
    }

}
