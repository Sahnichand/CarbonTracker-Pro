package com.chandni.cftes;

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

public class RegisterController implements Initializable {

    @FXML
    private Button btnRegister;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFullName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnGoToLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void register(ActionEvent event) {
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (!fullName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setUserType("user");
            boolean success = newUser.createUser();
            if (success) {
                System.out.println("User registered successfully!");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
                Util.redirect(event, fxmlLoader);
            } else {
                System.out.println("User registration failed.");
                Util.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Email already exists. Please use a different email.");
            }
        } else {
            System.out.println("Please fill in all fields.");
            Util.showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all fields.");
        }
    }

    @FXML

    private void goToLogin(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Util.redirect(event, fxmlLoader);
    }

}
