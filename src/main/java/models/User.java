package models;

import java.util.Objects;

public class User {
    private Long userId;
    private String userName;
    private String userPassword;
    private Boolean isAdmin;
    private Long customerId;

    public User(Long userId, String userName, String userPassword, Boolean isAdmin, Long customerId) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin;
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", isAdmin=" + isAdmin +
                ", customerId=" + customerId +
                '}';
    }
}
