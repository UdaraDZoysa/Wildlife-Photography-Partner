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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class DashBoard2Controller implements Initializable {
    
    private User u;//create new private user variable to assign retrieved details
    
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
    public void setUser(User user){
        if(user!=null){
            uName.setText(user.getUserName()+" !");
            Image image =new Image(new File(user.getProfilePic()).toURI().toString());
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
            
            this.u=user;//assign user details to u
        }
    }
    
    @FXML
    private void handleSelectBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Select_image.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            Select_imageController controller = loader.getController();
            controller.setUser(u);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handleAddBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add_image.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the user
            Add_imageController controller = loader.getController();
            controller.setUser(u);
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handlePlanTripBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Plan_trip.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the user
            Plan_tripController controller = loader.getController();
            controller.setUser(u);
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handleMemoBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Memories.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            MemoriesController controller = loader.getController();
            controller.setUser(u);
            controller.loadFavouriteImages();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handleTipsBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tips.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the user
            TipsController controller = loader.getController();
            controller.setUser(u);
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handleLogoutBtnAction(ActionEvent event){
        
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm LogOut");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to LogOut?");
            
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If user click ok, LogOut from account
            u = null;
            
            Alert successAlert=new Alert(Alert.AlertType.CONFIRMATION);
            successAlert.setTitle("Logged Out");
            successAlert.setHeaderText(null);
            successAlert.setContentText("You have been logged out Successfully!");
            successAlert.showAndWait();
            
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                Parent root =loader.load();
            
                Scene scene = new Scene(root);        
                Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                
                repositionWindow(stage);
                
                stage.show();
            }catch(IOException e){
                System.out.println("Error:"+e.getMessage());
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
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter); 
        dateLabel.setText(formattedDate);
        
    }    
    
}
