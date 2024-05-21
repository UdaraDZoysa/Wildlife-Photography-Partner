/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package photography.partner;

/**
 *
 * @author Harsha
 */
public class UserDraft {
    private static String userName;
    private static String password;
    private static String email;
    private static String profilePicPath;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String aUserName) {
        userName = aUserName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String aEmail) {
        email = aEmail;
    }

    public static String getProfilePicPath() {
        return profilePicPath;
    }

    public static void setProfilePicPath(String aProfilePicPath) {
        profilePicPath = aProfilePicPath;
    }  
}
