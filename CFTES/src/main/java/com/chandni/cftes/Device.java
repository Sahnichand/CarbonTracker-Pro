package com.chandni.cftes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Device {

    private int id;
    private String deviceName;
    private float carbonFootprint;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
        return this.deviceName;
    }

    // CRUD functions
    // Create a new Device
    public void createDevice(Connection connection) throws SQLException {
        String query = "INSERT INTO Device (device_name, carbon_footprint) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, deviceName);
        statement.setFloat(2, carbonFootprint);
        statement.executeUpdate();
    }

    // Read a Device by id
    public static Device readDeviceById(Connection connection, int id) throws SQLException {
        String query = "SELECT * FROM Device WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Device device = new Device();
            device.setId(resultSet.getInt("id"));
            device.setDeviceName(resultSet.getString("device_name"));
            device.setCarbonFootprint(resultSet.getFloat("carbon_footprint"));
            return device;
        } else {
            return null;
        }
    }

    public static Device getRandomDevice(Connection connection) throws SQLException {
        String query = "SELECT * FROM `Device` ORDER BY RANDOM() LIMIT 1";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                Device device = new Device();
                device.setId(resultSet.getInt("id"));
                device.setDeviceName(resultSet.getString("device_name"));
                device.setCarbonFootprint(resultSet.getFloat("carbon_footprint"));
                return device;
            } else {
                return null;
            }
        }
    }

    public static List<Device> getAllDevices(Connection connection) throws SQLException {
        String query = "SELECT * FROM Device";

        List<Device> devices = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Device device = new Device();
                device.setId(resultSet.getInt("id"));
                device.setDeviceName(resultSet.getString("device_name"));
                device.setCarbonFootprint(resultSet.getFloat("carbon_footprint"));
                devices.add(device);
            }
            return devices;

        }

    }

}
