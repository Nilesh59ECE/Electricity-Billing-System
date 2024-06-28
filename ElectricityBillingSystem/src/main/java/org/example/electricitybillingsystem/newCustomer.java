package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class newCustomer extends Application {

    @FXML
    private Label meterNumText;

    @FXML
    private TextField nameText, addressText, cityText, emailText, phoneText;

    @FXML
    private Button next, close;

    private DatabaseConnection databaseConnection;

    public newCustomer(){

    }

    private String name;
    private String meterNumber;
    private String address;
    private String city;
    private String email;
    private String phone;

    public newCustomer(String name, String meterNumber, String address, String city, String email, String phone) {
        this.name = name;
        this.meterNumber = meterNumber;
        this.address = address;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newCustomer.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("New Customer");
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

    public void initialize() {
        Random ran = new Random();

        // Generate a random string in uppercase with length 3
        String characters1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder randomString1 = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            char randomChar = characters1.charAt(ran.nextInt(characters1.length()));
            randomString1.append(randomChar);
        }

        // Generate a random number with 8 digits
        long number = ran.nextLong() % 1000000000;
        String randomNumber = String.format("%08d", Math.abs(number)); // Ensure 8-digit number

        // Combine the random string and number to form the final string
        String combinedString = randomString1.toString() + randomNumber;

        // Set the combined string to the label
        meterNumText.setText(combinedString);

        databaseConnection = new DatabaseConnection();
        nameText.setEditable(true);
        nameText.setFocusTraversable(false);
        addressText.setEditable(true);
        addressText.setFocusTraversable(false);
        cityText.setEditable(true);
        cityText.setFocusTraversable(false);
        emailText.setEditable(true);
        emailText.setFocusTraversable(false);
        phoneText.setEditable(true);
        phoneText.setFocusTraversable(false);
        next.setOnAction(this::handleNextButton);
    }

    @FXML
    public void handleNextButton(ActionEvent event) {
        // Check if any of the text fields are empty
        if (isEmptyField(nameText)) {
            displayAlert("Error", "Name field is required.");
        } else if (nameText.getText().length() > 30) {
            displayAlert("Error", "Name cannot exceed 30 characters.");
        } else if (isNumberString(nameText.getText())) {
            displayAlert("Error", "Name cannot contain numeric characters.");
        } else if (!isString(addressText.getText())) {
            displayAlert("Error", "Invalid address.");
        } else if (addressText.getText().length() > 50) {
            displayAlert("Error", "Address cannot exceed 50 characters.");
        } else if (!isString(cityText.getText())) {
            displayAlert("Error", "Invalid city.");
        } else if (cityText.getText().length() > 30) {
            displayAlert("Error", "City cannot exceed 30 characters.");
        } else if (isEmptyField(emailText)) {
            displayAlert("Error", "Email field is required.");
        } else if (emailText.getText().length() > 50) {
            displayAlert("Error", "Email cannot exceed 50 characters.");
        } else if (!isValidEmail(emailText.getText())) {
            displayAlert("Error", "Invalid email address.");
        } else if (isEmptyField(phoneText)) {
            displayAlert("Error", "Phone field is required.");
        } else if (phoneText.getText().length() > 10) {
            displayAlert("Error", "Phone number cannot exceed 10 characters.");
        } else if (!isValidPhoneNumber(phoneText.getText())) {
            displayAlert("Error", "Invalid phone number.");

        } else {
            // If all fields are filled, proceed with database insertion
            String sname = nameText.getText();
            String smeter = meterNumText.getText();
            String saddress = addressText.getText();
            String scity = cityText.getText();
            String eemail = emailText.getText();
            String sphone = phoneText.getText();

            try {
                // Use prepareStatement method to get a PreparedStatement instance
                String query_customer = "INSERT INTO new_customer VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatementCustomer = databaseConnection.prepareStatement(query_customer);

                // Set parameters
                preparedStatementCustomer.setString(1, sname);
                preparedStatementCustomer.setString(2, smeter);
                preparedStatementCustomer.setString(3, saddress);
                preparedStatementCustomer.setString(4, scity);
                preparedStatementCustomer.setString(5, eemail);
                preparedStatementCustomer.setString(6, sphone);

                // Execute the update
                preparedStatementCustomer.executeUpdate();
                openMeterInformationScreen(smeter);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Helper method to check if a TextField is empty
    private boolean isEmptyField(TextField textField) {
        return textField.getText().trim().isEmpty();
    }

    private boolean isNumberString(String value) {
        // Check if the given value is a string containing only digits
        return value.matches("\\d+");
    }

    private boolean isString(String value) {
        // Check if the given value is a string
        return value.matches("[a-zA-Z]+");
    }

    private boolean isValidEmail(String email) {
        // Add your email validation logic here
        // For example, you can use a regular expression
        return email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }

    private boolean isValidPhoneNumber(String phone) {
        // Add your phone number validation logic here
        // For example, you can use a regular expression or other validation rules
        return phone.matches("^\\d{10}$");
    }

    private void displayAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    @FXML
    private void handleCancelAction(ActionEvent event) {
        if(event.getSource() == close){
            EmployeeElectricityBillingSystem employeeBillingSystem = new EmployeeElectricityBillingSystem();
            employeeBillingSystem.start(new Stage());
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        }
    }

    private void openMeterInformationScreen(String meterNumber) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("meterInformation.fxml"));
            Parent meterInfoRoot = loader.load();

            meterInformation meterInfoController = loader.getController();
            meterInfoController.setMeterNumber(meterNumber);// Set meter number in controller

            Scene meterInfoScene = new Scene(meterInfoRoot, 1050, 670);

            Stage primaryStage = (Stage) next.getScene().getWindow();
            primaryStage.setScene(meterInfoScene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Meter Information screen.");
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
