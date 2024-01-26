package com.chandni.cftes;

import java.sql.*;

public class Database {

    public static Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:sqlite:cftesDatabase.db";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error connecting to SQLite database");
            e.printStackTrace();
            return null;
        }
    }

    public static void init() {
        try {
            // Purge database
//            try (Connection connection = getConnection()) {
//                Statement statement = connection.createStatement();
//                statement.execute("DROP TABLE IF EXISTS `User`");
//                statement.execute("DROP TABLE IF EXISTS `Device`");
//                statement.execute("DROP TABLE IF EXISTS `Usage`");
//                statement.execute("DROP TABLE IF EXISTS `CarbonFootprint`");
//            }
            try (Connection connection = getConnection()) {
                createTables(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String createUserTable = "CREATE TABLE IF NOT EXISTS `User` ("
                + "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  `full_name` VARCHAR(255),"
                + "  `email` VARCHAR(255) UNIQUE,"
                + "  `password` VARCHAR(255),"
                + "  `user_type` VARCHAR(255)"
                + ")";
        String insertDefaultAdmin = "INSERT OR IGNORE INTO `User` (full_name, email, password, user_type) "
                + "VALUES ('Admin', 'admin963@gmail.com', 'Admin1234', 'admin')";

        String createDeviceTable = "CREATE TABLE IF NOT EXISTS `Device` ("
                + "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  `device_name` VARCHAR(255),"
                + "  `carbon_footprint` FLOAT"
                + ")";

        String createUsageTable = "CREATE TABLE IF NOT EXISTS `Usage` ("
                + "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  `usage_name` VARCHAR(255),"
                + "  `carbon_footprint` FLOAT"
                + ")";

        String createCarbonFootprintTable = "CREATE TABLE IF NOT EXISTS `CarbonFootprint` ("
                + "  `id` INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "  `user_id` INTEGER,"
                + "  `device_id` INTEGER,"
                + "  `usage_id` INTEGER,"
                + "  `location` VARCHAR(255),"
                + "  `cf_value` FLOAT"
                + ")";

        Statement statement = connection.createStatement();
        statement.execute(createUserTable);
        statement.executeUpdate(insertDefaultAdmin);
        statement.execute(createDeviceTable);
        statement.execute(createUsageTable);
        statement.execute(createCarbonFootprintTable);
    }

    private static void insertDeviceData(Connection connection) throws SQLException {
        String[] deviceData = {
            "Desktop + Screen, 621",
            "Laptop + Screen, 691",
            "Desktop + 2 Screens, 903",
            "Laptop + 2 Screens, 928",
            "Desktop + Screen + Laptop, 1030"
        };

        for (String deviceString : deviceData) {
            String[] parts = deviceString.split(", ");
            Device device = new Device();
            device.setDeviceName(parts[0]);
            device.setCarbonFootprint(Float.parseFloat(parts[1]));
            device.createDevice(connection);
        }
    }

    private static void insertUsageData(Connection connection) throws SQLException {
        String[] usageData = {
            "Continuously kept 'active', 73",
            "With default power saving features, 37",
            "Shutdown when not in use, 17.6",
            "Turned off at wall when not in use, 14.7"
        };

        for (String usageString : usageData) {
            String[] parts = usageString.split(", ");
            Usage usage = new Usage();
            usage.setUsageName(parts[0]);
            usage.setCarbonFootprint(Float.parseFloat(parts[1]));
            usage.createUsage(connection);
        }
    }

}
