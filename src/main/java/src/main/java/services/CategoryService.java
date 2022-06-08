package services;

import models.Category;
import models.Product;
import repositories.CategoryRepository;
import repositories.ProductRepository;

import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Boolean addCategory(String categoryName){
        Category category = new Category(0L, categoryName);
        return categoryRepository.addCategory(category);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    public List<Category> getCategories() {
        return categoryRepository.getCategories();
    }

    public Boolean updateCategory(Long categoryId, String categoryName) {
        Category category = getCategoryById(categoryId);

        if(category!=null){
            category.setCategoryName(categoryName);
            return categoryRepository.updateCategory(category, categoryId);
        }

        return false;
    }

    public Boolean deleteCategory(Long categoryId){
        return categoryRepository.deleteCategory(categoryId);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }
}
