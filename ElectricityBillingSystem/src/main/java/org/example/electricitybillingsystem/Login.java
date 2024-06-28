package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Statement;
import java.io.IOException;
import java.sql.ResultSet;

public class Login extends Application {

    @FXML
    private TextField userText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private ChoiceBox<String> loginChoice;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button signupButton;

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 1050, 670));

            primaryStage.initStyle(StageStyle.DECORATED);

            primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    primaryStage.setMaximized(false);
                }
            });

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to load the Login screen.");
        }
    }

    @FXML
    private void initialize() {
        if (loginChoice != null) {
            loginChoice.setItems(FXCollections.observableArrayList("Employee", "Customer"));
            loginChoice.setValue("Employee");
        }
        userText.setEditable(true);
        userText.setFocusTraversable(false);

        passwordText.setEditable(true);
        passwordText.setFocusTraversable(false);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == loginButton) {
            String susername = userText.getText();
            String spassword = passwordText.getText();
            String suser = loginChoice.getValue();

            try {
                Statement statement = databaseConnection.getStatement();

                String query = "select * from Signup where username = '" + susername + "' and password = '" + spassword + "' and usertype ='" + suser + "'";
                ResultSet resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    String meter = resultSet.getString("meter_no");

                    if ("Employee".equals(suser)) {
                        openEmployeeElectricityBillingSystem(suser, meter);
                    } else if ("Customer".equals(suser)) {
                        openCustomerElectricityBillingSystem(suser, meter);
                    }

                } else {
                    showAlert("Invalid Login", "Please check your username, password, and user type.");
                }

                resultSet.close();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred during login.");
            }

        } else if (event.getSource() == cancelButton) {
            Platform.exit();
        } else if (event.getSource() == signupButton) {
            openSignupScreen();
        }
    }

    private void openSignupScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            Parent signupRoot = loader.load();
            Scene signupScene = new Scene(signupRoot, 1050, 670);

            Stage primaryStage = (Stage) signupButton.getScene().getWindow();
            primaryStage.setScene(signupScene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Signup screen.");
        }
    }

    private void openEmployeeElectricityBillingSystem(String acctype, String meter_pass) {
        try {
            // Create the ElectricityBillingSystem UI directly in the Java code
            EmployeeElectricityBillingSystem electricityBillingSystem = new EmployeeElectricityBillingSystem(acctype, meter_pass);
            Stage primaryStage = (Stage) loginButton.getScene().getWindow();
            electricityBillingSystem.start(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Electricity Billing System window.");
        }
    }

    private void openCustomerElectricityBillingSystem(String acctype, String meter_pass) {
        try {
            // Create the ElectricityBillingSystem UI directly in the Java code

            CustomerElectricityBillingSystem electricityBillingSystem = new CustomerElectricityBillingSystem(acctype, meter_pass);
            Stage primaryStage = (Stage) loginButton.getScene().getWindow();
            electricityBillingSystem.start(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Electricity Billing System window.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}