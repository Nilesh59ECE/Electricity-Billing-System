package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepositDetails extends Application {
    @FXML
    private TextField searchMeterTextField, searchMonthTextField;

    @FXML
    private TableView<CalculateBill> table;

    @FXML
    private TableColumn<CalculateBill, String> meterNumberCol, monthCol, unitCol, totalBillCol, statusCol;

    @FXML
    private Button search, print, close, Reset;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepositDetails.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Deposit Details");
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
            showAlert("Error", "Unable to load the Deposit Details screen.");
        }
    }

    @FXML
    private void initialize() {

        meterNumberCol.setCellValueFactory(new PropertyValueFactory<>("meterNumber"));
        monthCol.setCellValueFactory(new PropertyValueFactory<>("Month"));
        unitCol.setCellValueFactory(new PropertyValueFactory<>("Unit"));
        totalBillCol.setCellValueFactory(new PropertyValueFactory<>("totalBill"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));

        loadData();

        searchMeterTextField.setEditable(true);
        searchMeterTextField.setFocusTraversable(false);

        searchMonthTextField.setEditable(true);
        searchMonthTextField.setFocusTraversable(false);

    }
    private void loadData() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();

            String query = "SELECT * FROM bill";
            ResultSet resultSet = databaseConnection.getStatement().executeQuery(query);

            ObservableList<CalculateBill> BillList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                CalculateBill Bill = new CalculateBill(
                        resultSet.getString("meter_no"),
                        resultSet.getString("month"),
                        resultSet.getString("unit"),
                        resultSet.getString("total_bill"),
                        resultSet.getString("status")
                );
                BillList.add(Bill);
            }
            table.setItems(BillList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleActionButton(ActionEvent event) {
        if (event.getSource() == search) {
            String enteredMeter = searchMeterTextField.getText();
            String enteredMonth = searchMonthTextField.getText();

            String querySearch;

            if (enteredMeter != null && !enteredMeter.isEmpty()) {
                querySearch = "SELECT * FROM bill WHERE meter_no = '" + enteredMeter + "'";
            } else if (!enteredMonth.isEmpty()) {
                querySearch = "SELECT * FROM bill WHERE month = '" + enteredMonth + "'";
            } else {
                showAlert("Error", "Please enter a meter number or month for search.");
                return;
            }

            try {
                DatabaseConnection c = new DatabaseConnection();
                ResultSet resultSet = c.getStatement().executeQuery(querySearch);

                table.getItems().clear();
                while (resultSet.next()) {
                    CalculateBill bill = new CalculateBill(
                            resultSet.getString("meter_no"),
                            resultSet.getString("month"),
                            resultSet.getString("unit"),
                            resultSet.getString("total_bill"),
                            resultSet.getString("status")
                    );
                    table.getItems().add(bill);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource() == Reset) {
            searchMeterTextField.clear();
            searchMonthTextField.clear();
            loadData();
        }
        else if (event.getSource() == print){
            try {
                PrinterJob printerJob = PrinterJob.createPrinterJob();

                if (printerJob != null && printerJob.showPrintDialog(print.getScene().getWindow())) {
                    table.getTransforms().add(new Scale(0.5, 0.5)); // Adjust the scaling factor as needed
                    boolean success = printerJob.printPage(table);
                    table.getTransforms().remove(table.getTransforms().size() - 1);

                    if (success) {
                        printerJob.endJob();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(event.getSource() == close){
            EmployeeElectricityBillingSystem employeeBillingSystem = new EmployeeElectricityBillingSystem();
            employeeBillingSystem.start(new Stage());
            Stage stage = (Stage) close.getScene().getWindow();
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
