package repositories;

import models.User;
import utils.ApplicationProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public List<User> getUsers() {
        List<User> listOfUsers = new ArrayList<>();
        String query = "SELECT * FROM users;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = mapToUser(resultSet);
                listOfUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfUsers;
    }

    public User getUserById(Long userId) {
        User user = null;
        String query = "SELECT TOP 1 * FROM users WHERE userId = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, userId);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user = mapToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public User getUserByUserName(String userName) {
        User user = null;
        String query = "SELECT TOP 1 * FROM users WHERE userName = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userName);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user = mapToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public Boolean addUser(User user) {
        if(userNameExists(user.getUserName())) {
            return false;
        }

        if(user.getUserPassword().length() < 6) {
            return false; //the password is too short!
        }
        String query = "INSERT INTO users (customerId, userName, userPassword, isAdmin) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setLong(1, user.getCustomerId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getUserPassword());
            ps.setBoolean(4, user.getAdmin());

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

    public Boolean userExists(Long userId) {
        User user = getUserById(userId);

        if(user==null) {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean updateUser(User user, Long userId) {

        if(user.getUserPassword().length() < 6) {
            return false; //the password is too short!
        }

        if(userExists(userId)) {
            String query = "UPDATE users SET customerId = ?, userName = ?, userPassword = ?, isAdmin = ? WHERE userId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {

                ps.setLong(1, user.getCustomerId());
                ps.setString(2, user.getUserName());
                ps.setString(3, user.getUserPassword());
                ps.setBoolean(4, user.getAdmin());
                ps.setLong(5, userId);

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

    public Boolean deleteUser(Long userId) {
        if(userExists(userId)) {
            String query = "DELETE FROM users WHERE userId = ?";

            try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);

                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setLong(1, userId);

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

    public Boolean userAndPasswordMatch(String userName, String userPassword) {
        User user = null;
        String query = "SELECT TOP 1 * FROM users WHERE userName = ? AND userPassword = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, userPassword);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user = mapToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user != null) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean userNameExists(String userName) {
        User user = null;
        String query = "SELECT TOP 1 * FROM users WHERE userName = ?;";

        try (Connection conn = DriverManager.getConnection(ApplicationProperties.JDBC_URL, ApplicationProperties.USERNAME, ApplicationProperties.PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, userName);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user = mapToUser(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user != null) {
            return true;
        }
        else
        {
            return false;
        }
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("userId");
        String userName = resultSet.getString("userName");
        String userPassword = resultSet.getString("userPassword");
        Boolean isAdmin = resultSet.getBoolean("isAdmin");
        Long customerId = resultSet.getLong("customerId");

        User user = new User(userId, userName, userPassword, isAdmin, customerId);
        return user;
    }

}
