package services;

import models.Category;
import models.Product;
import repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.getProducts();
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.getProductsByCategoryId(categoryId);
    }

    public Product getProductById(Long productId) {
        return productRepository.getProductById(productId);
    }

    public Boolean addProduct(String title, String description, Double price, Double quantity, List<Category> categories) {
        Product product = new Product(0L, title, description, price, quantity);

        Long productId = productRepository.addProduct(product);
        if(productId!=-1L)
        {
            Boolean success = true;
            for (Category category : categories) {
                success = (productRepository.addCategoryToProduct(category.getCategoryId(), productId)==false ? false : true);
            }

            return success;
        } else {
            return false;
        }
    }

    public Boolean addCategoryToProduct(Long categoryId, Long productId) {
        return productRepository.addCategoryToProduct(categoryId, productId);
    }

    public Boolean removeCategoryFromProduct(Long categoryId, Long productId) {
        return productRepository.removeCategoryFromProduct(categoryId, productId);
    }

    public Boolean updateProduct(Long productId, String title, String description, Double price, Double quantity, List<Category> categories) {
        Product product = productRepository.getProductById(productId);
        if(product!=null){
            product.setTitle(title);
            product.setDescription(description);
            product.setPrice(price);
            product.setQuantity(quantity);

            productRepository.updateProduct(product, productId);
            List<Long> addCategoryIds = new ArrayList<>();
            List<Long> removeCategoryIds = new ArrayList<>();

            for (Category category : categories) {
                if(category!=null) {
                    if (!productRepository.isProductInCategory(category.getCategoryId(), productId)) {
                        addCategoryIds.add(category.getCategoryId());
                    }
                }
            }

            for (Category category : product.getCategories()) {
                if(category!=null) {
                    if (categories.stream().filter(c -> c.getCategoryId() == category.getCategoryId()).findAny().isEmpty()) {
                        removeCategoryIds.add(category.getCategoryId());
                    }
                }
            }

            for (Long categoryId : addCategoryIds) {
                productRepository.addCategoryToProduct(categoryId, productId);
            }

            for (Long categoryId : removeCategoryIds) {
                productRepository.removeCategoryFromProduct(categoryId, productId);
            }

            return true;

        } else {
            return false;
        }
    }

    public Boolean deleteProduct(Long productId) {
        return productRepository.deleteProduct(productId);
    }
}
