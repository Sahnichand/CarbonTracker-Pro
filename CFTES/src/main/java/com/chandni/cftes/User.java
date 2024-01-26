package com.chandni.cftes;

import com.chandni.cftes.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String fullName;
    private String email;
    private String password;
    private String userType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    // CRUD functions
    // Create a new user
    public boolean createUser() {
        String query = "INSERT INTO User (full_name, email, password, user_type) VALUES (?, ?, ?, ?)";
        try (Connection con = Database.getConnection()) {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, fullName);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, userType);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    // Read a user by id
    public static User readUserById(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM User WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            User user = new User();
            user.setFullName(resultSet.getString("full_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setUserType(resultSet.getString("user_type"));
            return user;
        } else {
            return null;
        }
    }

    // Update a user
    public void updateUser(Connection connection) throws SQLException {
        String query = "UPDATE User SET full_name = ?, email = ?, password = ?, user_type = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, fullName);
        statement.setString(2, email);
        statement.setString(3, password);
        statement.setString(4, userType);
        statement.setInt(5, id);
        statement.executeUpdate();
    }

    // Delete a user
    public void deleteUser(Connection connection) throws SQLException {
        String query = "DELETE FROM User WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public static User getRandomUser(Connection conn) throws SQLException {
        String query = "SELECT * FROM `User` WHERE user_type = ? ORDER BY RANDOM() LIMIT 1";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "user");
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setUserType(rs.getString("user_type"));
            return user;
        } else {
            return null;
        }

    }

    public static List<User> getUsersByRole(Connection connection, String role) throws SQLException {
        String query = "SELECT * FROM User WHERE user_type = ?";

        List<User> users = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFullName(resultSet.getString("full_name"));
                user.setEmail(resultSet.getString("email"));
                user.setUserType(resultSet.getString("user_type"));
                users.add(user);
            }
        }

        return users;
    }

}
