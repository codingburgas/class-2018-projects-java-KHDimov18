package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {
    private Long productId;
    private String title;
    private String description;
    private Double price;
    private Double quantity;
    private List<Category> categories;

    public Product(Long productId, String title, String description, Double price, Double quantity) {
        this.productId = productId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categories = new ArrayList<>();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", categories=" + categories +
                '}';
    }
}
