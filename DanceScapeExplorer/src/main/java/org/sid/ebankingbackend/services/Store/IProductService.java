package org.sid.ebankingbackend.services.Store;


import org.sid.ebankingbackend.entities.Product;

import java.util.Date;
import java.util.List;

public interface IProductService {
    Product getProductById(Long productId);
    List<Product> getAllProducts();
    public Product AddProduct(Product product);
    void deleteProduct(Long productId);
    List<Product> findTop5SaleProductsByCategory(Long categoryId);
    // Map<String, Integer> getTotalSalesPerMonth();
    void processProductSale(Long productId, int quantitySold);
    //  int getTotalSales();
    // List<Map<String, Object>> getTotalSalesPerMonth();
    //  void updateProductSales(Long productId, int quantitySold, double revenue);
    List<Product> findTopSellingProductsForWeek(Date weekStartDate, Date weekEndDate);


    }
