package org.sid.ebankingbackend.services.Store;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.ImageStore;
import org.sid.ebankingbackend.entities.Product;
import org.sid.ebankingbackend.repository.ImageStoreRepo;
import org.sid.ebankingbackend.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImp implements IProductService {
    private ProductRepo productRepo;
//    private final CloudinaryService cloudinaryService;
    private final ImageStoreRepo imageStoreRepo;
    @Override
    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepo.findById(productId);
        return productOptional.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
   public Product AddProduct(Product product) {
        try {
            return productRepo.save(product);
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            e.printStackTrace();
            throw new RuntimeException("Failed to add product: " + e.getMessage());
        }
    }
  /*  public Product addProduct(Product product, MultipartFile imageFile) {
        try {
            Map uploadResult = cloudinaryService.upload(imageFile);
            ImageStore imageStore = new ImageStore();
            imageStore.setName(imageFile.getOriginalFilename());
            imageStore.setImageUrl(uploadResult.get("url").toString());
            imageStore.setImageId(uploadResult.get("public_id").toString());
            imageStoreRepo.save(imageStore);

            product.setImagestore(imageStore);
            return productRepo.save(product);
        } catch (IOException e) {
            // Log the exception or handle it accordingly
            throw new RuntimeException("Failed to upload image and add product: " + e.getMessage(), e);
        }

}*/

    @Override
    public void deleteProduct(Long productId) {
        productRepo.deleteById(productId);

    }
    @Override
    public List<Product> findTop5SaleProductsByCategory(Long categoryId) {
        return productRepo.findTop5SaleProductsByCategory(categoryId);
    }
    @Transactional
    public void processProductSale(Long productId, int quantitySold) {
        Optional<Product> productOptional = productRepo.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int availableQuantity = product.getQuantity();
            if (availableQuantity >= quantitySold) {
                int newQuantity = availableQuantity - quantitySold;
                product.setQuantity(newQuantity);
                product.setTotalSalesQuantity(product.getTotalSalesQuantity() + quantitySold);
                product.setTotalRevenue(product.getTotalRevenue() + (product.getPrice() * quantitySold));
                product.setLastSoldDate(new Date());
                productRepo.save(product);
            } else {
                throw new RuntimeException("Insufficient stock for product ID: " + productId);
            }
        } else {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }

    /*public List<Product> getWeeklyTopSellingProducts() {
        // Calculate start and end dates for the week
        LocalDate currentDate = LocalDate.now();
        LocalDate weekStartDate = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
        LocalDate weekEndDate = weekStartDate.plusDays(6);

        // Query the repository to get top selling products for the current week
        return productRepo.findTopSellingProductsForWeek(weekStartDate, weekEndDate);
    }*/
    public List<Product> findTopSellingProductsForWeek(Date weekStartDate, Date weekEndDate) {
        return productRepo.findTopSellingProductsForWeek(weekStartDate, weekEndDate);

    }

}
