package repositories;

import models.Customer;
import utils.ApplicationProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    public List<Customer> getCustomers() {
        List<Customer> listOfCustomers = new ArrayList<>();
        String query = "SELECT * FROM customers;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Customer customer = mapToCustomer(resultSet);
                listOfCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfCustomers;
    }

    public Customer getCustomerById(Long customerId) {
        Customer customer = null;
        String query = "SELECT TOP 1 * FROM customers WHERE customerId = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, customerId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                customer = mapToCustomer(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public Long addCustomer(Customer customer) {

        String query = "INSERT INTO Customers (firstName, lastName, phoneNumber) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPhoneNumber());

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

    public Boolean customerExists(Long customerId) {
        Customer customer = getCustomerById(customerId);

        if(customer==null) {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean updateCustomer(Customer customer, Long customerId) {
        if(customerExists(customerId)) {
            String query = "UPDATE customers SET firstName = ?, lastName = ?, phoneNumber = ? WHERE customerId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, customer.getFirstName());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getPhoneNumber());
                ps.setLong(4, customerId);

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

    public Boolean deleteCustomer(Long customerId) {
        if(customerExists(customerId)) {
            String query = "DELETE FROM customers WHERE customerId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setLong(1, customerId);

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

    private Customer mapToCustomer(ResultSet resultSet) throws SQLException {
        Long customerId = resultSet.getLong("customerId");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String phoneNumber = resultSet.getString("phoneNumber");

        Customer customer = new Customer(customerId, firstName, lastName, phoneNumber);
        return customer;
    }
}
