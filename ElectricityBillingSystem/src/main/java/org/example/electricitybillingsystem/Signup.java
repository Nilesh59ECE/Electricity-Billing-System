package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class Signup extends Application {

    @FXML
    private ChoiceBox<String> loginASChoiceBox;

    @FXML
    private Label meterNoLabel;

    @FXML
    private TextField meterTextField;

    @FXML
    private Label employerLabel;

    @FXML
    private TextField employerTextField;

    @FXML
    private Label userName;

    @FXML
    private TextField userNameTextField;

    @FXML
    private Label Name;

    @FXML
    private TextField NameTextField;

    @FXML
    private Label password;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/electricity_billing_system?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Nilesh123@";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 1050, 670);

            primaryStage.setTitle("Signup Page");
            primaryStage.setScene(scene);

            primaryStage.initStyle(StageStyle.DECORATED);

            primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue){
                    primaryStage.setMaximized(false);
                }
            });

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class AlertUtils {

        public static void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        loginASChoiceBox.setItems(FXCollections.observableArrayList("Employee", "Customer"));
        loginASChoiceBox.setValue("Employee");

        if ("Customer".equals(loginASChoiceBox.getValue())) {
            hideEmployerFields();
            showMeterFields();
        } else {
            showEmployerFields();
            hideMeterFields();
        }

        showUserNameFields();
        showNameFields();
        showPasswordFields();

        loginASChoiceBox.setOnAction(event -> {
            String user = loginASChoiceBox.getValue();

            if ("Customer".equals(user)) {
                hideEmployerFields();
                showMeterFields();
            } else {
                showEmployerFields();
                hideMeterFields();
            }
        });

        createButton.setOnAction(event -> handleCreateButton());
        backButton.setOnAction(event -> handleBackButton());

        meterTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateMeterNumber();
            }
        });
        employerTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateEmployerId();
            }
        });
        userNameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateUserName();
            }
        });
        NameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validateName();
            }
        });
        passwordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                validatePassword();
            }
        });
    }

    private void hideMeterFields() {
        meterNoLabel.setVisible(false);
        meterTextField.setVisible(false);
    }

    private void hideEmployerFields() {
        employerLabel.setVisible(false);
        employerTextField.setVisible(false);
    }

    @FXML
    private void showMeterFields() {
        meterNoLabel.setVisible(true);
        meterTextField.setVisible(true);
    }

    @FXML
    private void showEmployerFields() {
        employerLabel.setVisible(true);
        employerTextField.setVisible(true);
    }

    @FXML
    private void showUserNameFields() {
        userName.setVisible(true);
        userNameTextField.setVisible(true);
    }

    @FXML
    private void showNameFields() {
        Name.setVisible(true);
        NameTextField.setVisible(true);
    }

    @FXML
    private void showPasswordFields() {
        password.setVisible(true);
        passwordTextField.setVisible(true);
    }

    @FXML
    private void handleCreateButton() {
        String userType = loginASChoiceBox.getValue();
        String meterNumber = meterTextField.getText();
        String employerId = employerTextField.getText();
        String userNameValue = userNameTextField.getText();
        String nameValue = NameTextField.getText();
        String userPassword = passwordTextField.getText();

        if (userType.isEmpty() || userNameValue.isEmpty() || nameValue.isEmpty() || userPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Error ! Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query;

                String checkSignupQuery = "SELECT * FROM Signup WHERE meter_no = ? AND username = ? AND name = ?";
                PreparedStatement checkSignupStatement = connection.prepareStatement(checkSignupQuery);
                checkSignupStatement.setString(1,meterNumber);
                checkSignupStatement.setString(2, userNameValue);
                checkSignupStatement.setString(3,nameValue);
                ResultSet signupResultSet = checkSignupStatement.executeQuery();

                if (signupResultSet.next()) {
                    // User already exists in the Signup table
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Your account is already created.");
                    alert.showAndWait();
                    return;
                }
                if ("Employee".equals(userType)) {
                    query = "INSERT INTO Signup (username, name, password, usertype, employer_id) VALUES (?, ?, ?, ?, ?)";
                } else {
                    // Check if the meter number already exists
                    String checkCustomerQuery = "SELECT * FROM new_customer WHERE name = ? AND email = ? AND meter_no = ?";
                    PreparedStatement checkCustomerStatement = connection.prepareStatement(checkCustomerQuery);
                    checkCustomerStatement.setString(1, nameValue);
                    checkCustomerStatement.setString(2, userNameValue); // Assuming email is stored in userName field
                    checkCustomerStatement.setString(3, meterNumber);
                    ResultSet resultSet = checkCustomerStatement.executeQuery();

                    if (resultSet.next()) {
                        // Meter number exists, allow account creation
                        query = "INSERT INTO Signup (username, password, usertype, meter_no, name) VALUES (?, ?, ?, ?, ?)";
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Please check your meter number, email and name.");
                        alert.showAndWait();
                        return;
                        // Meter number does not exist, show error message and return

                    }
                }

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    if ("Employee".equals(userType)) {
                        statement.setString(1, userNameValue);
                        statement.setString(2, nameValue);
                        statement.setString(3, userPassword);
                        statement.setString(4, userType);
                        statement.setString(5, employerId); // Assuming employerId is the employer's ID
                    } else {
                        statement.setString(1, userNameValue);
                        statement.setString(2, userPassword);
                        statement.setString(3, userType);
                        statement.setString(4, meterNumber);
                        statement.setString(5, nameValue);
                    }

                    statement.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Account Created");
                    alert.showAndWait();

                    openLoginScreen();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

            AlertUtils.showAlert("Error", "An error occurred while processing the request.");
        }
    }

    private void openLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();
            Scene signupScene = new Scene(loginRoot, 1050, 670);

            Stage primaryStage = (Stage) createButton.getScene().getWindow();
            primaryStage.setScene(signupScene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Unable to open Login screen.");
        }
    }

    @FXML
    private void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot, 1050, 670);

            Stage primaryStage = (Stage) backButton.getScene().getWindow();
            primaryStage.setScene(loginScene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtils.showAlert("Error", "Unable to open Login screen.");
        }
    }

    private boolean validateAllFields() {
        if (!validateMeterNumber() || !validateEmployerId() || !validateUserName() || !validateName() || !validatePassword()) {
            return false;
        }

        return true;
    }

    private boolean validateMeterNumber() {
        String meterNumber = meterTextField.getText();

        if (meterNumber.isEmpty()) {
            displayAlert("Error", "Meter number is required.");
            return false;
        }

        return true;
    }

    private boolean validateEmployerId() {
        String employerId = employerTextField.getText();

        if (employerId.isEmpty()) {
            displayAlert("Error", "Employer ID is required.");
            return false;
        }

        return true;
    }

    private boolean validateUserName() {
        String userName = userNameTextField.getText();

        if (userName.isEmpty()) {
            displayAlert("Error", "Username is required.");
            return false;
        }

        if (!userName.contains("@")) {
            displayAlert("Error", "Username should contain '@' followed by a domain.");
            return false;
        }

        return true;
    }

    private boolean validateName() {
        String name = NameTextField.getText();

        if (name.isEmpty()) {
            displayAlert("Error", "Name is required.");
            return false;
        }

        if (!name.matches("[a-zA-Z\\s]+")) {
            displayAlert("Error", "Name should contain only letters and whitespace.");
            return false;
        }

        return true;
    }

    private boolean validatePassword() {
        String password = passwordTextField.getText();

        if (password.isEmpty()) {
            displayAlert("Error", "Password is required.");
            return false;
        }

        if (password.length() < 8) {
            displayAlert("Error", "Password should be at least 8 characters.");
            return false;
        }

        return true;
    }

    private void displayAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
