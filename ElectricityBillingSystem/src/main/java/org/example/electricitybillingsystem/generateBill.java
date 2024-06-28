package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class generateBill extends Application{

    private String meter;

    @FXML
    private TextArea Area;

    @FXML
    private TextField meterNumberText;

    @FXML
    private ChoiceBox<String> searchMonthChoice;

    @FXML
    private Button generateBillButton, printButton;

    public generateBill(){}

    public generateBill(String meter){
        this.meter = meter;
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("generateBill.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Generate Bill");
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
            showAlert("Error", "Unable to load the Generate Bill screen.");
        }
    }

    @FXML private void initialize(){
        if (searchMonthChoice != null) {
            searchMonthChoice.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
            searchMonthChoice.setValue("January");
        }
        meterNumberText.setEditable(true);
        meterNumberText.setFocusTraversable(false);
        printButton.setOnAction(this::printButtonAction);
        printButton.setVisible(false);
    }

    @FXML
    private void generateBillButton(ActionEvent event) {
        // Get the entered meter number and selected month
        String enteredMeterNumber = meterNumberText.getText();
        String selectedMonth = searchMonthChoice.getValue();

        if (enteredMeterNumber.isEmpty()) {
            showAlert("Error", "Please provide the meter number.");
            return; // Exit the method if meter number is not provided
        }

        String header = "Electricity Bill For Month of " + selectedMonth + ", 2024\n";
        String customerDetails = loadDataFromNewCustomer(enteredMeterNumber);
        String meterDetails = loadDataFromMeterInformation(enteredMeterNumber);
        String taxDetails = loadDataFromTax(selectedMonth);
        String billDetails = loadDataFromBill(enteredMeterNumber,selectedMonth);

        String combinedDetails = header + "\n"+ customerDetails + "\n" + meterDetails + "\n" + taxDetails + "\n" + billDetails;
        Area.setText(combinedDetails);

//        Area.setText("\n Power Limited \n Electricity Bill For Month of "+selectedMonth+",2024\n\n\n");
        Area.setEditable(false);
        Area.setVisible(true);
        printButton.setVisible(true);
    }

    private String loadDataFromNewCustomer(String enteredMeterNumber) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.getStatement().executeQuery("select * from new_customer where meter_no = '" + enteredMeterNumber + "'");
            if (resultSet.next()) {
                return " \n     Customer Name         : " + resultSet.getString("name") +
                        "\n    Customer Meter Number : " + resultSet.getString("meter_no") +
                        "\n    Customer Address      : " + resultSet.getString("address") +
                        "\n    Customer City         : " + resultSet.getString("city") +
                        "\n    Customer State        : " + resultSet.getString("state") +
                        "\n    Customer Email        : " + resultSet.getString("email") +
                        "\n    Customer Phone Number : " + resultSet.getString("phone_no");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Unable to load customer data.");
        }
        return "";
    }

    private String loadDataFromMeterInformation(String enteredMeterNumber){
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.getStatement().executeQuery("select * from meter_information where meter_number = '" + enteredMeterNumber + "'");
            if (resultSet.next()) {
                return "\n     Customer Meter Location : " + resultSet.getString("meter_location") +
                        "\n    Customer Meter Type     : " + resultSet.getString("meter_type") +
                        "\n    Customer Phase Code     : " + resultSet.getString("phase_code") +
                        "\n    Customer Bill Type      : " + resultSet.getString("bill_type") ;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Unable to load meter number.");
        }
        return "";
    }

    public String loadDataFromTax(String selectedMonth){
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.getStatement().executeQuery("select * from tax");
            if (resultSet.next()) {
                return "\n     Cost Per Unit  : " + resultSet.getString("cost_per_unit") +
                        "\n    Meter Rent     : " + resultSet.getString("meter_rent") +
                        "\n    Service Charge : " + resultSet.getString("service_charge") +
                        "\n    Service Tax    : " + resultSet.getString("service_tax")+
                        "\n    Swacch Bharat  : " + resultSet.getString("swacch_bharat") +
                        "\n    Fixed Tax      : " + resultSet.getString("fixed_tax");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Unable to load tax data.");
        }
        return "";
    }

    public String loadDataFromBill(String enteredMeterNumber,String selectedMonth){
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.getStatement().executeQuery("select * from bill where meter_no = '" + enteredMeterNumber + "' and month = '"+ selectedMonth+"'");
            if (resultSet.next()) {
                return "\n     Current Month  : " + resultSet.getString("month") +
                        "\n    Unit Consumed     : " + resultSet.getString("unit") +
                        "\n    Total Charges : " + resultSet.getString("total_bill") +
                        "\n    Total Payable   : " + resultSet.getString("total_bill");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Unable to load bill data.");
        }
        return "";
    }

    @FXML
    private void printButtonAction(ActionEvent event) {
        String content = Area.getText();

        if (!content.isEmpty()) {
            printContent(content);
        } else {
            showAlert("Info", "No content to print.");
        }
    }

    private void printContent(String content) {
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            boolean success = job.showPrintDialog(null);

            if (success) {
                printText(job, content);
                job.endJob();
            }
        } else {
            showAlert("Error", "Could not create a printer job.");
        }
    }

    private void printText(PrinterJob job, String content) {
        PageLayout pageLayout = job.getJobSettings().getPageLayout();
        double printableWidth = pageLayout.getPrintableWidth();
        double printableHeight = pageLayout.getPrintableHeight();

        Text text = new Text(content);
        text.setFont(Font.font("Times New Roman", 7));

        double textWidth = text.getBoundsInParent().getWidth();
        double textHeight = text.getBoundsInParent().getHeight();

        double scaleX = printableWidth / textWidth;
        double scaleY = printableHeight / textHeight;

        text.getTransforms().add(new javafx.scene.transform.Scale(scaleX, scaleY));

        job.printPage(text);
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args){
        launch(args);
    }
}