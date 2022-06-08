package repositories;

import models.Product;
import utils.ApplicationProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public List<Product> getProducts() {
        List<Product> listOfProducts = new ArrayList<>();
        String query = "SELECT * FROM products;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Product product = mapToProduct(resultSet);
                listOfProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfProducts;
    }

    public Product getProductById(Long productId) {
        Product product = null;
        String query = "SELECT TOP 1 * FROM products WHERE productId = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, productId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                product = mapToProduct(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public Long addProduct(Product product) {

        String query = "INSERT INTO Product (title, description, price, quantity) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, product.getTitle());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setDouble(4, product.getQuantity());

            int row = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            // rows affected
            if(row==1) {
                rs.next();
                return rs.getLong(1);
            }
            else{
                return -1L;
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1L;
    }

    public Boolean productExists(Long productId) {
        Product product = getProductById(productId);

        if(product==null) {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean isProductInCategory(Long categoryId, Long productId) {
        String query = "SELECT TOP 1 * FROM categories_product WHERE categoryId = ? AND productId = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, categoryId);
            ps.setLong(2, productId);

            ResultSet resultSet = ps.executeQuery();
            Boolean hasResult = false;
            while (resultSet.next()) {
                hasResult = true;
            }

            return hasResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public Boolean updateProduct(Product product, Long productId) {
        if(productExists(productId)) {
            String query = "UPDATE products SET title = ?, description = ?, price = ?, quantity = ? WHERE productId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, product.getTitle());
                ps.setString(2, product.getDescription());
                ps.setDouble(3, product.getPrice());
                ps.setDouble(4, product.getQuantity());
                ps.setLong(5, productId);

                int row = ps.executeUpdate();

                // rows affected
                if(row==1) {
                    return true;
                }
                else{
                    return false;
                }

            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public Boolean deleteProduct(Long productId) {
        if(productExists(productId)) {
            String query = "DELETE FROM products WHERE productId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setLong(1, productId);

                int row = ps.executeUpdate();

                // rows affected
                if(row==1) {
                    return true;
                }
                else{
                    return false;
                }

            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public List<Product> getProductsByCategoryId(Long categoryId)
    {
        List<Product> listOfProducts = new ArrayList<>();
        String query = "SELECT * FROM products AS p INNER JOIN categories_products AS cp ON p.productId=cp.productId WHERE categoryId=?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, categoryId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Product product = mapToProduct(resultSet);
                listOfProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfProducts;
    }

    public Boolean addCategoryToProduct(Long categoryId, Long productId) {
        String query = "INSERT INTO categories_products (categoryId, productId) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, categoryId);
            ps.setLong(2, productId);

            int row = ps.executeUpdate();

            // rows affected
            if(row==1) {
                return true;
            }
            else{
                return false;
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean removeCategoryFromProduct(Long categoryId, Long productId) {
        String query = "DELETE FROM categories_products WHERE categoryId=? AND productId=?";
        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, categoryId);
            ps.setLong(2, productId);

            int row = ps.executeUpdate();

            // rows affected
            if(row==1) {
                return true;
            }
            else{
                return false;
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Product mapToProduct(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("productId");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        Double price = resultSet.getDouble("price");
        Double quantity = resultSet.getDouble("quantity");

        Product product = new Product(productId, title, description, price, quantity);
        return product;
    }
}

