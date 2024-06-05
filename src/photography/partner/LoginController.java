/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.User;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import DataBaseOperations.UserService;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.stage.Screen;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class LoginController implements Initializable {
    
    @FXML
    private TextField LogUname;
    
    @FXML
    private PasswordField LogPwd;
    
    private UserService userService = new UserService();//create UserService object
    
    @FXML
    private void handleLoginButtonAction(ActionEvent event){
        
        //get user name and password from text fields
        String userName = LogUname.getText();
        String password = LogPwd.getText();
        
        //call authenticateUser using object and assign resulted user details to new variable
        //if username and passwod not match with entry in DB it will return null
        User user = userService.authenticateUser(userName,password);
        
        if(user !=null){
            loadDashBoardView(user, event);
        }
        else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Username or password is incorrect.");
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleBackButtonAction(ActionEvent event){
        loadView("Home.fxml",event);
    }
    
    @FXML
    private void handleCreateAccountHyperlinkAction(ActionEvent event){ 
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
    
    private void loadDashBoardView(User user, ActionEvent event){
        try{
            // Load the FXML file for the dashboard and initialize its root node
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard2.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the user
            DashBoard2Controller controller = loader.getController();
            controller.setUser(user);
            
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
    }    
    
}
