package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

public class paymentBill extends Application {

    @FXML
    private WebView webView;

    @FXML
    Button backButton;

    private String meter;

    private Stage primaryStage;

    public paymentBill(){
    }

    public paymentBill(String meter) {
        this.meter = meter;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("paymentBill.fxml"));
            Parent root = loader.load();

            this.primaryStage = primaryStage; // Set the primaryStage field

            primaryStage.setTitle("Payment Bill");
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
            showAlert("Error", "Unable to load the paymentBill screen.");
        }
    }

    public void initialize() {
        WebEngine webEngine = webView.getEngine();

        try {
            webEngine.load("https://pay.google.com/about/");
        } catch (Exception e) {
            e.printStackTrace();
            webView.getEngine().loadContent("<html>Error! Error! Error! Error! Error! Error!</html>");
        }
    }

    public void closeStage() {

        // Close the stage
        primaryStage.close();
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) webView.getScene().getWindow();
        stage.hide();
        new payBill(meter).start(new Stage());
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