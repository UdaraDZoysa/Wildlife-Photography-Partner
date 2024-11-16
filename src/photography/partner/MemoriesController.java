/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.ImageService;
import DataBaseOperations.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class MemoriesController implements Initializable {

    private User u;
    private ImageService imageService = new ImageService();
    LocalDate currentDate;

    @FXML
    private Label dateLabelCurrent;

    @FXML
    private TilePane imgTilePane;

    @FXML
    private ImageView profilePicView;

    public void setUser(User user) {
        if (user != null) {
            Image image = new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u = user;
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

    public void loadFavouriteImages() {

        imageService.displayFavouriteImages(u.getUserID());

        if (!(ImageService.images.isEmpty())) {

            for (int i = 0; i < ImageService.images.size(); i++) {
                try {
                    File file = new File(ImageService.images.get(i).getImgPath());
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        ImageView imageView = new ImageView(image);
                        StackPane imageWrapper = new StackPane(imageView);
                        imageWrapper.setPrefSize(400, 400);

                        imageView.setFitWidth(400);
                        imageView.setFitHeight(400);
                        imageView.setPreserveRatio(true);
                        imageView.getStyleClass().add("Tileimage-view");

                        HBox card = new HBox(10);
                        card.getStyleClass().add("fav-image-card");
                        card.setPadding(new Insets(10));

                        card.getChildren().addAll(imageWrapper, createImageCard(i));
                        setupInterationsOfImages(card);

                        imgTilePane.getChildren().add(card);

                    }
                } catch (Exception e) {
                    System.out.println("Error loading image:" + e.getMessage());
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Favourite Images Not Found");
            alert.setHeaderText(null);
            alert.setContentText("Can not Find Any Favourite Image!");
            alert.showAndWait();
        }
    }

    private Node createImageCard(int index) {
        VBox detailsVbox = new VBox(30);
        detailsVbox.setPadding(new Insets(10, 20, 10, 20));

        HBox dateBox = new HBox(10);
        Label dateTextLabel = new Label("Saved Date:");
        dateTextLabel.getStyleClass().add("fav-text-label");
        Label dateLabel = new Label(ImageService.images.get(index).getDate().toString());
        dateLabel.getStyleClass().add("fav-card-label");

        HBox locationBox = new HBox(10);
        Label locationTextLabel = new Label("Location:");
        locationTextLabel.getStyleClass().add("fav-text-label");
        Label locationLabel = new Label(ImageService.images.get(index).getLocation());
        locationLabel.getStyleClass().add("fav-card-label");

        HBox categoryBox = new HBox(10);
        Label categoryTextLabel = new Label("Category:");
        categoryTextLabel.getStyleClass().add("fav-text-label");
        Label categoryLabel = new Label(ImageService.images.get(index).getCategory());
        categoryLabel.getStyleClass().add("fav-card-label");

        Hyperlink moreHyperLink = new Hyperlink("more...");
        moreHyperLink.getStyleClass().add("fav-hyperLink");

        moreHyperLink.setOnAction(event -> displayImageWithDetails(event, index));

        dateBox.getChildren().addAll(dateTextLabel, dateLabel);
        locationBox.getChildren().addAll(locationTextLabel, locationLabel);
        categoryBox.getChildren().addAll(categoryTextLabel, categoryLabel);
        detailsVbox.getChildren().addAll(dateBox, locationBox, categoryBox, moreHyperLink);

        return detailsVbox;
    }

    public void displayImageWithDetails(ActionEvent event, int index) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSearchedImage.fxml"));
            Parent root = loader.load();

            // Retrieve the controller associated with the FXML file and set the u
            ViewSearchedImageController controller = loader.getController();
            controller.displayImageWithDetails(index, u, 2);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);

            repositionWindow(stage);

            stage.show();
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }

    }

    private void setupInterationsOfImages(HBox card) {
        // Scale transition for mouse enter
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), card);
        scaleUp.setToX(1.075);
        scaleUp.setToY(1.075);

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

    private void repositionWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

        imgTilePane.setAlignment(Pos.CENTER); // Ensures that the content is centered
        imgTilePane.setHgap(60); // Horizontal gap between children
        imgTilePane.setVgap(35); // Vertical gap between children

        //get Local Date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        dateLabelCurrent.setText(formattedDate);
    }

}
