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
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
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
public class EditTripDetailsController implements Initializable {

    private User u;
    static int index;
    int flag;//Use to indicate this ui load from where(1 --> ViewTripDetailsController , 2 --> CancelledTripsController)
    LocalDate currentDate;

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

    public void setUser(User user, int i, int fromFlag) {
        if (user != null) {
            Image image = new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u = user;
            index = i;
            flag = fromFlag;
            setFields();
        }
    }

    @FXML
    public void setFields() {
        locationField.setText(TripService.trips.get(index).getLocation());
        fromPicker.setValue(TripService.trips.get(index).getStartDate().toLocalDate());
        toPicker.setValue(TripService.trips.get(index).getEndDate().toLocalDate());
        otherDetailsField.setText(TripService.trips.get(index).getOtherDetails());
    }

    @FXML
    public void handleSaveBtnAction(ActionEvent event) {

        boolean success = false;
        String location = locationField.getText();
        LocalDate startDateLocal = fromPicker.getValue();
        LocalDate endDateLocal = toPicker.getValue();
        String otherDetails = otherDetailsField.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to Edit the Trip Details?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If user click ok, Update details
            //Update Trip Details
            if (startDateLocal.compareTo(endDateLocal) <= 0 && startDateLocal.compareTo(currentDate) >= 0) {

                Date startDate = Date.valueOf(fromPicker.getValue());
                Date endDate = Date.valueOf(toPicker.getValue());

                if (flag == 1) {
                    success = TripService.UpdateTripDetails(otherDetails, location, startDate, endDate, TripService.trips.get(index).getTripID());
                } else {
                    success = TripService.setAsNotCancelled(otherDetails, location, startDate, endDate, TripService.trips.get(index).getTripID());
                }
            } else {
                System.out.println("Invalid Date Range!");
            }

            if (success) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Details Updated");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Trip Details Updated Successfully!");
                successAlert.showAndWait();

                handlehomeBtnAction(event);
            } else {
                Alert failedAlert = new Alert(Alert.AlertType.ERROR);
                failedAlert.setTitle("Details Not Updated");
                failedAlert.setHeaderText(null);
                failedAlert.setContentText("Something was Wrong!");
                failedAlert.showAndWait();
                setFields();
            }
        } else {
            Alert failedAlert = new Alert(Alert.AlertType.ERROR);
            failedAlert.setTitle("Details Not Updated");
            failedAlert.setHeaderText(null);
            failedAlert.setContentText("Trip Details not Updated!");
            failedAlert.showAndWait();
            setFields();
        }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Create a Circle to be used as clip of profile picture
        profilePicView.setPreserveRatio(false);
        Circle profileClip = new Circle(profilePicView.getFitWidth() / 2, profilePicView.getFitHeight() / 2, profilePicView.getFitWidth() / 2);
        profilePicView.setClip(profileClip);

        //get Local Date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        dateLabel.setText(formattedDate);
    }

}
