package com.chandni.cftes;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerController implements Initializable {

    @FXML
    private Text txtEmail;
    @FXML
    private Text txtFullName;
    @FXML
    private Button btnGenerateReport;
    @FXML
    private TabPane tabPane;
    @FXML
    private TableView<User> tableIndividuals;
    @FXML
    private TableColumn<String, String> colId;
    @FXML
    private TableColumn<String, String> colFullName;
    @FXML
    private TableColumn<String, String> colEmail;
    @FXML
    private TableView<Device> tableDevices;
    @FXML
    private TableColumn<Device, String> colDevices;
    @FXML
    private Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User loggedInUser = UserData.getInstance().getUser();
        txtFullName.setText(loggedInUser.getFullName());
        txtEmail.setText(loggedInUser.getEmail());
        try {
            Connection conn = Database.getConnection();

            // Select all users from User table where role is 'user'
            List<User> users = User.getUsersByRole(conn, "user");
            ObservableList<User> userData = FXCollections.observableArrayList(users);

            // Insert values into tableIndividuals with columns colId, colFullName, colEmail
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableIndividuals.setItems(userData);

            // Fetch all devices and create an ObservableList
            List<Device> devices = Device.getAllDevices(conn);
            ObservableList<Device> deviceData = FXCollections.observableArrayList(devices);

            // Configure the tableDevices column
            colDevices.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
            tableDevices.setItems(deviceData);

        } catch (SQLException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    @FXML
    private void generateReport(ActionEvent event) {
        User selectedUser = tableIndividuals.getSelectionModel().getSelectedItem();
        Device selectedDevice = tableDevices.getSelectionModel().getSelectedItem();
        int tabPaneIndex = tabPane.getSelectionModel().getSelectedIndex();

        if (selectedUser != null && tabPaneIndex == 0) {
            IndividaulReport indR = new IndividaulReport(selectedUser);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("individual_report.fxml"));
                Parent root = loader.load();

                IndividualReportController controller = loader.getController();
                controller.setReport(indR);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Individual Report");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // TODO: do the same for selectedDevice and device_report.fxml
        if (selectedDevice != null && tabPaneIndex == 1) {
            DeviceReport devR = new DeviceReport(selectedDevice);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("device_report.fxml"));
                Parent root = loader.load();

                DeviceReportController controller = loader.getController();
                controller.setDeviceReport(devR);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Device Report");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        UserData.logout();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Util.redirect(event, fxmlLoader);
    }

}
