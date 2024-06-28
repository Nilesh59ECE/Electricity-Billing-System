package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.print.PrinterJob;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDetails extends Application {
    @FXML
    private TextField searchMeterTextField, searchNameTextField;

    @FXML
    private TableView<newCustomer> table;

    @FXML
    private TableColumn<newCustomer, String> nameCol, meterNumberCol, addressCol, cityCol, emailCol, phoneCol;

    @FXML
    private Button search, print, close, Reset;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerDetails.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Customer Details");
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
            showAlert("Error", "Unable to load the Customer Details screen.");
        }
    }

    @FXML
    private void initialize() {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        // Set prompt text for TextFields
        searchMeterTextField.setPromptText("Enter Meter Number");
        searchNameTextField.setPromptText("Enter Customer Name");

        // Populate TableView
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        meterNumberCol.setCellValueFactory(new PropertyValueFactory<>("meterNumber"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        Reset.setOnAction(this::actonHandleButton);
        search.setOnAction(this::actonHandleButton);  // Update event handler
        print.setOnAction(this::actonHandleButton);
        close.setOnAction(this::actonHandleButton);

        loadData(); // Load data initially

        searchMeterTextField.setEditable(true);
        searchMeterTextField.setFocusTraversable(false);

        searchNameTextField.setEditable(true);
        searchNameTextField.setFocusTraversable(false);
    }


    private void loadData() {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();

            String query = "SELECT * FROM new_customer";
            ResultSet resultSet = databaseConnection.getStatement().executeQuery(query);

            ObservableList<newCustomer> customerList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                newCustomer customer = new newCustomer(
                        resultSet.getString("name"),
                        resultSet.getString("meter_no"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_no")
                );
                customerList.add(customer);
            }
            table.setItems(customerList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actonHandleButton(ActionEvent event) {
        if (event.getSource() == search) {
            String searchMeterValue = searchMeterTextField.getText();
            String searchNameValue = searchNameTextField.getText();

            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                String query = "SELECT * FROM new_customer WHERE meter_no = ? OR name = ?";
                PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(query);
                preparedStatement.setString(1, searchMeterValue);
                preparedStatement.setString(2, searchNameValue);

                ResultSet resultSet = preparedStatement.executeQuery();

                ObservableList<newCustomer> customerList = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    newCustomer customer = new newCustomer(
                            resultSet.getString("name"),
                            resultSet.getString("meter_no"),
                            resultSet.getString("address"),
                            resultSet.getString("city"),
                            resultSet.getString("email"),
                            resultSet.getString("phone_no")
                    );
                    customerList.add(customer);
                }
                table.setItems(customerList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == Reset) {
            searchMeterTextField.clear();
            searchNameTextField.clear();
            loadData();
        } else if (event.getSource() == print) {
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
        } else if (event.getSource() == close) {
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
