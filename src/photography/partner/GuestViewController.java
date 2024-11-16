/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.ImageService;
import DataBaseOperations.UserService;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class GuestViewController implements Initializable {

    LocalDate currentDate;

    @FXML
    private ImageView guestBImageView;

    @FXML
    private Label dateLabel;

    @FXML
    private TilePane imgTilePane;

    @FXML
    public void loadImageDynamically() {

        HBox containerBox = new HBox(35);
        containerBox.setPadding(new Insets(25, 25, 25, 35));

        for (int i = 0; i < ImageService.images.size(); i++) {
            try {
                File file = new File(ImageService.images.get(i).getImgPath());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    StackPane imageWrapper = new StackPane(imageView);
                    imageWrapper.setPrefSize(230, 230);

                    imageView.setFitWidth(230);
                    imageView.setFitHeight(230);
                    imageView.setPreserveRatio(true);
                    imageView.getStyleClass().add("Tileimage-view");

                    //containter of Image and short details
                    VBox card = new VBox(10);
                    card.getStyleClass().add("public-image-card");

                    card.getChildren().addAll(imageWrapper, createImageCard(i));
                    setupInterationsOfImages(card);

                    containerBox.getChildren().add(card);
                }
            } catch (Exception e) {
                System.out.println("Error loading image:" + e.getMessage());
            }
        }
        imgTilePane.getChildren().add(containerBox);
    }

    private Node createImageCard(int index) {

        //partial container of details
        VBox detailsVbox = new VBox(5);
        detailsVbox.setPadding(new Insets(0, 10, 5, 10));

        //container of labels
        HBox dateBox = new HBox(10);
        Label dateTextLabel = new Label("Saved Date:");
        dateTextLabel.getStyleClass().add("text-label");
        Label newdateLabel = new Label(ImageService.images.get(index).getDate().toString());
        newdateLabel.getStyleClass().add("card-label");

        //container of labels
        HBox userNameBox = new HBox(10);
        Label userNameTextLabel = new Label("User Name:");
        userNameTextLabel.getStyleClass().add("text-label");
        Label userNameLabel = new Label(UserService.getUserName(ImageService.images.get(index).getUserID()));//get user name
        userNameLabel.getStyleClass().add("card-label");

        //add date labels to container
        dateBox.getChildren().addAll(dateTextLabel, newdateLabel);

        //add user name details to box
        userNameBox.getChildren().addAll(userNameTextLabel, userNameLabel);

        //add label containers to partial container
        detailsVbox.getChildren().addAll(dateBox, userNameBox);

        return detailsVbox;
    }

    private void setupInterationsOfImages(VBox card) {
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

    @FXML
    public void handleBackBtnAction(ActionEvent event) {

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

    private void repositionWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ImageService.getPublicImages();

        // Create a rectangle with rounded corners to be used as a clip
        Rectangle clip = new Rectangle(guestBImageView.getFitWidth(), guestBImageView.getFitHeight());
        clip.setArcWidth(150);
        clip.setArcHeight(150);
        guestBImageView.setClip(clip);

        //get Local Date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        dateLabel.setText(formattedDate);
    }

}
