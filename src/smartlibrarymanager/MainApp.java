package smartlibrarymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize default data
            FileHandler.initializeDefaultData();
            
            // Load main.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            BorderPane rootLayout = loader.load();
            
            // Create the scene
            Scene scene = new Scene(rootLayout, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
// Set up the stage
            primaryStage.setTitle("Smart Library Manager");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Error loading main.fxml: " + e.getMessage());
            e.printStackTrace();
            
            // Show error message
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setHeaderText("Failed to Load");
            alert.setContentText("Could not load the main interface. Please check if main.fxml exists.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}