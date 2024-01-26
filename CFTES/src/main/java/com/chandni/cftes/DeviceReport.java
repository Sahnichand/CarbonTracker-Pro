package com.chandni.cftes;

import com.chandni.cftes.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceReport {

    private Device device;
    private float totalHomeCF;
    private float totalCampusCF;
    private float totalCF = 0;

    public DeviceReport(Device device) {
        this.device = device;

        try (Connection con = Database.getConnection()) {
            String getTotalCFQuery = "SELECT cf.location, SUM(cf.cf_value) as total_cf "
                    + "FROM CarbonFootprint cf "
                    + "WHERE cf.device_id = ? "
                    + "GROUP BY cf.location";

            PreparedStatement statement = con.prepareStatement(getTotalCFQuery);
            statement.setInt(1, device.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String location = resultSet.getString("location");
                float cfVal = resultSet.getFloat("total_cf");

                if ("Home".equalsIgnoreCase(location)) {
                    totalHomeCF = cfVal;
                } else if ("Campus".equalsIgnoreCase(location)) {
                    totalCampusCF = cfVal;
                }
                this.totalCF += cfVal;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Device getDevice() {
        return device;
    }

    public float getTotalHomeCF() {
        return totalHomeCF;
    }

    public float getTotalCampusCF() {
        return totalCampusCF;
    }

    public float getTotalCF() {
        return totalCF;
    }
}
