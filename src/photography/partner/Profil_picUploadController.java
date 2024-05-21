/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

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
import javafx.scene.control.Button;
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
public class Profil_picUploadController implements Initializable {
    
    @FXML
    private ImageView profileImageView;
    
    @FXML
    private Button selectPathBtn;
    
    private void processFile(File file) {
        UserDraft.setProfilePicPath(file.getAbsolutePath());
        Image image = new Image(file.toURI().toString());
        profileImageView.setImage(image);
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
    private void handleselectPathBtn(){
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilter= new FileChooser.ExtensionFilter("Image files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File file = fileChooser.showOpenDialog(selectPathBtn.getScene().getWindow());
        
        if(file!=null){
            processFile(file);
        }
    }
            
    
    @FXML
    private void handleuploadBackBtnAction(ActionEvent event){
        loadView("CreateAccount.fxml",event);
    }
    
    private void loadView(String fxmlFile, ActionEvent event){
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
        }
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
