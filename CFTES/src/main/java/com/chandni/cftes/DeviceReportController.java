package com.chandni.cftes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DeviceReportController implements Initializable {

    @FXML
    private Text txtCampusCF;
    @FXML
    private Text txtBaseCF;
    @FXML
    private Text txtDeviceName;
    @FXML
    private Text txtHomeCF;

    private DeviceReport deviceReport;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnClose;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setDeviceReport(DeviceReport deviceReport) {
        this.deviceReport = deviceReport;
        displayDeviceReport();
    }

    private void displayDeviceReport() {
        txtDeviceName.setText(deviceReport.getDevice().getDeviceName());
        txtBaseCF.setText(String.valueOf(deviceReport.getDevice().getCarbonFootprint()));
        txtHomeCF.setText(String.valueOf(deviceReport.getTotalHomeCF()));
        txtCampusCF.setText(String.valueOf(deviceReport.getTotalCampusCF()));
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
