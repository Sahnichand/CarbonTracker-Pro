package com.chandni.cftes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IndividualReportController implements Initializable {

    @FXML
    private TableView<UserCF> tableUserEntries;
    @FXML
    private TableColumn<String, String> colDeviceType;
    @FXML
    private TableColumn<String, String> colUsage;
    @FXML
    private TableColumn<String, String> colLocation;
    @FXML
    private TableColumn<String, String> colCFvalue;
    @FXML
    private Text txtTotalCF;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtFullName;
    @FXML
    private Text txtUserId;
    private IndividaulReport indReport;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnClose;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configure table columns to display UserCF properties
        colDeviceType.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        colUsage.setCellValueFactory(new PropertyValueFactory<>("usageName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colCFvalue.setCellValueFactory(new PropertyValueFactory<>("cfValue"));
    }

    public void setReport(IndividaulReport indR) {
        this.indReport = indR;

        // Set user information
        User user = indReport.getUser();
        txtEmail.setText(user.getEmail());
        txtFullName.setText(user.getFullName());
        txtUserId.setText(String.valueOf(user.getId()));

        // Populate the TableView with UserCF entries
        ObservableList<UserCF> entries = FXCollections.observableArrayList(indReport.getEntries());
        tableUserEntries.setItems(entries);

        // Calculate and display the total carbon footprint
        float totalCF = 0;
        for (UserCF entry : indReport.getEntries()) {
            totalCF += entry.getCfValue();
        }
        txtTotalCF.setText(String.format("%.2f", totalCF));
    }

    @FXML
    private void printDialog(ActionEvent event) {

        Util.printDialog(event);
    }

    @FXML
    private void closeDialog(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the stage
        stage.close();
    }

}
