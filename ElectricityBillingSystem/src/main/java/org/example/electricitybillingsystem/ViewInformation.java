package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.application.Platform;
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

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewInformation extends Application {

    @FXML
    private Label nameLabel, addressLabel, cityLabel, emailLabel, phoneLabel;

    @FXML
    private TextField meterTextField;

    @FXML
    private Button Close;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewInformation.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("View Information");
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
            showAlert("Error", "Unable to load the View Information screen.");
        }
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            // Set focus on another control (e.g., nameLabel) to prevent automatic focus on meterTextField
            nameLabel.requestFocus();
        });
        meterTextField.textProperty().addListener((observable, oldValue, newValue) -> loadDataFromDatabase(newValue));
    }

    public void loadDataFromDatabase(String selectedMeter) {
        try {
            DatabaseConnection c = new DatabaseConnection();
            PreparedStatement preparedStatement = c.getConnection().prepareStatement(
                    "SELECT * FROM new_customer WHERE meter_no = ?");
            preparedStatement.setString(1, selectedMeter);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nameLabel.setText(resultSet.getString("name"));
                addressLabel.setText(resultSet.getString("address"));
                cityLabel.setText(resultSet.getString("city"));
                emailLabel.setText(resultSet.getString("email"));
                phoneLabel.setText(resultSet.getString("phone_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to fetch customer details from the database.");
        }
    }

    @FXML
    private void actionHandleButton(ActionEvent event){
        if(event.getSource() == Close){
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
