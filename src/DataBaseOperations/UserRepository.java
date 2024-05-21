/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.*;

public class UserRepository {
    
    //this method use to handle login option
    //this method return User type result
    public User login(String userName,String password){
        
        User user=null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql= "select * from users where username = ? and password = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql); 
            stmt.setString(1, userName);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                //get user details to newly created user object
                user = new User(rs.getString("username"),rs.getString("password"),rs.getString("email"),rs.getString("profile_pic"));
                user.setUserID(rs.getInt("user_id"));
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found:"+e.getMessage()); 
        
        }catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        return user;
    }
    
    //this method use to handle create account option
    public boolean createAccount(User user){
        boolean rtrn=false;//initial return states
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off
            
            String sql="INSERT INTO users (username, password, email, profile_pic) VALUES (?, ?, ?, ?)";
            
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            
             if (user.getProfilePic() == null || user.getProfilePic().trim().isEmpty()) {
                 //if user didn't select profile picture set this default image
                stmt.setString(4, "D:\\Sem 1\\OOP\\Assignment_Codes\\NetBeanPractical\\Photography partner\\src\\image\\user_2.png");
            }
            else{
                 //if user select image use it
                stmt.setString(4, user.getProfilePic());
            }
            
            int effectedRowCount=stmt.executeUpdate();//get number of effected rows in DB
            
            if(effectedRowCount>0){
                //if opreation done correctly return set to true
                conn.commit();//commit result
                rtrn=true;
            }
            else{
                conn.rollback();
            }
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return rtrn;
    }
    
}
