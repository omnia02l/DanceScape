package org.sid.ebankingbackend.services.Store;


import jakarta.transaction.Transactional;
import org.sid.ebankingbackend.entities.Orders;
import org.sid.ebankingbackend.entities.Product;
import org.sid.ebankingbackend.entities.ShoppingCart;
import org.sid.ebankingbackend.repository.OrderRepo;
import org.sid.ebankingbackend.repository.ProductRepo;
import org.sid.ebankingbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepo orderRepository;
    private ProductRepo productRepository;
private UserRepository customerRepository;
    public OrderService(OrderRepo orderRepository, ProductRepo productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Orders getOrderDetail(Long orderId) {
        Optional<Orders> order = this.orderRepository.findById(orderId);
        return order.isPresent() ? order.get() : null;
    }

    public float getCartAmount(List<ShoppingCart> shoppingCartList) {
        float totalCartAmount = 0f;

        for (ShoppingCart cart : shoppingCartList) {
            Long productId = cart.getProductId();
            Optional<Product> productOptional = productRepository.findById(productId);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                int availableQuantity = product.getQuantity();
                int requestedQuantity = cart.getQuantity();

                if (availableQuantity >= requestedQuantity) {
                    float cartAmount = product.getPrice() * requestedQuantity;
                    totalCartAmount += cartAmount;
                    product.setQuantity(availableQuantity - requestedQuantity);
                    productRepository.save(product);
                    cart.setAmount(cartAmount);
                } else {
                    float cartAmount = product.getPrice() * availableQuantity;
                    totalCartAmount += cartAmount;
                    cart.setQuantity(availableQuantity);
                    cart.setAmount(cartAmount);
                }
            }
        }
        return totalCartAmount;
    }


    public Orders saveOrder(Orders order) {
        float totalAmount = getCartAmount(order.getCartItems());
        order.setTotalAmount(totalAmount);
        order.setDateCreation(new Date());
        return orderRepository.save(order);
    }
    /*    public ResponseOrderDTO saveOrder(OrderDTO orderDTO) {
        User customer = customerRepository.findByEmail(orderDTO.getCustomerEmail());
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with email: " + orderDTO.getCustomerEmail());
        }

        // Calculate total cart amount and update product quantities
        float totalCartAmount = getCartAmount(orderDTO.getCartItems());

        // Save the order
        Orders order = new Orders(orderDTO.getOrderDescription(), customer, orderDTO.getCartItems());
        Orders savedOrder = orderRepository.save(order);

        // Update product sales-related fields
        updateProductSales(orderDTO.getCartItems());

        // Prepare and return the response DTO
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setOrderId(savedOrder.getOrderId());
        responseOrderDTO.setAmount(totalCartAmount); // Update this line

        return responseOrderDTO;
    }*/


    public void updateProductSales(List<ShoppingCart> cartItems) {
        for (ShoppingCart cart : cartItems) {
            Long productId = cart.getProductId();
            int quantitySold = cart.getQuantity();
            Optional<Product> productOptional = productRepository.findById(productId);
            productOptional.ifPresent(product -> {
                int availableQuantity = product.getQuantity() - quantitySold;
                product.setQuantity(availableQuantity);
                product.setTotalSalesQuantity(product.getTotalSalesQuantity() + quantitySold);
                product.setTotalRevenue(product.getTotalRevenue() + (product.getPrice() * quantitySold));
                product.setLastSoldDate(new Date());
                productRepository.save(product);
            });
        }
    }
    /**/ public List<Orders> findOrdersBetweenDates(Date startDate, Date endDate) {
        return orderRepository.findByDateCreationBetween(startDate, endDate);
    }

    public List<Object[]> findMostSoldProductsByMonthAndYear(int month, int year) {
        return orderRepository.findSalesCountByProductAndMonth(month, year);
    }

    @Transactional
    public void populateOrderProducts(Orders order, List<ShoppingCart> cartItems) {
        for (ShoppingCart cart : cartItems) {
            Long productId = cart.getProductId();
            Optional<Product> productOptional = productRepository.findById(productId);
            productOptional.ifPresent(product -> order.getProducts().add(product));
        }
    }
}
