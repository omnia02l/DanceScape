package org.sid.ebankingbackend.controllers;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.Category;
import org.sid.ebankingbackend.entities.Product;
import org.sid.ebankingbackend.services.Store.ICategoryService;
import org.sid.ebankingbackend.services.Store.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
@Component
@Aspect
@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired

    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add-Product")
    public Product addProducts(@RequestBody Product product) {
        return productService.AddProduct(product);
        //return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Product existingProduct = productService.getProductById(productId);
        if (existingProduct != null) {
            product.setProductId(productId);
            productService.AddProduct(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, @RequestParam Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setCategory(category);
        Product savedProduct = productService.AddProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/top5sale/{categoryId}")
    public ResponseEntity<List<Product>> getTop5SaleProductsByCategory(@PathVariable Long categoryId) {
        System.out.println("Received categoryId: " + categoryId); // Log the received categoryId
        List<Product> top5SaleProducts = productService.findTop5SaleProductsByCategory(categoryId);
        return ResponseEntity.ok(top5SaleProducts);
    }
    /* @GetMapping("/weekly-top-selling")
     public List<Product> getWeeklyTopSellingProducts() {
         return productService.getWeeklyTopSellingProducts();
     }*/

    @GetMapping("/weekly-top-selling")
    public List<Product> getWeeklyTopSellingProducts() {
        LocalDate currentDate = LocalDate.now();
        LocalDate weekStartDate = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
        LocalDate weekEndDate = weekStartDate.plusDays(6);

        // Convert LocalDate to Date
        Date sqlWeekStartDate = Date.valueOf(weekStartDate);
        Date sqlWeekEndDate = Date.valueOf(weekEndDate);

        return productService.findTopSellingProductsForWeek(sqlWeekStartDate, sqlWeekEndDate);
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        // Check if file is empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload an image");
        }

        // You can add logic here to save the file to your desired location
        // For simplicity, let's just return the filename
        String filename = file.getOriginalFilename();
        return ResponseEntity.ok().body(filename);
    }

}
