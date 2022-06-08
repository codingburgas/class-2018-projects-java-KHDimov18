package repositories;

import models.Order;
import utils.ApplicationProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    public List<Order> getOrders() {
        List<Order> listOfOrders = new ArrayList<>();
        String query = "SELECT * FROM orders;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Order order = mapToOrder(resultSet);
                listOfOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfOrders;
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        List<Order> listOfOrders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customerId=?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, customerId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Order order = mapToOrder(resultSet);
                listOfOrders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfOrders;
    }

    public Order getOrderById(Long orderId) {
        Order order = null;
        String query = "SELECT TOP 1 * FROM orders WHERE orderId = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, orderId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                order = mapToOrder(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    public Long addOrder(Order order) {

        String query = "INSERT INTO Orders (deliveryAddress, shipmentDate) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, order.getDeliveryAddress());
            ps.setDate(2, (Date) order.getShipmentDate());


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

    public Boolean addToOrder(Long orderId, Long productId, Double quantity) {
        String query = "INSERT INTO orders_products (orderId, productId, quantity) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, orderId);
            ps.setLong(2, productId);
            ps.setDouble(3, quantity);


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

    public Boolean removeProductFromOrder(Long orderId, Long productId) {
        String query = "DELETE FROM orders_products WHERE orderId=? AND productId=?";
        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, orderId);
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

    public Boolean updateProductByOrder(Long orderId, Long productId, Double quantity) {
        if(quantity <= 0) {
            return false;
        }

        if(orderExists(orderId)) {
            String query = "UPDATE orders_products SET quantity = ? WHERE orderId = ? ANd productId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setDouble(1, quantity);
                ps.setLong(2, orderId);
                ps.setLong(3, productId);

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
    public Boolean orderExists(Long orderId) {
        Order order = getOrderById(orderId);

        if(order==null) {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean updateOrder(Order order, Long orderId) {
        if(orderExists(orderId)) {
            String query = "UPDATE orders SET deliveryAddress = ?, shipmentDate = ? WHERE orderId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setString(1, order.getDeliveryAddress());
                ps.setDate(2, (Date) order.getShipmentDate());
                ps.setLong(3, orderId);

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

    public Boolean deleteOrder(Long orderId) {
        if(orderExists(orderId)) {
            String query = "DELETE FROM orders WHERE orderId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setLong(1, orderId);

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

    private Order mapToOrder(ResultSet resultSet) throws SQLException {
        Long orderId = resultSet.getLong("orderId");
        Long customerId = resultSet.getLong("customerId");
        String deliveryAddress = resultSet.getString("deliveryAddress");
        Date shipmentDate = resultSet.getDate("shipmentDate");

        Order order = new Order(orderId, customerId, deliveryAddress, shipmentDate);
        return order;
    }
}