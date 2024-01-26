package com.chandni.cftes;

import java.net.URL;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class DeviceController implements Initializable {

    @FXML
    private Button btnSubmit;
    @FXML
    private ComboBox<Device> selDevices;
    @FXML
    private ComboBox<Usage> selUsage;
    @FXML
    private ComboBox<String> comLocation;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> locations = new ArrayList<>(Arrays.asList("Home", "Campus"));
        try (Connection con = Database.getConnection()) {

            selDevices.getItems().addAll(Device.getAllDevices(con));
            selUsage.getItems().addAll(Usage.getAllUsage(con));
            comLocation.getItems().addAll(locations);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void submitData(ActionEvent event) {
        CarbonFootprint cf = new CarbonFootprint();
        Device device = selDevices.getSelectionModel().getSelectedItem();
        Usage usage = selUsage.getSelectionModel().getSelectedItem();
        String location = comLocation.getSelectionModel().getSelectedItem();
        cf.setDeviceId(device.getId());
        cf.setUsageId(usage.getId());
        cf.setUserId(UserData.getInstance().getUser().getId());
        cf.setLocation(location);
        cf.setCfValue(cf.calculateCF(device.getId(), usage.getId()));
        try (Connection con = Database.getConnection()) {

            cf.createCarbonFootprint(con);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            Util.redirect(event, fxmlLoader);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Util.redirect(event, fxmlLoader);
    }

}
