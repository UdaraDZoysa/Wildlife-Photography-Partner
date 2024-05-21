/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.UserService;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class CreateAccountController implements Initializable {
    
    @FXML
    private TextField CreUname;
    
    @FXML
    private PasswordField CrePwd;
    
    @FXML
    private TextField CreEmail;
    
    @FXML
    private TextField profilePath;
    
    //Save draft as user details using UserDraft class
    private void saveDraft(){
        UserDraft.setUserName(CreUname.getText());
        UserDraft.setPassword(CrePwd.getText());
        UserDraft.setEmail(CreEmail.getText());
    }
    
    @FXML
    private void handleSelectButtonAction(ActionEvent event) {
        saveDraft();//Save draft details calling saveDraft when user try to select the profile picture
        loadView("profil_picUpload.fxml", event);
    }
    
    //user back again to create account ui load draft details
    private void loadDraft(){
        CreUname.setText(UserDraft.getUserName());
        CrePwd.setText(UserDraft.getPassword());
        CreEmail.setText(UserDraft.getEmail());
        profilePath.setText(UserDraft.getProfilePicPath());
        
        UserDraft.setPassword(null);//Set password in UserDraft class becouse of security reasons
    }
    
    private final UserService userservice= new UserService();//create UserService object
    
    @FXML
    private void handleCreateButtonAction(ActionEvent event){
        
        //get values from text fields
        String userName=CreUname.getText();
        String password=CrePwd.getText();
        String email=CreEmail.getText();
        String profilePicPath=profilePath.getText();
        
        //call createAccount method using object and assign result to success boolean variable
        //if process done correctly method will return true
        boolean successs=userservice.createAccount(userName, password, email, profilePicPath);
        
        if(successs){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Account Created");
            alert.setHeaderText(null);
            alert.setContentText("Account Created Successfully!");
            alert.showAndWait();
            loadView("Login.fxml", event);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Account Not Created");
            alert.setHeaderText(null);
            alert.setContentText("Invalid user name or missing required field!");
            alert.showAndWait();
        }
    
    }

    @FXML
    private void handleBacktLgnHyperlinkAction(ActionEvent event){
        loadView("Login.fxml",event);
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
        loadDraft();//when controller initialize that will call
    }    
    
}
