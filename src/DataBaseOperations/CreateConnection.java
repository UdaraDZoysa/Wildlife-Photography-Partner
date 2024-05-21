/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.*;

public class CreateConnection {
    
    private static final String URL = "jdbc:mysql://localhost/WPMSProject";
    private static final String USER = "root";
    private static final String PASSWORD = "2000319";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /*public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = getConnection();
            
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found:"+e.getMessage()); 
        
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
   
    }*/
    
}
