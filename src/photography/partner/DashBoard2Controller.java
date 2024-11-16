/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.ImageService;
import DataBaseOperations.TripService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import DataBaseOperations.User;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.temporal.ChronoUnit;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class DashBoard2Controller implements Initializable {

    private User u;//create new private user variable to assign retrieved details
    LocalDate currentDate;

    @FXML
    private TilePane tripsTilePane;

    @FXML
    private Label rLabel1;

    @FXML
    private Label rLabel2;

    @FXML
    private Label rLabel3;

    @FXML
    private Label rLabel4;

    @FXML
    private Label rLabel5;

    @FXML
    private Label rLabel6;

    @FXML
    private AnchorPane rPane1;

    @FXML
    private AnchorPane rPane2;

    @FXML
    private AnchorPane rPane3;

    @FXML
    private AnchorPane rPane4;

    @FXML
    private AnchorPane rPane5;

    @FXML
    private AnchorPane rPane6;

    @FXML
    private ImageView recentImg1;

    @FXML
    private ImageView recentImg2;

    @FXML
    private ImageView recentImg3;

    @FXML
    private ImageView recentImg4;

    @FXML
    private ImageView recentImg5;

    @FXML
    private ImageView recentImg6;

    @FXML
    private Label totalImageLabel;

    @FXML
    private Label plannedTripsLabel;

    @FXML
    private Label favouriteLabel;

    @FXML
    private Label publicLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView dashBImageView;

    @FXML
    private ImageView profilePicView;

    @FXML
    private Label uName;

    //this method use to get logged user details to this class and show them
    public void setUser(User user) {
        if (user != null) {
            uName.setText(user.getUserName() + " !");
            Image image = new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);

            //get total image count
            String formattedTotal = String.format("%02d", ImageService.getTotalImageCount(user.getUserID()));
            totalImageLabel.setText(formattedTotal);

            //get favourite image count
            String formattedFavourite = String.format("%02d", ImageService.getFavouriteImageCount(user.getUserID()));
            favouriteLabel.setText(formattedFavourite);

            //get public image count
            String formattedPublic = String.format("%02d", ImageService.getPublicImageCount(user.getUserID()));
            publicLabel.setText(formattedPublic);

            //get public image count
            String formattedTrip = String.format("%02d", TripService.getTripCount(user.getUserID()));
            plannedTripsLabel.setText(formattedTrip);

            this.u = user;//assign user details to u

            //get recent Images
            setRecentImages();

            //get planned Trips
            setPlannedTrips();
        }
    }

    public void setPlannedTrips() {

        TripService.getPlannedTrips(u.getUserID());
        HBox tripsHBox = new HBox(35);
        tripsHBox.setStyle("-fx-background-color:  #748C74;");

        for (int i = 0; i < TripService.trips.size(); i++) {
            VBox card = new VBox(10);
            card.getStyleClass().add("trip-card-green");

            //Create Remaining date label
            LocalDate startDateLocal = TripService.trips.get(i).getStartDate().toLocalDate();//Convert sql Date into Local Date
            LocalDate endDateLocal = TripService.trips.get(i).getEndDate().toLocalDate();

            long daysBetweenStart = ChronoUnit.DAYS.between(currentDate, startDateLocal);// Calculate the number of days between startDate and curreny date(Using Local Dates)
            long daysBetweenEnd = ChronoUnit.DAYS.between(currentDate, endDateLocal);// Calculate the number of days between ednDate and curreny date(Using Local Dates)

            String formattedDateCount = String.format("%02d", daysBetweenStart);//convert Date count into formatted string
            Label remainDateLabel = new Label();
            Label RemainingTextLabel = new Label();

            //container of remaining Date label
            HBox remainingDateBox = new HBox();
            remainingDateBox.setAlignment(Pos.BOTTOM_LEFT);
            remainingDateBox.setPadding(new Insets(10, 10, 10, 10));
            remainingDateBox.setStyle("-fx-background-color: #e8f5e9; -fx-background-radius: 20;");

            // Create a spacer region
            Region spacer = new Region();
            Region spacer1 = new Region();

            spacer1.setPrefWidth(10); // Set the desired width for the gap
            spacer.setPrefWidth(10);

            if (daysBetweenStart > 0) {
                //set styles
                remainDateLabel.setStyle("-fx-font-size: 75px; -fx-text-fill: #489651; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 10 10 10 10;");
                RemainingTextLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: #333; -fx-font-family: 'Segoe UI'; -fx-padding: 20 10 10 10; -fx-font-weight: bold;");
                remainingDateBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-background-radius: 20;");
                //#e8f5e9  #388e3c
                ///////////////////////////////////////////////////
                remainDateLabel.setText(formattedDateCount);
                RemainingTextLabel.setText("Days Left!");
                remainingDateBox.getChildren().addAll(spacer1, remainDateLabel, RemainingTextLabel, spacer);

            } else if (daysBetweenStart == 0) {
                //set styles
                RemainingTextLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: #489651; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-padding: 20 10 10 10;");
                remainingDateBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-background-radius: 20;");
                //#e8f5e9  #388e3c
                ///////////////////////////////////////////////////////////////////
                RemainingTextLabel.setText("It's Today, Have a Nice Trip!");
                remainingDateBox.getChildren().addAll(spacer1, RemainingTextLabel, spacer);
            } else {
                //set styles
                remainDateLabel.setStyle("-fx-font-size: 75px; -fx-text-fill: #E45C5C; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 10 10 10 10;");
                RemainingTextLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: #333; -fx-font-family: 'Segoe UI'; -fx-padding: 20 10 10 10; -fx-font-weight: bold;");
                card.getStyleClass().add("trip-card-red");
                remainingDateBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-background-radius: 20;");
                //#fdecea  #d32f2f
                ///////////////////////////////////////////////////////////////////
                if (daysBetweenEnd > 0) {
                    daysBetweenStart *= -1;
                    formattedDateCount = String.format("%02d", daysBetweenStart);
                    remainDateLabel.setText(formattedDateCount);
                    RemainingTextLabel.setText("Days passed!");
                    remainingDateBox.getChildren().addAll(remainDateLabel, RemainingTextLabel);
                    daysBetweenStart *= -1;
                } else if (daysBetweenEnd == 0) {
                    RemainingTextLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: #E45C5C; -fx-font-family: 'Segoe UI'; -fx-padding: 20 10 10 10; -fx-font-weight: bold;");
                    RemainingTextLabel.setText("Today is the last Day of the trip!");
                    remainingDateBox.getChildren().addAll(spacer1, RemainingTextLabel, spacer);
                } else {
                    daysBetweenEnd *= -1;
                    Label lebel = new Label("days ago!");
                    lebel.setStyle("-fx-font-size: 30px; -fx-text-fill: #333; -fx-font-family: 'Segoe UI'; -fx-padding: 0 10 20 10; -fx-font-weight: bold;");
                    RemainingTextLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: #333; -fx-font-family: 'Segoe UI'; -fx-padding: 0 10 20 10; -fx-font-weight: bold;");
                    remainDateLabel.setStyle("-fx-font-size: 75px; -fx-text-fill: #E45C5C; -fx-font-weight: bold; -fx-font-family: 'Segoe UI'; -fx-padding: 10 10 10 10;");

                    formattedDateCount = String.format("%02d", daysBetweenEnd);
                    remainDateLabel.setText(formattedDateCount);
                    RemainingTextLabel.setText("The trip ended ");
                    remainingDateBox.getChildren().addAll(RemainingTextLabel, remainDateLabel, lebel);
                    daysBetweenEnd *= -1;
                }

            }

            //container of labels
            HBox dateBox = new HBox(10);
            Label dateTextLabel = new Label("Date Duration:");
            dateTextLabel.getStyleClass().add("trip-text-label");
            Label cardDateLabel = new Label(TripService.trips.get(i).getStartDate().toString() + "  to  " + TripService.trips.get(i).getEndDate().toString());
            cardDateLabel.getStyleClass().add("trip-card-label");

            //container of labels
            HBox locationBox = new HBox(10);
            Label locationTextLabel = new Label("Location:");
            locationTextLabel.getStyleClass().add("trip-text-label");
            Label locationLabel = new Label(TripService.trips.get(i).getLocation());
            locationLabel.getStyleClass().add("trip-card-label");

            final int index = i;
            final long startDaysBetween = daysBetweenStart;
            final long endDaysBetween = daysBetweenEnd;

            Hyperlink moreHyperLink = new Hyperlink("more...");
            moreHyperLink.getStyleClass().add("hyperLink");

            moreHyperLink.setOnAction(event -> displayTripDetails(event, index, startDaysBetween, endDaysBetween));

            //add date labels to container
            dateBox.getChildren().addAll(dateTextLabel, cardDateLabel);

            //add location labels to container
            locationBox.getChildren().addAll(locationTextLabel, locationLabel);

            //add label containers to main card container
            spacer.setPrefWidth(20);
            card.getChildren().addAll(remainingDateBox, spacer, dateBox, locationBox, moreHyperLink);

            setupInterationsOfTrips(card);

            tripsHBox.getChildren().add(card);
        }
        //Container HBox of All HBoxes and Link Cards
        HBox finalHBox = new HBox(35);
        finalHBox.setPadding(new Insets(25, 25, 25, 35));

        //hyper link to completed Trips
        Hyperlink completedHyperLink = new Hyperlink("View All Completed Trips...");
        completedHyperLink.setMaxWidth(150); // Set the maximum width to an appropriate value
        completedHyperLink.setWrapText(true); // Enable text wrapping
        completedHyperLink.setTextAlignment(TextAlignment.CENTER); // Optional: Center the text alignment
        completedHyperLink.getStyleClass().add("trip-linkBox-hyperLink");
        completedHyperLink.setOnAction(event -> handleCompletedHyperLinkAction(event));

        VBox completedLinkCard = new VBox(completedHyperLink);
        completedLinkCard.setAlignment(Pos.CENTER); // Optional: Center the hyperlink within the container
        completedLinkCard.getStyleClass().add("trip-link-card");
        setupInterationsOfTrips(completedLinkCard);

        //hyper link to cancelled trips
        Hyperlink cancelledHyperLink = new Hyperlink("View All Cancelled Trips...");
        cancelledHyperLink.setMaxWidth(150); // Set the maximum width to an appropriate value
        cancelledHyperLink.setWrapText(true); // Enable text wrapping
        cancelledHyperLink.setTextAlignment(TextAlignment.CENTER); // Optional: Center the text alignment
        cancelledHyperLink.getStyleClass().add("trip-linkBox-hyperLink");
        cancelledHyperLink.setOnAction(event -> handleCancelledHyperLinkAction(event));

        VBox cancelledLinkCard = new VBox(cancelledHyperLink);
        cancelledLinkCard.setAlignment(Pos.CENTER); // Optional: Center the hyperlink within the container
        cancelledLinkCard.getStyleClass().add("trip-link-card");
        setupInterationsOfTrips(cancelledLinkCard);

        finalHBox.getChildren().addAll(tripsHBox, completedLinkCard, cancelledLinkCard);

        tripsTilePane.getChildren().addAll(finalHBox);
    }

    public void displayTripDetails(ActionEvent event, int index, long daysBetweenStart, long daysBetweenEnd) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTripDetails.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the user
            ViewTripDetailsController controller = loader.getController();
            controller.setTripDetails(daysBetweenStart, daysBetweenEnd, index);
            controller.setUser(u);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    public void handleCompletedHyperLinkAction(ActionEvent event) {
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

    public void handleCancelledHyperLinkAction(ActionEvent event) {
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

    public void setRecentImages() {

        int j = 0;
        ImageService.getRecentImages(u.getUserID());
        ImageView[] recentArray = {recentImg1, recentImg2, recentImg3, recentImg4, recentImg5, recentImg6};
        AnchorPane[] recentPaneArray = {rPane1, rPane2, rPane3, rPane4, rPane5, rPane6};
        Label[] recentLabelArray = {rLabel1, rLabel2, rLabel3, rLabel4, rLabel5, rLabel6};
        
        for (int i = 0; i < ImageService.images.size(); i++) {
            if (j < recentArray.length) {
                try {
                    File file = new File(ImageService.images.get(i).getImgPath());
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        recentArray[j].setImage(image);
                        recentLabelArray[j].setText(ImageService.images.get(i).getDate().toString());
                        setupInterationsOfImages(recentPaneArray[j]);
                        j++;
                    } else {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Image Not Found");
                        alert.setHeaderText(null);
                        alert.setContentText("Image at path: " + file.getAbsolutePath() + " was not found.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    System.out.println("Error loading image:" + e.getMessage());
                }
            }

        }

    }

    private void setupInterationsOfTrips(Node card) {
        // Scale transition for mouse enter
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), card);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        // Scale transition for mouse exit
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), card);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleDown.stop(); // Stop the other transition if it's running
            scaleUp.playFromStart(); // Play the scale-up transition
        });

        card.setOnMouseExited(e -> {
            scaleUp.stop(); // Stop the other transition if it's running
            scaleDown.playFromStart(); // Play the scale-down transition
        });

    }

    private void setupInterationsOfImages(Node card) {
        // Scale transition for mouse enter
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), card);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        // Scale transition for mouse exit
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), card);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleDown.stop(); // Stop the other transition if it's running
            scaleUp.playFromStart(); // Play the scale-up transition
        });

        card.setOnMouseExited(e -> {
            scaleUp.stop(); // Stop the other transition if it's running
            scaleDown.playFromStart(); // Play the scale-down transition
        });

        card.setOnMouseClicked(e -> {
            scaleUp.stop(); // Stop the other transition if it's running
            scaleDown.playFromStart(); // Play the scale-down transition
        });
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
    private void handlePlanTripBtnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Plan_trip.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the user
            Plan_tripController controller = loader.getController();
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

    @FXML
    private void handleLogoutBtnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm LogOut");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to LogOut?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If user click ok, LogOut from account
            u = null;

            Alert successAlert = new Alert(Alert.AlertType.CONFIRMATION);
            successAlert.setTitle("Logged Out");
            successAlert.setHeaderText(null);
            successAlert.setContentText("You have been logged out Successfully!");
            successAlert.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);

                repositionWindow(stage);

                stage.show();
            } catch (IOException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    private void repositionWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Create a Circle to be used as clip of profile picture
        profilePicView.setPreserveRatio(false);
        Circle profileClip = new Circle(profilePicView.getFitWidth() / 2, profilePicView.getFitHeight() / 2, profilePicView.getFitWidth() / 2);
        profilePicView.setClip(profileClip);

        // Create a rectangle with rounded corners to be used as a clip
        Rectangle clip = new Rectangle(dashBImageView.getFitWidth(), dashBImageView.getFitHeight());
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        dashBImageView.setClip(clip);

        //get Local Date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        dateLabel.setText(formattedDate);

        tripsTilePane.setAlignment(Pos.BASELINE_CENTER); // Ensures that the content is centered

    }

}
