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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class CancelledTripsController implements Initializable {

    private User u;
    int index;
    LocalDate currentDate;
    
    @FXML
    private Label dateLabelCurrent;
    
    @FXML
    private TilePane tripsTilePane;
    
    @FXML
    private ImageView profilePicView;
    
    public void setUser(User user){
        if(user!=null){
            Image image =new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u=user;
        }
    }

    public void setCancelledTrips(){
        
        TripService.getCancelledTrips(u.getUserID());  
        
        if(!(TripService.trips.isEmpty())){ 
            VBox tripsVBox = new VBox(45);
            tripsVBox.setPadding(new Insets(25, 25, 25, 35));
            tripsVBox.setStyle("-fx-background-color: rgb(182, 196, 182);"); 
        
            for(int i=0;i<TripService.trips.size();i++){
                index = i;
                //create card
                VBox card = new VBox(15);
                card.getStyleClass().add("completed-trip-card");
                
                //container of labels
                HBox dateBox = new HBox(10);
                Label dateTextLabel = new Label("Date Duration:");
                dateTextLabel.getStyleClass().add("completed-trip-text-label");
                Label cardDateLabel = new Label(TripService.trips.get(i).getStartDate().toString()+"  to  "+TripService.trips.get(i).getEndDate().toString());
                cardDateLabel.getStyleClass().add("completed-trip-card-label"); 
            
                //container of labels
                HBox locationBox = new HBox(10);
                Label locationTextLabel = new Label("Location:");
                locationTextLabel.getStyleClass().add("completed-trip-text-label");
                Label locationLabel = new Label(TripService.trips.get(i).getLocation());
                locationLabel.getStyleClass().add("completed-trip-card-label");
                
                //container of labels
                HBox otherDetailsBox = new HBox(10);
                Label otherDetailsTextLabel = new Label("Other Details:");
                otherDetailsTextLabel.getStyleClass().add("completed-trip-text-label");
                Text otherDetailsText = new Text(TripService.trips.get(i).getOtherDetails());
                otherDetailsText.setFill(Color.web("#21381B")); // Set text color
                otherDetailsText.setFont(Font.font("Segoe UI Bold", FontWeight.NORMAL, 30));
                TextFlow otherDetailsTextFlow = new TextFlow();
                otherDetailsTextFlow.setStyle("-fx-padding: 4; -fx-pref-width:575;");
                otherDetailsTextFlow.getChildren().add(otherDetailsText);
                
                //container of buttons
                HBox buttonBox = new HBox(225);
                buttonBox.setPadding(new Insets(25, 25, 25, 25));
                buttonBox.setAlignment(Pos.CENTER); 
                Button rearrangeBtn = new Button("Rearrange Trip");
                rearrangeBtn.getStyleClass().add("card-button");
                rearrangeBtn.setOnAction(event->handleRearrangeTripBtnAction(event));
                
                Button removeBtn = new Button("Remove Trip Details");
                removeBtn.getStyleClass().add("card-button");
                removeBtn.setOnAction(event->handleRemoveBtnAction(event));
                
                
                //add date labels to container
                dateBox.getChildren().addAll(dateTextLabel,cardDateLabel);
        
                //add location labels to container
                locationBox.getChildren().addAll(locationTextLabel,locationLabel);
                
                //add otherDetails labels to container
                otherDetailsBox.getChildren().addAll(otherDetailsTextLabel,otherDetailsTextFlow);
                
                //add buttons to container
                buttonBox.getChildren().addAll(rearrangeBtn,removeBtn);
        
                //add label containers to main card container
                card.getChildren().addAll(dateBox,locationBox,otherDetailsBox,buttonBox);
            
            setupInterationsOfTrips(card);
            
            tripsVBox.getChildren().add(card); 
        }
        tripsTilePane.getChildren().add(tripsVBox);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cancelled Trips Not Found");
            alert.setHeaderText(null);
            alert.setContentText("Can not Find Any Cancelled Trips!");
            alert.showAndWait();
        }
    }
    
    public void handleRemoveBtnAction(ActionEvent event){
        
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Remove");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to Remove the Trip Details?");
            
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If user click ok, Update details
            //Delete Image
            boolean success = TripService.RemoveTrip(TripService.trips.get(index).getTripID());
            
            if(success){
                Alert successAlert=new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Trip Removed");
                successAlert.setHeaderText(null);
                successAlert.setContentText("The Trip is Removed Successfully!");
                successAlert.showAndWait();
                
                //If the Trip Removed, again load remain cancelled trtps
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CancelledTrips.fxml"));
                    Parent root =loader.load();
            
                    // Retrieve the controller associated with the FXML file and set the u
                    CancelledTripsController controller = loader.getController();
                    controller.setUser(u);
                    controller.setCancelledTrips();
            
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());
            
                    Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
            
                    repositionWindow(stage);
            
                    // Display the updated stage
                    stage.show();
                }catch(IOException e){
                    System.out.println("Error:"+e.getMessage());
                }
                
            }else{
                Alert failedAlert=new Alert(Alert.AlertType.ERROR);
                failedAlert.setTitle("Trip not Removed");
                failedAlert.setHeaderText(null);
                failedAlert.setContentText("Something Went Wrong!");
                failedAlert.showAndWait();  
            }
        }else{
            Alert failedAlert=new Alert(Alert.AlertType.ERROR);
            failedAlert.setTitle("Trip not Removed");
            failedAlert.setHeaderText(null);
            failedAlert.setContentText("Trip Details are not Removed!");
            failedAlert.showAndWait(); 
        }
    }
    
    public void handleRearrangeTripBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTripDetails.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            EditTripDetailsController controller = loader.getController();
            controller.setUser(u,index,2);//pass 2 to indicate that call coming from ViewTripDetailsController
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            repositionWindow(stage);
            
            // Display the updated stage
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
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
    
    @FXML
    public void handlecompletedTripsBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CompletedTrips.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            CompletedTripsController controller = loader.getController();
            controller.setUser(u);
            controller.setCompletedTrips();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            repositionWindow(stage);
            
            // Display the updated stage
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    public void handlehomeBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard2.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            DashBoard2Controller controller = loader.getController();
            controller.setUser(u);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            repositionWindow(stage);
            
            // Display the updated stage
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
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
        
        tripsTilePane.setAlignment(Pos.TOP_CENTER);
        
         //get Local Date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter); 
        dateLabelCurrent.setText(formattedDate);
    }    
    
}
