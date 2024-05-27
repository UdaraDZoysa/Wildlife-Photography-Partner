/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Harsha
 */
public class TripRepository {
    
    public boolean planTrip(Trip trip){
        boolean rtrn=false;//initial return states
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off
            
            String sql="INSERT INTO trip (user_id, location, start_date, end_date,other_details) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, trip.getUserID());
            stmt.setString(2, trip.getLocation());
            stmt.setDate(3, trip.getStartDate());
            stmt.setDate(4, trip.getEndDate());
            stmt.setString(5, trip.getOtherDetails());
            
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
