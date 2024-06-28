package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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

public class payBill extends Application {

    private String meter;

    @FXML
    private Label nameText, unitText, totalBillText, statusText;

    @FXML
    private TextField meterNumberText;

    @FXML
    private ChoiceBox<String> searchMonthChoice;

    @FXML private Button payButton, closeButton;

    public payBill(){}

    public payBill(String meter) {
        this.meter = meter;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("payBill.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Pay Bill");
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
            showAlert("Error", "Unable to load the pay Bill screen.");
        }
    }

    @FXML
    private void initialize() {
        if (searchMonthChoice != null) {
            searchMonthChoice.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
            searchMonthChoice.setValue("January");
            searchMonthChoice.setOnAction(this::handleMonthSelection);
        }
        Platform.runLater(() -> {
            nameText.requestFocus();
        });
        meterNumberText.textProperty().addListener((observable, oldValue, newValue) -> loadDataFromDatabaseNewCustomer(newValue));
    }

    public void loadDataFromDatabaseNewCustomer(String selectedMeter) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(
                    "SELECT * FROM new_customer WHERE meter_no = ?");
            preparedStatement.setString(1, selectedMeter);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nameText.setText(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to fetch data from customer details from the database.");
        }
    }

    private void handleMonthSelection(ActionEvent event) {
        String selectedMonth = searchMonthChoice.getValue();
        String selectedMeter = meterNumberText.getText();
        loadBillDetails(selectedMeter, selectedMonth);
    }

    private void loadBillDetails(String selectedMeter, String selectedMonth) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(
                    "SELECT unit, total_bill, status FROM bill WHERE meter_no = ? AND month = ?");
            preparedStatement.setString(1, selectedMeter);
            preparedStatement.setString(2, selectedMonth);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                unitText.setText(resultSet.getString("unit"));
                totalBillText.setText(resultSet.getString("total_bill"));
                statusText.setText(resultSet.getString("status"));
            } else {
                // If no data found for the selected meter and month, clear the labels
                unitText.setText("");
                totalBillText.setText("");
                statusText.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Unable to fetch bill details from the database.");
        }
    }



    @FXML
    private void actionHandleButton(ActionEvent e) {
        if (e.getSource() == payButton) {
            try {
                // Update the status in the database
                DatabaseConnection databaseConnection = new DatabaseConnection();
                String updateQuery = "UPDATE bill SET status = 'Paid' WHERE meter_no = ? AND month = ?";
                try (PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, meterNumberText.getText());
                    preparedStatement.setString(2, searchMonthChoice.getValue());
                    preparedStatement.executeUpdate();
                }

                // Load the paymentBill FXML in the same stage
                FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentBill.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) payButton.getScene().getWindow();
                stage.setScene(new Scene(root, 1050, 670));

            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Unable to update the status or load the paymentBill screen.");
            }
        }
        else if (e.getSource() == closeButton) {
            // Close the current stage (go back or close the application)
            CustomerElectricityBillingSystem customerBillingSystem = new CustomerElectricityBillingSystem();
            customerBillingSystem.start(new Stage());
            Stage stage = (Stage) closeButton.getScene().getWindow();
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
