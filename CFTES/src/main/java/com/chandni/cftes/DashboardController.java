package com.chandni.cftes;

import com.chandni.cftes.Database;
import com.chandni.cftes.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardController implements Initializable {

    @FXML
    private Button btnGoToAddDevice;
    @FXML
    private TableView<UserCF> tableUserEntries;
    @FXML
    private TableColumn<String, String> colDeviceType;
    @FXML
    private TableColumn<String, String> colUsage;
    @FXML
    private TableColumn<String, String> colCFvalue;
    @FXML
    private Text txtTotalCF;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtFullName;
    @FXML
    private TableColumn<String, String> colLocation;
    @FXML
    private Button btnLogout;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        colDeviceType.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        colUsage.setCellValueFactory(new PropertyValueFactory<>("usageName"));
        colCFvalue.setCellValueFactory(new PropertyValueFactory<>("cfValue"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        UserData userData = UserData.getInstance();
        User user = userData.getUser();

        ArrayList<UserCF> entries = getUserEntries(user);

        // Set user information
        txtEmail.setText(user.getEmail());
        txtFullName.setText(user.getFullName());

        // Set user entries in table
        ObservableList<UserCF> data = FXCollections.observableArrayList(entries);

        tableUserEntries.setItems(data);

        // Calculate total carbon footprint
        float totalCF = entries.stream().map(UserCF::getCfValue).reduce(0f, Float::sum);
        txtTotalCF.setText(String.format("%.2f", totalCF));
    }

    @FXML
    private void goToAddDevice(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("device.fxml"));
        Util.redirect(event, fxmlLoader);
    }

    private ArrayList<UserCF> getUserEntries(User user) {
        ArrayList<UserCF> entries = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT CarbonFootprint.id, Device.device_name, Usage.usage_name, CarbonFootprint.cf_value, CarbonFootprint.location FROM CarbonFootprint "
                    + "INNER JOIN Device ON CarbonFootprint.device_id = Device.id "
                    + "INNER JOIN Usage ON CarbonFootprint.usage_id = Usage.id "
                    + "WHERE CarbonFootprint.user_id = ?"
            );
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String deviceName = result.getString("device_name");
                String usageName = result.getString("usage_name");
                float cfValue = result.getFloat("cf_value");
                String location = result.getString("location");
                UserCF entry = new UserCF(id, deviceName, usageName, cfValue, location);
                entries.add(entry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entries;
    }

     @FXML
    private void logout(ActionEvent event) {
        UserData.logout();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Util.redirect(event, fxmlLoader);
    }
}
