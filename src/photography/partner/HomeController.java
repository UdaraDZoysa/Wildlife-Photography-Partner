package photography.partner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    @FXML
    private void handleUserButtonAction(ActionEvent event) {
        loadView("Login.fxml", event);
    }

    @FXML
    /*private void handleGuestButtonAction(ActionEvent event) {
        System.out.println("Guest button clicked!");
        // Load guest-specific view
        loadView("GuestView.fxml", event);
    }*/

    private void loadView(String fxmlFile, ActionEvent event) {
        try {
            // Load the FXML file and create a new scene
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(parent);
            
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();// Get the current window (stage) from the event source
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
