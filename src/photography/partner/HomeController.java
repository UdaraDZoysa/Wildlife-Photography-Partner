package photography.partner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    @FXML
    private void handleUserButtonAction(ActionEvent event) {
        loadView("Login.fxml", event);
    }

    @FXML
    private void handleGuestBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GuestView.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            GuestViewController controller = loader.getController();
            controller.loadImageDynamically();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            repositionWindow(stage);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private void repositionWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    private void loadView(String fxmlFile, ActionEvent event) {
        try {
            // Load the FXML file and create a new scene
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(parent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();// Get the current window (stage) from the event source
            window.setScene(scene); // Set the new scene to the window
            window.show();// Display the updated window
        } catch (IOException e) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Any initialization logic here
    }
}
