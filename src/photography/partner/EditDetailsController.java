/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.ImageService;
import DataBaseOperations.Images;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class EditDetailsController implements Initializable {

    private User u;
    private Images img;
    private ImageService imageService = new ImageService();
    static boolean toggle;
    LocalDate currentDate;
    
    @FXML
    private Label dateLabel;
    
    @FXML
    private ToggleButton publicToggle;
    
    @FXML
    private TextField locationField;
    
    @FXML
    private TextField categoryField;
    
    @FXML
    private TextArea otherDetailsField;
    
    @FXML
    private TextArea techDetailsField;
    
    @FXML
    private ImageView profilePicView;
    
    public void setUser(User user){
        if(user!=null){
            Image image =new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u=user;
        }
    }
    
    @FXML
    public void setImage(Images image){
        
        locationField.setText(image.getLocation());
        categoryField.setText(image.getCategory());
        otherDetailsField.setText(image.getOtherDetails());
        techDetailsField.setText(image.getTechDetails()); 
        publicToggle.setSelected(image.isVilibleAll());
        
        toggle = image.isVilibleAll();
        img = image;
    }
    
    //select location
    
    @FXML
    public void handleWilpattuBtnAction(ActionEvent event){
        locationField.setText("Wilpattu");
    }
    
    @FXML
    public void handleHecBtnAction(ActionEvent event){
        locationField.setText("Hurulu Eco Park");
    }
    
    @FXML
    public void handleKaudullaBtnAction(ActionEvent event){
        locationField.setText("Kaudulla");
    }
    
    @FXML
    public void handleMinneriyaBtnAction(ActionEvent event){
        locationField.setText("Minneriya");
    }
    
    @FXML
    public void handleWasgamuwaBtnAction(ActionEvent event){
        locationField.setText("Wasgamuwa");
    }
    
    @FXML
    public void handleHortonPlainsBtnAction(ActionEvent event){
        locationField.setText("Horton Plains");
    }
    
    @FXML
    public void handleGaloyaBtnAction(ActionEvent event){
        locationField.setText("Galoya");
    }
    
    @FXML
    public void handleUdawalaweBtnAction(ActionEvent event){
        locationField.setText("Udawalawe");
    }
    
    @FXML
    public void handleSinharajaBtnAction(ActionEvent event){
        locationField.setText("Sinharaja");
    }
    
    @FXML
    public void handleBundalaBtnAction(ActionEvent event){
        locationField.setText("Bundala");
    }
    
    @FXML
    public void handleYalaBtnAction(ActionEvent event){
        locationField.setText("Yala");
    }
    
    @FXML
    public void handleKumanaBtnAction(ActionEvent event){
        locationField.setText("Kumana");
    }
    
    
    //choose category
    
    @FXML
    private void handleMammalsBtnAction(ActionEvent event){
        categoryField.setText("Mammals");
    }
    
    @FXML
    private void handleBirdsBtnAction(ActionEvent event){
        categoryField.setText("Birds");
    }
    
    @FXML
    private void handleRAABtnAction(ActionEvent event){
        categoryField.setText("Reptils and Amphibians");
    }
    
    @FXML
    private void handleIAABtnAction(ActionEvent event){
        categoryField.setText("Insects and Arachnids");
    }
    
    @FXML
    private void handleAquaticBtnAction(ActionEvent event){
        categoryField.setText("Aquatic Life");
    }
    
    @FXML
    private void handleOtherBtnAction(ActionEvent event){
        categoryField.setText("Other");
    }
    
    @FXML
    private void handleSaveBtnAction(ActionEvent event){
        
        String location = locationField.getText();
        String category = categoryField.getText();
        String otherDetails = otherDetailsField.getText();
        String techDetails = techDetailsField.getText();
        int userID = u.getUserID();
        
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Edits");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to save the changes?");
            
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If user click ok, Update details
            //Get Image with edited details
            Images editedImage = imageService.UpdateImage(location, category, otherDetails, techDetails, toggle, img.getImgID());
            
            if(editedImage != null){
                Alert successAlert=new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Details Updated");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Details Updated Successfully!");
                successAlert.showAndWait();
                
                displayEditedImageWithDetails(editedImage,event);//Again Display Image With Edited Details
                
                
            }else{
                Alert failedAlert=new Alert(Alert.AlertType.ERROR);
                failedAlert.setTitle("Details not Updated");
                failedAlert.setHeaderText(null);
                failedAlert.setContentText("Invalid input!");
                failedAlert.showAndWait();
            }
            
        }else{
            //else set details as exist
            locationField.setText(img.getLocation());
            categoryField.setText(img.getCategory());
            otherDetailsField.setText(img.getOtherDetails());
            techDetailsField.setText(img.getTechDetails()); 
            publicToggle.setSelected(img.isVilibleAll());
        }
    }
    
    public void displayEditedImageWithDetails(Images img,ActionEvent event){
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSearchedImage.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            ViewSearchedImageController controller = loader.getController();
            controller.displayEditedImageWithDetails(img, u);
        
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());
            
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
        
    }
    
    @FXML
    public void handlePublicToggleBtnAction(ActionEvent event){
        toggle = !toggle; // Toggle the boolean value when it click
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //get Local Date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter); 
        dateLabel.setText(formattedDate);
    }    
    
}
