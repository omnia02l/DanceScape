package org.sid.ebankingbackend.controllers;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.Category;
import org.sid.ebankingbackend.entities.ImageStore;
import org.sid.ebankingbackend.entities.Product;
import org.sid.ebankingbackend.services.Store.CloudinaryService;
import org.sid.ebankingbackend.services.Store.ICategoryService;
import org.sid.ebankingbackend.services.Store.IProductService;
import org.sid.ebankingbackend.services.Store.ServiceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private CloudinaryService cloudinaryService;

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

 /*   @PostMapping("/add-Product")
    public Product addProducts(@RequestBody Product product) {
        return productService.AddProduct(product);
        //return new ResponseEntity<>(HttpStatus.CREATED);
    }*/

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
  /* @PostMapping("/add")
   public ResponseEntity<Product> addProduct(@RequestParam("image") MultipartFile file,
                                             @RequestBody Product product,
                                             @RequestParam Long categoryId) {
       // Check if the category exists
       Category category = categoryService.getCategoryById(categoryId);
       if (category == null) {
           return ResponseEntity.notFound().build();
       }

       // Set the category for the product
       product.setCategory(category);

       // Save the image data to the product
       try {
           if (file != null) {
               String fileName = StringUtils.cleanPath(file.getOriginalFilename());
               serviceFile.saveFile(file, fileName);
               // Optionally, you can set the file path or URL in the product entity
               // product.setImagePath("path/to/uploaded/file/" + fileName);
           }
       } catch (IOException e) {
           // Handle file storage exception
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }

       // Save the product
       Product savedProduct = productService.AddProduct(product);
       if (savedProduct != null) {
           return ResponseEntity.ok(savedProduct);
       } else {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
   }
*/


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

    /* @PostMapping("/upload")
     public String handleFileUpload(@RequestParam("file") MultipartFile file) {
         if (file.isEmpty()) {
             // Handle empty file upload
             return "redirect:/error";
         }

         try {
             // Save the file to the desired location
             // Here, you can replace "/path/to/save/file" with your desired file path
             String filePath = "/path/to/save/file/" + file.getOriginalFilename();
             file.transferTo(new File(filePath));

             // File uploaded successfully, redirect to success page
             return "redirect:/success";
         } catch (IOException e) {
             // Handle file upload exception
             return "redirect:/error";
         }
     }*/
    @PostMapping("/{productId}/assign-image")
    public ResponseEntity<String> assignImageToProduct(@PathVariable Long productId, @RequestParam MultipartFile imageFile) {
        // Retrieve the product by ID
        Product product = productService.getProductById(productId);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        try {
            // Upload the image to Cloudinary
            Map uploadResult = cloudinaryService.upload(imageFile);

            // Create ImageStore entity
            ImageStore imageStore = new ImageStore();
            imageStore.setName(imageFile.getOriginalFilename());
            imageStore.setImageUrl(uploadResult.get("url").toString());
            imageStore.setImageId(uploadResult.get("public_id").toString());

            // Assign image to product
            product.setImagestore(imageStore);

            // Save product with assigned image
            productService.AddProduct(product);

            return new ResponseEntity<>("Image assigned to product successfully", HttpStatus.OK);
        } catch (IOException e) {
            // Handle file upload or image assignment error
            e.printStackTrace();
            return new ResponseEntity<>("Failed to assign image to product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}