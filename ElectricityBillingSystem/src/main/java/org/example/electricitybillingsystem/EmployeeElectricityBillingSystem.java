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
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EmployeeElectricityBillingSystem extends Application implements EventHandler<ActionEvent> {

    private Stage primaryStage;
    private String acctype;
    private String meter_pass;



    public EmployeeElectricityBillingSystem() {
        // Default constructor with no arguments
    }
    public EmployeeElectricityBillingSystem(String acctype, String meter_pass) {
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
            if (newValue) {
                // If the user tries to maximize, set it back to not maximize
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
            // newCustomer Button
            Image newCustomerImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/newCustomer.png"));
            ImageView newCustomerImageView = new ImageView(newCustomerImage);
            newCustomerImageView.setFitWidth(40);
            newCustomerImageView.setFitHeight(40);

            Button newCustomerButton = new Button("New Customer ");
            newCustomerButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            newCustomerButton.setGraphic(newCustomerImageView);
            newCustomerButton.setOnAction(event -> openNewCustomerScreen());
            newCustomerButton.setOnMouseEntered(e -> newCustomerButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            newCustomerButton.setOnMouseExited(e -> newCustomerButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));

            Pane pane = new Pane();
            pane.getChildren().add(newCustomerButton);


            newCustomerButton.setLayoutX(150);
            newCustomerButton.setLayoutY(150);

            // customer Details
            Image customerDetailsImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/customerDetails.jpeg"));
            ImageView customerDetailsImageView = new ImageView(customerDetailsImage);
            customerDetailsImageView.setFitWidth(40);
            customerDetailsImageView.setFitHeight(40);

            Button customerDetailsButton = new Button("Customer Details");
            customerDetailsButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            customerDetailsButton.setGraphic(customerDetailsImageView);
            customerDetailsButton.setOnAction(event -> showCustomerDetails());
            customerDetailsButton.setOnMouseEntered(e -> customerDetailsButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            customerDetailsButton.setOnMouseExited(e -> customerDetailsButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(customerDetailsButton);
            customerDetailsButton.setLayoutX(600);
            customerDetailsButton.setLayoutY(150);

            // Deposit Details
            Image depositDetailsImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/depositDetails.png"));
            ImageView depositDetailsImageView = new ImageView(depositDetailsImage);
            depositDetailsImageView.setFitWidth(40);
            depositDetailsImageView.setFitHeight(40);

            Button depositDetailsButton = new Button("Deposit Details");
            depositDetailsButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            depositDetailsButton.setGraphic(depositDetailsImageView);
            depositDetailsButton.setOnAction(event -> showDepositDetails());
            depositDetailsButton.setOnMouseEntered(e -> depositDetailsButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            depositDetailsButton.setOnMouseExited(e -> depositDetailsButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(depositDetailsButton);
            depositDetailsButton.setLayoutX(150);
            depositDetailsButton.setLayoutY(300);

            // Calculate Bill

            Image calculateBillImage = new Image(getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/calculateBill.png"));
            ImageView calculateBillImageView = new ImageView(calculateBillImage);
            calculateBillImageView.setFitWidth(40);
            calculateBillImageView.setFitHeight(40);

            Button calculateBillButton = new Button("Calculate Bill");
            calculateBillButton.setStyle("-fx-font-size: 30; -fx-font-family: 'Times New Roman';");
            calculateBillButton.setGraphic(calculateBillImageView);
            calculateBillButton.setOnAction(event -> calculateBill());
            calculateBillButton.setOnMouseEntered(e -> calculateBillButton.setStyle("-fx-background-color: #00CC66; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            calculateBillButton.setOnMouseExited(e -> calculateBillButton.setStyle("-fx-background-color: white; -fx-font-size: 30; -fx-font-family: 'Times New Roman';"));
            pane.getChildren().add(calculateBillButton);
            calculateBillButton.setLayoutX(600);
            calculateBillButton.setLayoutY(300);

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
                case "New Customer":
                    openNewCustomerScreen();
                    break;
                case "Customer Details":
                    showCustomerDetails();
                    break;
                case "Deposit Details":
                    showDepositDetails();
                    break;
                case "Calculate Bill":
                    calculateBill();
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

    // Custom logic for menu items
    private void openNewCustomerScreen() {
        // Implement logic to open the new customer screen
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newCustomer.fxml"));
            Parent newCustomerRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(newCustomerRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open new customer screen.");
        }
    }
    private void showCustomerDetails() {
        // Implement logic to show customer details
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerDetails.fxml"));
            Parent CustomerDetailsRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(CustomerDetailsRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open customer details screen.");
        }
    }

    private void showDepositDetails() {
        // Implement logic to show deposit details
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepositDetails.fxml"));
            Parent DepositDetailsRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(DepositDetailsRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to open deposit details screen.");
        }
    }

    private void calculateBill() {
        try {
            // Load the FXML file for the new customer screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculateBill.fxml"));
            Parent CalculateBillRoot = loader.load();

            // Create a new BorderPane and set the new customer screen as the center
            BorderPane root = new BorderPane();
            root.setCenter(CalculateBillRoot);

            // Set the new BorderPane as the root of the scene
            Scene scene = new Scene(root, 1050, 670);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Unable to calculate bill screen.");
        }
        System.out.println("Calculating Bill");
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

// Add similar methods for other menu items...

    private void exitApplication() {
        // Implement logic to exit the application
//        System.out.println("Exiting Application");
//        System.exit(0);
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
