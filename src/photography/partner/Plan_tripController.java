/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.TripService;
import DataBaseOperations.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class Plan_tripController implements Initializable {

    private User u;
    LocalDate currentDateInit;

    @FXML
    private Label dateLabel;

    @FXML
    private DatePicker fromPicker;

    @FXML
    private DatePicker toPicker;

    @FXML
    private TextArea otherDetailsField;

    @FXML
    private TextField locationField;

    @FXML
    private ImageView profilePicView;

    public void setUser(User user) {
        if (user != null) {
            Image image = new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u = user;
        }
    }

    //select location
    @FXML
    public void handleWilpattuBtnAction(ActionEvent event) {
        locationField.setText("Wilpattu");
    }

    @FXML
    public void handleHecBtnAction(ActionEvent event) {
        locationField.setText("Hurulu Eco Park");
    }

    @FXML
    public void handleKaudullaBtnAction(ActionEvent event) {
        locationField.setText("Kaudulla");
    }

    @FXML
    public void handleMinneriyaBtnAction(ActionEvent event) {
        locationField.setText("Minneriya");
    }

    @FXML
    public void handleWasgamuwaBtnAction(ActionEvent event) {
        locationField.setText("Wasgamuwa");
    }

    @FXML
    public void handleHortonPlainsBtnAction(ActionEvent event) {
        locationField.setText("Horton Plains");
    }

    @FXML
    public void handleGaloyaBtnAction(ActionEvent event) {
        locationField.setText("Galoya");
    }

    @FXML
    public void handleUdawalaweBtnAction(ActionEvent event) {
        locationField.setText("Udawalawe");
    }

    @FXML
    public void handleSinharajaBtnAction(ActionEvent event) {
        locationField.setText("Sinharaja");
    }

    @FXML
    public void handleBundalaBtnAction(ActionEvent event) {
        locationField.setText("Bundala");
    }

    @FXML
    public void handleYalaBtnAction(ActionEvent event) {
        locationField.setText("Yala");
    }

    @FXML
    public void handleKumanaBtnAction(ActionEvent event) {
        locationField.setText("Kumana");
    }

    @FXML
    private void handleSaveTripBtnAction(ActionEvent event) {

        boolean success = false;
        String location = locationField.getText().trim();
        LocalDate startDateLocal = fromPicker.getValue();
        LocalDate endDateLocal = toPicker.getValue();
        String otherDetails = otherDetailsField.getText();
        LocalDate currentDate = LocalDate.now();

        if (!location.isEmpty() && startDateLocal != null && endDateLocal != null) {
            if (startDateLocal.compareTo(endDateLocal) <= 0 && startDateLocal.compareTo(currentDate) >= 0) {

                Date startDate = Date.valueOf(fromPicker.getValue());
                Date endDate = Date.valueOf(toPicker.getValue());
                success = TripService.PlanTrip(u.getUserID(), location, startDate, endDate, otherDetails);
            }

            if (success) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Trip Plan Saved");
                alert.setHeaderText(null);
                alert.setContentText("Trip Plan Saved Successfully!");
                alert.showAndWait();
                clearDetails();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Trip Plan not Saved");
                alert.setHeaderText(null);
                alert.setContentText("Invalid input!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Date");
            alert.setHeaderText("Missing Required Information");
            alert.setContentText("Please ensure that all fields including location and dates are properly filled.");
            alert.showAndWait();
        }
    }

    public void clearDetails() {
        locationField.clear();
        otherDetailsField.clear();
        fromPicker.setValue(null);
        toPicker.setValue(null);
    }

    @FXML
    public void handlehomeBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard2.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            DashBoard2Controller controller = loader.getController();
            controller.setUser(u);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            repositionWindow(stage);

            // Display the updated stage
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

    @FXML
    private void handleSelectBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Select_image.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            Select_imageController controller = loader.getController();
            controller.setUser(u);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    @FXML
    private void handleAddBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add_image.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the user
            Add_imageController controller = loader.getController();
            controller.setUser(u);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    @FXML
    private void handleMemoBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Memories.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            MemoriesController controller = loader.getController();
            controller.setUser(u);
            controller.loadFavouriteImages();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    @FXML
    private void handleTipsBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tips.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the user
            TipsController controller = loader.getController();
            controller.setUser(u);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Create a Circle to be used as clip of profile picture
        profilePicView.setPreserveRatio(false);
        Circle profileClip = new Circle(profilePicView.getFitWidth() / 2, profilePicView.getFitHeight() / 2, profilePicView.getFitWidth() / 2);
        profilePicView.setClip(profileClip);

        //get Local Date
        currentDateInit = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDateInit.format(formatter);
        dateLabel.setText(formattedDate);
    }

}
