package com.chandni.cftes;

import com.chandni.cftes.Database;
import java.sql.*;

public class CarbonFootprint {

    private int id;
    private int userId;
    private int deviceId;
    private int usageId;
    private String location;
    private float cfValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getUsageId() {
        return usageId;
    }

    public void setUsageId(int usageId) {
        this.usageId = usageId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getCfValue() {
        return cfValue;
    }

    // Constructors, getters, and setters
    public void setCfValue(float cfValue) {
        this.cfValue = cfValue;
    }

    public void createCarbonFootprint(Connection connection) throws SQLException {
        String query = "INSERT INTO CarbonFootprint (user_id, device_id, usage_id, location, cf_value) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, deviceId);
        statement.setInt(3, usageId);
        statement.setString(4, location);
        statement.setFloat(5, cfValue);
        statement.executeUpdate();
        System.out.println("CF Data Successfully Saved");
    }

    public float calculateCF(int deviceId, int usageId) {
        try (Connection con = Database.getConnection()) {
            float deviceCF = getDeviceCF(con, deviceId);
            float usageCF = getUsageFCF(con, usageId);
            float cfValue = (float) ((deviceCF * 0.85) + usageCF);
            return cfValue;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static float getDeviceCF(Connection connection, int deviceId) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT carbon_footprint FROM Device WHERE id = ?");
        statement.setInt(1, deviceId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getFloat("carbon_footprint");
        } else {
            throw new SQLException("Device not found");
        }
    }

    private static float getUsageFCF(Connection connection, int usageId) throws SQLException {

        PreparedStatement statement = connection.prepareStatement("SELECT carbon_footprint FROM Usage WHERE id = ?");
        statement.setInt(1, usageId);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            return result.getFloat("carbon_footprint");
        } else {
            throw new SQLException("Usage not found");
        }

    }

}
