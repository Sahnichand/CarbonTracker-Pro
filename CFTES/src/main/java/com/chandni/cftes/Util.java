package com.chandni.cftes;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;

public class Util {

    public static void redirect(ActionEvent event, FXMLLoader fxmlLoader) {
        try {

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void printDialog(ActionEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        if (printer != null) {
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            PrinterJob printerJob = PrinterJob.createPrinterJob(printer);

            if (printerJob != null && printerJob.showPrintDialog(((Node) event.getSource()).getScene().getWindow())) {
                boolean success = printerJob.printPage(pageLayout, ((Node) event.getSource()).getScene().getRoot());
                if (success) {
                    printerJob.endJob();
                } else {
                    System.out.println("Failed to print the page");
                }
            } else {
                System.out.println("Failed to create a PrinterJob");
            }
        } else {
            System.out.println("No printer found");
        }
    }

}
