/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import javafx.scene.control.Alert;

public class UserService {
    
    private static UserRepository userRepository = new UserRepository();//create userRepository object
    
    //this method handle the user authentication process when user try to login
    //this will return retrived user details 
    public User authenticateUser(String userName,String password){
        
        //newly created user type variable get the result of login method
        //call login method using userRepository object
        //userName, password get from the loginController class
        User user = userRepository.login(userName, password); // This should verify the hashed password in future development
        
        if(user != null){
            //'user != null' mean userName and password match with entry in DB
            showWelcomeAlert(user.getUserName());
        }
        return user;
    }
    
    //this method handle create account opreration
    //arguments coming from CreateAccountCOntroller class
    //if account create successfully return true
    public boolean createAccount(String userName,String password,String email,String profile_pic){
        
        User user=new User(userName,password,email,profile_pic);
        
        //call createAccount method using userRepository object and return boolean value
        return userRepository.createAccount(user);
        
    }
    
    //this method responsible to display welcome alert
    private void showWelcomeAlert(String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Successful");
        alert.setHeaderText(null);
        alert.setContentText("Welcome, " + username + "!");
        alert.showAndWait();
    }
    
    public static String getUserName(int userID){
        return userRepository.getUserName(userID).getUserName();
    }
}
