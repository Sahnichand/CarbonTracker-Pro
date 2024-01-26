package com.chandni.cftes;

import com.chandni.cftes.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IndividaulReport {

    private User user;
    private final ArrayList<UserCF> entries;

    public User getUser() {
        return user;
    }

    public ArrayList<UserCF> getEntries() {
        return entries;
    }

    public IndividaulReport(User user) {
        this.user = user;
        ArrayList<UserCF> cfEntries = new ArrayList<>();

        try (Connection con = Database.getConnection()) {
            String getReportDataQuery = "SELECT d.device_name, u.usage_name, cf.location, cf.cf_value "
                    + "FROM CarbonFootprint cf "
                    + "JOIN Device d ON cf.device_id = d.id "
                    + "JOIN Usage u ON cf.usage_id = u.id "
                    + "WHERE cf.user_id = ?";

            PreparedStatement statement = con.prepareStatement(getReportDataQuery);
            statement.setInt(1, user.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String deviceName = resultSet.getString("device_name");
                String usageName = resultSet.getString("usage_name");
                String location = resultSet.getString("location");
                float cfValue = resultSet.getFloat("cf_value");

                UserCF entry = new UserCF(user.getId(), deviceName, usageName, cfValue, location);
                cfEntries.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.entries = cfEntries;
    }

}
