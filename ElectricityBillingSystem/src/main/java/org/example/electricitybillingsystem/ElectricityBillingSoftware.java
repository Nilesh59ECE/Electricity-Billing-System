package org.example.electricitybillingsystem;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;

public class ElectricityBillingSoftware extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/org/example/electricitybillingsystem/icon/splash/splash.jpg");

            if (inputStream != null) {
                ImageView imageView = new ImageView(new Image(inputStream));
                imageView.setFitWidth(1050);
                imageView.setFitHeight(670);

                StackPane root = new StackPane();
                root.getChildren().add(imageView);

                Scene scene = new Scene(root, 1050, 670);

                primaryStage.setTitle("Splash Screen");
                primaryStage.setScene(scene);
                primaryStage.show();

                // Pause for 3 seconds before launching the main application
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(event -> {
                    primaryStage.hide();
                    launchLogin();
                });
                pause.play();
            } else {
                System.err.println("Error loading image: InputStream is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchLogin() {
        Platform.runLater(() -> {
            try {
                Login login = new Login();
                Stage loginStage = new Stage();
                login.start(loginStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
