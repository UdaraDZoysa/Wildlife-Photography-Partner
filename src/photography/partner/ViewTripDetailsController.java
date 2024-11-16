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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class ViewTripDetailsController implements Initializable {

    private User u;
    static int i;
    LocalDate currentDate;

    @FXML
    private Label dateLabelCurrent;

    @FXML
    private Label locationLabel;

    @FXML
    private Label dateDurationLabel;

    @FXML
    private TextFlow otherDetailsTextFlow;

    @FXML
    private Label headingText;

    @FXML
    private ImageView profilePicView;

    public void setUser(User user) {
        if (user != null) {
            Image image = new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u = user;
        }
    }

    public void setTripDetails(long daysBetweenStart, long daysBetweenEnd, int index) {

        i = index;

        locationLabel.setText(TripService.trips.get(index).getLocation());
        dateDurationLabel.setText(TripService.trips.get(index).getStartDate().toString() + "  to  " + TripService.trips.get(index).getEndDate().toString());

        Text otherDetailsText = new Text(TripService.trips.get(index).getOtherDetails());
        otherDetailsText.setFill(Color.web("#21381B")); // Set text color
        otherDetailsText.setFont(Font.font("Segoe UI Bold", FontWeight.NORMAL, 30));
        otherDetailsTextFlow.getChildren().add(otherDetailsText);

        headingText.setStyle("-fx-font-size: 40px; -fx-text-fill: #489651; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");

        if (daysBetweenStart > 0) {
            headingText.setText(daysBetweenStart + " days more to start the Trip!");
        } else if (daysBetweenStart == 0) {
            headingText.setText("Today is the planned start date of the trip!");
        } else {
            headingText.setStyle("-fx-font-size: 40px; -fx-text-fill: #d43131; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
            if (daysBetweenEnd > 0) {
                daysBetweenStart *= -1;
                headingText.setText("It has been " + daysBetweenStart + " days since the planned trip start date!");
            } else if (daysBetweenEnd == 0) {
                headingText.setText("Today is the planned end date of the trip!!");
            } else {
                headingText.setText("The trip ended " + daysBetweenEnd + " days ago!");
            }
        }
    }

    @FXML
    public void handlecompletedBtnAction(ActionEvent event) {

        int tripID = TripService.trips.get(i).getTripID();
        System.out.println("Setting trip ID " + tripID + " as completed.");

        boolean success = TripService.setAsCompleted(tripID);
        if (success) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Set As Completed");
            alert.setHeaderText(null);
            alert.setContentText("Congratulations You Have Completed Trip Successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Can Not Set As Completed");
            alert.setHeaderText(null);
            alert.setContentText("Something Was Wrong!");
            alert.showAndWait();
        }

    }

    // Delete Action
    public void handlecancelTripBtnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to Cancel the Trip?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If user click ok, Update details
            //Delete Image
            boolean success = TripService.setAsCancelled(TripService.trips.get(i).getTripID());

            if (success) {
                Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Trip Cancelled!");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The Trip is Cancelled Successfully!");
                successAlert.showAndWait();

                handlehomeBtnAction(event);
            }
        } else {
            Alert failedAlert = new Alert(Alert.AlertType.ERROR);
            failedAlert.setTitle("Not Cancelled");
            failedAlert.setHeaderText(null);
            failedAlert.setContentText("Trip is not Cancelled!");
            failedAlert.showAndWait();
        }

    }

    @FXML
    public void handleEditBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTripDetails.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            EditTripDetailsController controller = loader.getController();
            controller.setUser(u, i, 1);//pass 1 to indicate that call coming from ViewTripDetailsController

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

    @FXML
    public void handlecompletedTripsBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompletedTrips.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            CompletedTripsController controller = loader.getController();
            controller.setUser(u);
            controller.setCompletedTrips();

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

    @FXML
    public void handleCancelledTripsBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelledTrips.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            CancelledTripsController controller = loader.getController();
            controller.setUser(u);
            controller.setCancelledTrips();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            repositionWindow(stage);

            // Display the updated stage
            stage.show();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
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
        dateLabelCurrent.setText(formattedDate);
    }

}
