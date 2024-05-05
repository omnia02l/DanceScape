package org.sid.ebankingbackend.services.Store;


import org.sid.ebankingbackend.entities.Category;

import java.util.List;

public interface ICategoryService {
    public Category AddCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long categoryId);

    List<Category> getAllCategories();

    Category getCategoryById(Long categoryId);
}
