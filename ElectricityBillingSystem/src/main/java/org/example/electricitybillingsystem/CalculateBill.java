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

public class CalculateBill extends Application {

    @FXML
    private Label nameText, addressText;

    @FXML
    private TextField unitTextField, searchTextField;

    @FXML
    private ChoiceBox<String> monthChoice;

    @FXML
    private Button submitButton, closeButton;

    private DatabaseConnection databaseConnection;

    private String meterNumber;
    private String Month;
    private String Unit;
    private String totalBill;
    private String status;

    public CalculateBill(){

    }

    public CalculateBill(String meterNumber, String Month, String Unit, String totalBill, String status) {
        this.meterNumber = meterNumber;
        this.Month = Month;
        this.Unit = Unit;
        this.totalBill = totalBill;
        this.status = status;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public String getMonth() {
        return Month;
    }

    public String getUnit() {
        return Unit;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public String getStatus(){return status;}


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculateBill.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Calculate Bill");
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
        // Initialize DatabaseConnection

        if (monthChoice != null) {
            monthChoice.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
            monthChoice.setValue("April");
        }

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> fetchDataFromDatabase(newValue));

        searchTextField.setEditable(true);
        searchTextField.setFocusTraversable(false);

        unitTextField.setEditable(true);
        unitTextField.setFocusTraversable(false);

    }

    @FXML
    private void fetchDataFromDatabase(String selectedMeter) {
        try {
            DatabaseConnection c = new DatabaseConnection();
            PreparedStatement preparedStatement = c.getConnection().prepareStatement(
                    "SELECT * FROM new_customer WHERE meter_no = ?");
            preparedStatement.setString(1, selectedMeter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nameText.setText(resultSet.getString("name"));
                addressText.setText(resultSet.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == submitButton) {
            String smeterNo = searchTextField.getText();
            String sunit = unitTextField.getText();
            String smonth = monthChoice.getValue();

            int totalBill = 0;
            int units = Integer.parseInt(sunit);
            String query_tax = "select * from tax";
            try {
                DatabaseConnection c = new DatabaseConnection();
                ResultSet resultSet = c.getStatement().executeQuery(query_tax);
                while (resultSet.next()) {
                    totalBill += units * Integer.parseInt(resultSet.getString("cost_per_unit"));
                    totalBill += Integer.parseInt(resultSet.getString("meter_rent"));
                    totalBill += Integer.parseInt(resultSet.getString("service_charge"));
                    totalBill += Integer.parseInt(resultSet.getString("swacch_bharat"));
                    totalBill += Integer.parseInt(resultSet.getString("fixed_tax"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String query_total_bill = "insert into bill values('" + smeterNo + "', '" + smonth + "','" + sunit + "', '" + totalBill + "','Not Paid')";
            try {
                DatabaseConnection c = new DatabaseConnection();
                c.getStatement().executeUpdate(query_total_bill);

                showAlert("Success", "Customer Bill Updated Successfully");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (event.getSource() == closeButton) {
            EmployeeElectricityBillingSystem employeeBillingSystem = new EmployeeElectricityBillingSystem();
            employeeBillingSystem.start(new Stage());
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
