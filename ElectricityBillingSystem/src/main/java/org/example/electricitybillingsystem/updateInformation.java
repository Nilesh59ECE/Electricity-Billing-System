package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class updateInformation extends Application {

    @FXML
    private Label nameLabel;

    @FXML
    private TextField meterTextField;

    @FXML
    private TextField addressText, cityText, stateText, emailText, phoneText;

    @FXML
    private Button Update, Close;


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateInformation.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Update Information");
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
            showAlert("Error", "Unable to load the Update Information screen.");
        }
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            nameLabel.requestFocus();
        });
        meterTextField.textProperty().addListener((observable, oldValue, newValue) -> showCustomerDetails(newValue));
    }

    private void showCustomerDetails(String selectedMeter) {
        try {
            DatabaseConnection c = new DatabaseConnection();
            PreparedStatement preparedStatement = c.getConnection().prepareStatement(
                    "SELECT * FROM new_customer WHERE meter_no = ?");
            preparedStatement.setString(1, selectedMeter);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nameLabel.setText(resultSet.getString("name"));
                addressText.setText(resultSet.getString("address"));
                cityText.setText(resultSet.getString("city"));
                stateText.setText(resultSet.getString("state"));
                emailText.setText(resultSet.getString("email"));
                phoneText.setText(resultSet.getString("phone_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to fetch customer details from the database.");
        }
    }


    @FXML
    private void handleButton(ActionEvent event) {
        if(event.getSource() == Update){
            String selectedMeter = meterTextField.getText();
            String saddress = addressText.getText();
            String scity = cityText.getText();
            String sstate = stateText.getText();
            String semail = emailText.getText();
            String sphone = phoneText.getText();

            try {
                DatabaseConnection c = new DatabaseConnection();
                PreparedStatement preparedStatement = c.getConnection().prepareStatement(
                        "UPDATE new_customer SET address=?, city=?, state=?, email=?, phone_no=? WHERE meter_no=?");
                preparedStatement.setString(1, saddress);
                preparedStatement.setString(2, scity);
                preparedStatement.setString(3, sstate);
                preparedStatement.setString(4, semail);
                preparedStatement.setString(5, sphone);
                preparedStatement.setString(6, selectedMeter);

                preparedStatement.executeUpdate();

                showAlert("Success", "User Information Updated Successfully");
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Unable to update user information.");
            }
        }
        else if(event.getSource() == Close){
            CustomerElectricityBillingSystem customerBillingSystem = new CustomerElectricityBillingSystem();
            customerBillingSystem.start(new Stage());
            Stage stage = (Stage) Close.getScene().getWindow();
            stage.close();
        }

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}