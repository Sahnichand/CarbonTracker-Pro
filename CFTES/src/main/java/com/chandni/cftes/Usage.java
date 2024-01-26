package com.chandni.cftes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Usage {

    private int id;
    private String usageName;
    private float carbonFootprint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsageName() {
        return usageName;
    }

    public void setUsageName(String usageName) {
        this.usageName = usageName;
    }

    public float getCarbonFootprint() {
        return carbonFootprint;
    }

    // Constructors, getters, and setters
    public void setCarbonFootprint(float carbonFootprint) {
        this.carbonFootprint = carbonFootprint;
    }

    @Override
    public String toString() {
        return this.usageName;
    }

    // CRUD functions
    // Create a new Usage
    public void createUsage(Connection connection) throws SQLException {
        String query = "INSERT INTO Usage (usage_name, carbon_footprint) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, usageName);
        statement.setFloat(2, carbonFootprint);
        statement.executeUpdate();
    }

    // Read a Usage by id
    public static Usage readUsageById(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM Usage WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Usage usage = new Usage();
            usage.setId(resultSet.getInt("id"));
            usage.setUsageName(resultSet.getString("usage_name"));
            usage.setCarbonFootprint(resultSet.getFloat("carbon_footprint"));
            return usage;
        } else {
            return null;
        }
    }

    public static Usage getRandomUsage(Connection connection) throws SQLException {
        String query = "SELECT * FROM `Usage` ORDER BY RANDOM() LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Usage usage = new Usage();
                usage.setId(resultSet.getInt("id"));
                usage.setUsageName(resultSet.getString("usage_name"));
                usage.setCarbonFootprint(resultSet.getFloat("carbon_footprint"));
                return usage;
            } else {
                return null;
            }
        }
    }

    public static List<Usage> getAllUsage(Connection connection) throws SQLException {
        String query = "SELECT * FROM Usage";

        List<Usage> usages = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Usage usage = new Usage();
                usage.setId(resultSet.getInt("id"));
                usage.setUsageName(resultSet.getString("usage_name"));
                usage.setCarbonFootprint(resultSet.getFloat("carbon_footprint"));
                usages.add(usage);
            }
        }

        return usages;
    }

}
