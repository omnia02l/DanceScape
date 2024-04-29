package org.sid.ebankingbackend.services.Store;


import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.DTO.OrderDTO;
import org.sid.ebankingbackend.DTO.ResponseOrderDTO;
import org.sid.ebankingbackend.entities.Orders;
import org.sid.ebankingbackend.entities.Product;
import org.sid.ebankingbackend.entities.ShoppingCart;
import org.sid.ebankingbackend.models.User;
import org.sid.ebankingbackend.repository.OrderRepo;
import org.sid.ebankingbackend.repository.ProductRepo;
import org.sid.ebankingbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
                    // Sufficient stock available to fulfill the entire order
                    float cartAmount = product.getPrice() * requestedQuantity;
                    totalCartAmount += cartAmount;

                    // Deduct requestedQuantity from availableQuantity
                    product.setQuantity(availableQuantity - requestedQuantity);
                    productRepository.save(product);

                    cart.setAmount(cartAmount);
                } else {
                    // Not enough stock available to fulfill the entire order
                    float cartAmount = product.getPrice() * availableQuantity;
                    totalCartAmount += cartAmount;

                    // Update cart quantity to reflect the available stock
                    cart.setQuantity(availableQuantity);
                    cart.setAmount(cartAmount);

                    // Set product quantity to 0 since all available stock is being used
                    product.setQuantity(0);
                    productRepository.save(product);
                }



            }
        }
        return totalCartAmount;
    }
//float calculerDistanceLivreurAEsprit(float latt,float longi){
//    float dist=sqrt((latt-36.899566)²+(longi-10.189212)²)
//        return 0;
//}

    public Orders saveOrder(Orders order) {
       // float totalAmount = getCartAmount(order.getCartItems());
     //   order.setTotalAmount(totalAmount);
        order.setDateCreation(new Date());
        return orderRepository.save(order);
    }
     /* public ResponseOrderDTO saveOrder(OrderDTO orderDTO) {
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
        final  int quantitySold;
        int availableQuantity;
        long productId=0;
        for (ShoppingCart cart : cartItems) {
             productId = cart.getProductId();
            // quantitySold = cart.getQuantity();
            Product product = productRepository.findById(productId).orElse(null);

              //  availableQuantity = product.getQuantity() - cart.getQuantity();
                log.info("quantity is: "+product.getQuantity());
                //product.setQuantity(product.getQuantity() - cart.getQuantity());
                log.info("quantity is: "+product.getQuantity());
                product.setTotalSalesQuantity(product.getTotalSalesQuantity() + cart.getQuantity());
                product.setTotalRevenue(product.getTotalRevenue() + (product.getPrice() * cart.getQuantity()));
                product.setLastSoldDate(new Date());
                productRepository.save(product);
                log.info("quantity is: "+product.getQuantity());
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
    /*public List<Orders> getOrderHistoryByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }*/

    public List<Orders> getOrderHistoryByEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }
}
