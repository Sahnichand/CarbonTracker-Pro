package com.chandni.cftes;

import com.chandni.cftes.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AdminController implements Initializable {

    @FXML
    private Button btnCreateManager;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void createManager(ActionEvent event) {
        String fullName = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (!fullName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setUserType("manager");
            boolean success = newUser.createUser();
            if (success) {
                System.out.println("Manager registered successfully!");
                Util.showAlert(Alert.AlertType.CONFIRMATION, "Registration Success", "You have registered a Manager to the system");
            } else {
                System.out.println("Manager registration failed.");
                Util.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email already exists. Please use a different email.");
            }
        } else {
            System.out.println("Please fill in all fields.");
            Util.showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all fields.");
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        UserData.logout();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Util.redirect(event, fxmlLoader);
    }

}
