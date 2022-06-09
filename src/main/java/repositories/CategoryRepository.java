package repositories;

import models.Category;
import utils.ApplicationProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    public List<Category> getCategories(){
        List<Category> listOfCategories = new ArrayList<>();
        String query = "SELECT * FROM categories;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Category category = mapToCategory(resultSet);
                listOfCategories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfCategories;

    }

    public Category getCategoryById(Long categoryId){
        Category category = null;
        String query = "SELECT TOP 1 * FROM categories WHERE categoryId = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, categoryId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                category = mapToCategory(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;

    }

    public Boolean addCategory(Category category) {
        String query = "INSERT INTO categories (categoryName) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query)) {


            ps.setString(1, category.getCategoryName());

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

    public Boolean categoryExists(Long categoryId) {

        Category category = getCategoryById(categoryId);

        if(category==null) {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Boolean updateCategory(Category category, Long categoryId) {
        if(categoryExists(categoryId)) {
            String query = "UPDATE categories SET categoryName = ? WHERE categoryId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, category.getCategoryName());
                ps.setLong(2, categoryId);

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

    public Boolean deleteCategory(Long categoryId) {
        if(categoryExists(categoryId)) {
            String query = "DELETE FROM categories WHERE categoryId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setLong(1, categoryId);

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

    private Category mapToCategory(ResultSet resultSet) throws SQLException  {
        Long categoryId = resultSet.getLong("categoryId");
        String categoryName = resultSet.getString("categoryName");

        Category category = new Category(categoryId, categoryName);
        return category;

    }
}
