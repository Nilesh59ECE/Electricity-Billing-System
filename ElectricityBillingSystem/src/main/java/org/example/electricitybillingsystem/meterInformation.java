package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class meterInformation extends Application {

    public  meterInformation(){
        //
    }

    @FXML
    private Label meterNumText;

    @FXML
    private ChoiceBox<String> meterLocationChoice;
    @FXML
    private ChoiceBox<String> meterTypeChoice;
    @FXML
    private ChoiceBox<String> phaseCodeChoice;
    @FXML
    private ChoiceBox<String> billTypeChoice;
    @FXML private Button submit;
    private String meterNumber;

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
        initialize();
    }
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("meterInformation.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("Meter Information");
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
        if (meterLocationChoice != null) {
            meterLocationChoice.setItems(FXCollections.observableArrayList("Outside", "Inside"));
            meterLocationChoice.setValue("Outside");
        }

        if (meterTypeChoice != null) {
            meterTypeChoice.setItems(FXCollections.observableArrayList("Electric Meter", "Solar Meter", "Smart Meter"));
            meterTypeChoice.setValue("Electric Meter");
        }

        if (phaseCodeChoice != null) {
            phaseCodeChoice.setItems(FXCollections.observableArrayList("011", "022", "033","044", "055", "066", "077", "088", "099"));
            phaseCodeChoice.setValue("011");
        }

        if (billTypeChoice != null) {
            billTypeChoice.setItems(FXCollections.observableArrayList("Normal", "Industrial"));
            billTypeChoice.setValue("Normal");
        }

        meterNumText.setText(meterNumber);
    }

    public void onButtonClick(ActionEvent event) {
        if(event.getSource() == submit){
            String smeterNum = meterNumber;
            String smeterLoc = meterLocationChoice.getValue();
            String smeterTyp = meterTypeChoice.getValue();
            String sphaseCode = phaseCodeChoice.getValue();
            String sbillTyp = billTypeChoice.getValue();

            String query_meterInformation = "insert into meter_information values('" + smeterNum + "','" + smeterLoc + "','" + smeterTyp + "','" + sphaseCode + "','" + sbillTyp + "')";
            try {
                DatabaseConnection c = new DatabaseConnection(); // Assuming you have a DatabaseConnection class
                c.getStatement().executeUpdate(query_meterInformation);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("Meter Information Created Successfully");
                alert.showAndWait();
                EmployeeElectricityBillingSystem employeeBillingSystem = new EmployeeElectricityBillingSystem();
                employeeBillingSystem.start(new Stage());
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();

                // Assuming you have a reference to the stage or scene, you can use it to close the window
                // stage.close(); or scene.getWindow().hide();

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "An error occurred while submitting meter information.");
            }
        }
        // Add your logic for the button click here
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args){
        launch(args);
    }
}
