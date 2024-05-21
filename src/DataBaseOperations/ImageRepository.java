/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Date;

/**
 *
 * @author Harsha
 */
public class ImageRepository {
    
    public boolean AddImage(Images img){
        boolean rtrn=false;//initial return state
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off
            
            String sql="INSERT INTO photos(user_id, photo_url, OtherDetails, TechDetails, categories, Location,Privacy) VALUES  (?, ?, ?, ?,?,?,?)";
            
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, img.getUserID());
            stmt.setString(2,img.getImgPath());
            stmt.setString(3, img.getOtherDetails());
            stmt.setString(4, img.getTechDetails());
            stmt.setString(5, img.getCategory());
            stmt.setString(6, img.getLocation());
            stmt.setBoolean(7, img.isVilibleAll());
                                   
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
    
    public List<Images> SearchImagesUsingExactDate(int userID,Date date,String location,String category){
        
        List<Images> images = new ArrayList<>();
        
        String[] locations = location.split(", ");
        String inClause = String.join(",",java.util.Collections.nCopies(locations.length, "?"));
        
        String[] categories = category.split(", ");
        String inClauseCat = String.join(",",java.util.Collections.nCopies(categories.length, "?"));
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT * FROM photos WHERE user_id = ? AND timestamp = ? AND Categories IN (" + inClauseCat + ") AND Location IN (" + inClause + ")";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setInt(1,userID);
            stmt.setDate(2, date);
            
            int index =3;
            
            for(String cat : categories){
                stmt.setString(index++, cat.trim());
            }
            
            for(String loc : locations){
                stmt.setString(index++, loc.trim());
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Images image = new Images();
                
                image.setImgID(rs.getInt("photo_id"));
                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setLocation(rs.getString("Location"));
                image.setCategory(rs.getString("Categories"));
                image.setOtherDetails(rs.getString("OtherDetails"));
                image.setTechDetails(rs.getString("TechDetails"));
                image.setDate(rs.getDate("timestamp"));
                image.setVilibleAll(rs.getBoolean("Privacy"));
                images.add(image);
            }
            
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return images;
    }
    
    public List<Images> SearchImagesUsingDateRange(int userID,Date startDate,Date endDate,String location,String category){
        
        List<Images> images = new ArrayList<>();
        
        String[] locations = location.split(", ");
        String inClause = String.join(",",java.util.Collections.nCopies(locations.length, "?"));
        
        String[] categories = category.split(", ");
        String inClauseCat = String.join(",",java.util.Collections.nCopies(categories.length, "?"));
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT * FROM photos WHERE user_id = ? AND timestamp BETWEEN ? AND ? AND Categories IN (" + inClauseCat + ") AND Location IN (" + inClause + ")";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setInt(1,userID);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);
            
            int index =4;
            
            for(String cat : categories){
                stmt.setString(index++, cat.trim());
            }
            
            for(String loc : locations){
                stmt.setString(index++, loc.trim());
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Images image = new Images();
                
                image.setImgID(rs.getInt("photo_id"));
                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setLocation(rs.getString("Location"));
                image.setCategory(rs.getString("Categories"));
                image.setOtherDetails(rs.getString("OtherDetails"));
                image.setTechDetails(rs.getString("TechDetails"));
                image.setDate(rs.getDate("timestamp"));
                image.setVilibleAll(rs.getBoolean("Privacy"));
                images.add(image);
            }
            
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return images;
    }
    
    public List<Images> SearchImagesWithoutDate(int userID,String location,String category){
        
        List<Images> images = new ArrayList<>();
        
        String[] locations = location.split(", ");
        String inClause = String.join(",",java.util.Collections.nCopies(locations.length, "?"));
        
        String[] categories = category.split(", ");
        String inClauseCat = String.join(",",java.util.Collections.nCopies(categories.length, "?"));
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT * FROM photos WHERE user_id = ? AND Categories IN (" + inClauseCat + ") AND Location IN (" + inClause + ")";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setInt(1,userID);
            
            int index =2;
            
            for(String cat : categories){
                stmt.setString(index++, cat.trim());
            }
            
            for(String loc : locations){
                stmt.setString(index++, loc.trim());
            }
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Images image = new Images();
                
                image.setImgID(rs.getInt("photo_id"));
                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setLocation(rs.getString("Location"));
                image.setCategory(rs.getString("Categories"));
                image.setOtherDetails(rs.getString("OtherDetails"));
                image.setTechDetails(rs.getString("TechDetails"));
                image.setDate(rs.getDate("timestamp"));
                image.setVilibleAll(rs.getBoolean("Privacy"));
                images.add(image);
            }
            
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return images;
    }
}
