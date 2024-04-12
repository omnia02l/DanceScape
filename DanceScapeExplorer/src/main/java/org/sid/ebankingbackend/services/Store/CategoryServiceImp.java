package org.sid.ebankingbackend.services.Store;


import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Category;
import org.sid.ebankingbackend.repository.CategoryRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements ICategoryService{

    private CategoryRepo categoryRepo;

    @Override
    public Category AddCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepo.findById(categoryId);
        return categoryOptional.orElse(null);
    }
}
