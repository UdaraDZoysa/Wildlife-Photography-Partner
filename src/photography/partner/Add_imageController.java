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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class Add_imageController implements Initializable {
    
    private User u;
    private ImageService imageService = new ImageService();
    static boolean toggle;
    
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
    private TextField imgPathFiled;
    
    @FXML
    private ImageView addImageView;
    
    @FXML
    private Hyperlink browsImageHyperLink;
    
    @FXML
    private ImageView profilePicView;
    
    @FXML
    private Label uName;
    
    public void setUser(User user){
        if(user!= null){
            uName.setText("Hello,"+user.getUserName()+"!");
            Image image =new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u=user;
        }
    }
    
     private void processFile(File file) {
        imgPathFiled.setText(file.getAbsolutePath());
        //UserDraft.setProfilePicPath(file.getAbsolutePath());
        Image image = new Image(file.toURI().toString());
        addImageView.setImage(image);
    }
     
    @FXML
    private void handleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }
    
    @FXML
    private void handleDragDropped(DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success =false;
        if(db.hasFiles()){
            File file = db.getFiles().get(0);
            
            if(file.getName().toLowerCase().matches(".*\\.(jpg|jpeg|png)")){
                processFile(file);
                success=true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    private void handleBrowsImageHyperLink(){
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter= new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File file = fileChooser.showOpenDialog(browsImageHyperLink.getScene().getWindow());
        
        if(file!=null){
            processFile(file);
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
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
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
    
        String imgPath = imgPathFiled.getText();
        String location = locationField.getText();
        String category = categoryField.getText();
        String otherDetails = otherDetailsField.getText();
        String techDetails = techDetailsField.getText();
        int userID = u.getUserID();
        
        boolean success = imageService.addImage(userID, imgPath, location, category, otherDetails, techDetails,toggle);
        
        if(success){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Image Saved");
            alert.setHeaderText(null);
            alert.setContentText("Image Saved Successfully!");
            alert.showAndWait();
            clearDetails();
            publicToggle.setSelected(false);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Image not Saved");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input!");
            alert.showAndWait();
        }
    }
    
    public void clearDetails(){
        imgPathFiled.setText("");
        locationField.setText("");
        categoryField.setText("");
        otherDetailsField.setText("");
        techDetailsField.setText("");
        addImageView.setImage(null);
    }
    
    @FXML
    public void handlePublicToggleBtnAction(ActionEvent event){
        toggle = !toggle; // Toggle the boolean value when it click
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
