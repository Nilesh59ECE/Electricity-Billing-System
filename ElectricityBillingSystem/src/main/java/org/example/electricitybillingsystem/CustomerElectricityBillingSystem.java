package org.example.electricitybillingsystem;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CustomerElectricityBillingSystem extends Application implements EventHandler<ActionEvent> {
    private Stage primaryStage;
    private String acctype;
    private String meter_pass;

    public CustomerElectricityBillingSystem() {
        // Default constructor with no arguments
    }
    public CustomerElectricityBillingSystem(String acctype, String meter_pass) {
        this.meter_pass = meter_pass;
        this.acctype = acctype;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(true);
        primaryStage.setWidth(1050);
        primaryStage.setHeight(670);
        primaryStage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                primaryStage.setMaximized(false);
            }
        });


        // Load image
        Image image = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/back.jpg"));

        // Create an ImageView with the loaded image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1050);
        imageView.setFitHeight(670);

        // Create a StackPane and add the ImageView
        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(imageView);

        Label headingLabel = new Label("Electricity Billing System");
        headingLabel.setStyle("-fx-font-size: 60; -fx-font-family: 'Times New Roman'; -fx-text-fill: white; -fx-font-weight: bold;");
        headingLabel.setPadding(new Insets(20));
        headingLabel.setAlignment(Pos.TOP_CENTER);

        // Create a VBox to hold the heading and set its position
        VBox headingBox = new VBox(headingLabel);
        headingBox.setAlignment(Pos.TOP_CENTER);
        headingBox.setPadding(new Insets(20));
        headingBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Add the heading to the centerPane
        centerPane.getChildren().add(headingBox);
        StackPane.setAlignment(headingBox, Pos.TOP_CENTER);

        try{
            // update Information
            Image updateInformationImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/refresh.png"));
            ImageView updateInformationImageView = new ImageView(updateInformationImage);
            updateInformationImageView.setFitWidth(40);
            updateInformationImageView.setFitHeight(40);

            Button updateInformationButton = new Button("Update Information");
            updateInformationButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            updateInformationButton.setGraphic(updateInformationImageView);
            updateInformationButton.setOnAction(event -> openUpdateInformationScreen());
            updateInformationButton.setOnMouseEntered(e -> updateInformationButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            updateInformationButton.setOnMouseExited(e -> updateInformationButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));

            Pane pane = new Pane();
            pane.getChildren().add(updateInformationButton);


            updateInformationButton.setLayoutX(150);
            updateInformationButton.setLayoutY(150);

            // View Information

            Image viewInformationImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/information.png"));
            ImageView viewInformationImageView = new ImageView(viewInformationImage);
            viewInformationImageView.setFitWidth(40);
            viewInformationImageView.setFitHeight(40);

            Button viewInformationButton = new Button("View Information");
            viewInformationButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            viewInformationButton.setGraphic(viewInformationImageView);
            viewInformationButton.setOnAction(event -> openViewInformationScreen());
            viewInformationButton.setOnMouseEntered(e -> viewInformationButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            viewInformationButton.setOnMouseExited(e -> viewInformationButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(viewInformationButton);


            viewInformationButton.setLayoutX(600);
            viewInformationButton.setLayoutY(150);

            // Pay Bill

            Image payBillImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/pay.jpeg"));
            ImageView payBillImageView = new ImageView(payBillImage);
            payBillImageView.setFitWidth(40);
            payBillImageView.setFitHeight(40);

            Button payBillButton = new Button("Pay Bill");
            payBillButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            payBillButton.setGraphic(payBillImageView);
            payBillButton.setOnAction(event -> openViewInformationScreen());
            payBillButton.setOnMouseEntered(e -> payBillButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            payBillButton.setOnMouseExited(e -> payBillButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(payBillButton);


            payBillButton.setLayoutX(80);
            payBillButton.setLayoutY(300);

            // Bill Details

            Image billDetailsImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/billDetails.jpg"));
            ImageView billDetailsImageView = new ImageView(billDetailsImage);
            billDetailsImageView.setFitWidth(40);
            billDetailsImageView.setFitHeight(40);

            Button billDetailsButton = new Button("Bill Details");
            billDetailsButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            billDetailsButton.setGraphic(billDetailsImageView);
            billDetailsButton.setOnAction(event -> openViewInformationScreen());
            billDetailsButton.setOnMouseEntered(e -> billDetailsButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            billDetailsButton.setOnMouseExited(e -> billDetailsButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(billDetailsButton);


            billDetailsButton.setLayoutX(380);
            billDetailsButton.setLayoutY(300);

            // Generate Bill

            Image generateBillImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/bill.png"));
            ImageView generateBillImageView = new ImageView(generateBillImage);
            generateBillImageView.setFitWidth(40);
            generateBillImageView.setFitHeight(40);

            Button generateBillButton = new Button("Generate Bill");
            generateBillButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            generateBillButton.setGraphic(generateBillImageView);
            generateBillButton.setOnAction(event -> openViewInformationScreen());
            generateBillButton.setOnMouseEntered(e -> generateBillButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            generateBillButton.setOnMouseExited(e -> generateBillButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(generateBillButton);


            generateBillButton.setLayoutX(700);
            generateBillButton.setLayoutY(300);

            // Notepad

            Image notepadImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/notepad.png"));
            ImageView notepadImageView = new ImageView(notepadImage);
            notepadImageView.setFitWidth(30);
            notepadImageView.setFitHeight(30);

            Button notepadButton = new Button("Notepad");
            notepadButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            notepadButton.setGraphic(notepadImageView);
            notepadButton.setOnAction(event -> openNotepad());
            notepadButton.setOnMouseEntered(e -> notepadButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            notepadButton.setOnMouseExited(e -> notepadButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(notepadButton);
            notepadButton.setLayoutX(150);
            notepadButton.setLayoutY(450);

            // Calculator

            Image calculatorImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/calculator.png"));
            ImageView calculatorImageView = new ImageView(calculatorImage);
            calculatorImageView.setFitWidth(30);
            calculatorImageView.setFitHeight(30);

            Button calculatorButton = new Button("Calculator");
            calculatorButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            calculatorButton.setGraphic(calculatorImageView);
            calculatorButton.setOnAction(event -> openCalculator());
            calculatorButton.setOnMouseEntered(e -> calculatorButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            calculatorButton.setOnMouseExited(e -> calculatorButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(calculatorButton);
            calculatorButton.setLayoutX(600);
            calculatorButton.setLayoutY(450);

            // Exit

            Image exitImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/exit.png"));
            ImageView exitImageView = new ImageView(exitImage);
            exitImageView.setFitWidth(10);
            exitImageView.setFitHeight(10);

            Button exitButton = new Button("Exit");
            exitButton.setStyle("-fx-font-size: 15; -fx-font-family: 'Times New Roman';");
            exitButton.setGraphic(exitImageView);
            exitButton.setOnAction(event -> exitApplication());
            exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: #FF3333; -fx-font-size: 15; -fx-font-family: 'Times New Roman';"));
            exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color: white; -fx-font-size: 15; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(exitButton);
            exitButton.setLayoutX(950);
            exitButton.setLayoutY(20);

            centerPane.getChildren().add(pane);
        }catch (Exception e) {
            System.err.println("Error loading image for the buttons: " + e.getMessage());
            // Handle the exception gracefully
        }

        // Create BorderPane and set the layout
        BorderPane root = new BorderPane();
        root.setCenter(centerPane);

        // Create the scene and set it on the stage
        primaryStage.setTitle("Electricity Billing System");
        Scene scene = new Scene(root, 1050, 670);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    @Override
    public void handle(ActionEvent event) {
        // Handle the action events for the MenuItems
        if (event.getSource() instanceof MenuItem) {
            MenuItem menuItem = (MenuItem) event.getSource();

            // Implement custom logic based on the clicked menu item
            switch (menuItem.getText()) {
                case "Update Information":
                    openUpdateInformationScreen();
                    break;
                case "View Information":
                    openViewInformationScreen();
                    break;
                case "Pay Bill":
                    openPayBillScreen();
                    break;
                case "Bill Details":
                    openShowBillDetailsScreen();
                    break;
                case "Generate Bill":
                    openGenerateBill();
                    break;
                case "Notepad":
                    openNotepad();
                    break;
                case "Calculator":
                    openCalculator();
                    break;
                case "Exit":
                    exitApplication();
                    break;
                default:
                    System.out.println("No action defined for " + menuItem.getText());
            }
        }
    }


    private void openUpdateInformationScreen(){
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateInformation.fxml"));
            Parent updateInformationRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(updateInformationRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open update information screen.");
        }
    }

    private void openViewInformationScreen(){
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewInformation.fxml"));
            Parent ViewInformationRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(ViewInformationRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open view information screen.");
        }
    }

    private void openPayBillScreen(){
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("payBill.fxml"));
            Parent payBillRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(payBillRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open pay bill screen.");
        }
    }

    private void openShowBillDetailsScreen(){
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billDetails.fxml"));
            Parent billDetailsRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(billDetailsRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open bill details screen.");
        }
    }

    private void openGenerateBill(){
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("generateBill.fxml"));
            Parent generateBillRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(generateBillRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open generate bill screen.");
        }
    }

    private void openNotepad(){
        try {
            Runtime.getRuntime().exec("notepad.exe");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Notepad.");
        }
    }

    private void openCalculator(){
        try {
            Runtime.getRuntime().exec("calc.exe");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open Calculator.");
        }
    }


    private void exitApplication() {
        openLoginScreen();
    }

    private void openLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent loginRoot = loader.load();
            Scene ElectricityBilingSystem = new Scene(loginRoot, 1050, 670);
            primaryStage.setScene(ElectricityBilingSystem);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Signup.AlertUtils.showAlert("Error", "Unable to open Login screen.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


