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
            
            String sql="INSERT INTO photos(user_id, photo_url, OtherDetails, TechDetails, categories, Location,Public) VALUES  (?, ?, ?, ?,?,?,?)";
            
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
    
    public Images UpdateImage(String location, String category, String otherDetails, String techDetails,boolean visibleAll,int imgID){
        
        Images rtrn = null;//initial return state
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off
            
            String sql="UPDATE photos SET OtherDetails = ?, TechDetails = ?, categories = ?, Location = ?,Public = ? WHERE photo_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, otherDetails);
            stmt.setString(2, techDetails);
            stmt.setString(3, category);
            stmt.setString(4, location);
            stmt.setBoolean(5, visibleAll);
            stmt.setInt(6, imgID);
                                   
            int effectedRowCount=stmt.executeUpdate();//get number of effected rows in DB
            
            if(effectedRowCount>0){
                //if opreation done correctly return set to true
                conn.commit();//commit result
                
                String getSql="SELECT * FROM photos WHERE photo_id = ? ";
                
                PreparedStatement statement = conn.prepareStatement(getSql);
                statement.setInt(1, imgID);
                
                ResultSet rs = statement.executeQuery();
                
                Images image = new Images();
                while(rs.next()){
                    
                    image.setImgID(rs.getInt("photo_id"));
                    image.setUserID(rs.getInt("user_id"));
                    image.setImgPath(rs.getString("photo_url"));
                    image.setLocation(rs.getString("Location"));
                    image.setCategory(rs.getString("Categories"));
                    image.setOtherDetails(rs.getString("OtherDetails"));
                    image.setTechDetails(rs.getString("TechDetails"));
                    image.setDate(rs.getDate("timestamp"));
                    image.setVilibleAll(rs.getBoolean("Public"));
                    image.setFavourite(rs.getBoolean("Favourite"));
                } 
                
                rtrn = image;
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
    
    public boolean SetAddFavouriteStatus(int imgID,boolean favourite){
        
        boolean rtrn = false;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off
            
            String sql="UPDATE photos SET Favourite = ? WHERE photo_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setBoolean(1, favourite);
            stmt.setInt(2, imgID);
                                   
            int effectedRowCount=stmt.executeUpdate();//get number of effected rows in DB
            
            if(effectedRowCount>0){
                //if opreation done correctly return set to true
                conn.commit();//commit result
                rtrn = true;
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
    
    public boolean DeleteImage(int imgID){
        
        boolean rtrn = false;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off
            
            String sql="DELETE FROM photos WHERE photo_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
        
            stmt.setInt(1, imgID);
                                   
            int effectedRowCount=stmt.executeUpdate();//get number of effected rows in DB
            
            if(effectedRowCount>0){
                //if opreation done correctly return set to true
                conn.commit();//commit result
                rtrn = true;
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
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
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
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
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
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
                images.add(image);
            }
            
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return images;
    }
    
    public List<Images> DisplayFavouriteImages(int userID){
        
        List<Images> images = new ArrayList<>();
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT * FROM photos WHERE user_id = ? AND Favourite = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setInt(1,userID);
            stmt.setBoolean(2, true);//Only if image favourite is true
            
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
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
                images.add(image);
            }
            
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
       
        return images;
    }
    
    //get total image count
    public int TotalImageCount(int userID){
        int count = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT COUNT(*) AS total_imageCount FROM photos WHERE user_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
        
            stmt.setInt(1, userID);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                count = rs.getInt("total_imageCount");
            }
                                   
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return count;
    }
    
    public int FavouriteImageCount(int userID){
        int count = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT COUNT(*) AS favourite_imageCount FROM photos WHERE user_id = ? AND Favourite = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
        
            stmt.setInt(1, userID);
            stmt.setBoolean(2, true);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                count = rs.getInt("favourite_imageCount");
            }
                                   
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return count;
    }
    
    public int PublicImageCount(int userID){
        int count = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT COUNT(*) AS public_imageCount FROM photos WHERE user_id = ? AND Public = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
        
            stmt.setInt(1, userID);
            stmt.setBoolean(2, true);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                count = rs.getInt("public_imageCount");
            }
                                   
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
        
        return count;
    }
    
    public List<Images> DisplayRecentImages(int userID){
        
        List<Images> images = new ArrayList<>();
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT * FROM photos WHERE user_id = ? ORDER BY timestamp DESC Limit 6";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
           
            stmt.setInt(1,userID);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Images image = new Images();
                image.setImgPath(rs.getString("photo_url"));
                image.setDate(rs.getDate("timestamp"));
                images.add(image);
            }
            
        }catch(ClassNotFoundException e){
            System.out.println("Class Not Found:"+e.getMessage());
        }catch(SQLException e){
            System.out.println("SQL Exception:"+e.getMessage());
        }
       
        return images;
    }
    
    public List<Images> DisplayPublicImages(){
        
        List<Images> images = new ArrayList<>();
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = CreateConnection.getConnection();
            
            String sql="SELECT * FROM photos WHERE Public = TRUE";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Images image = new Images();

                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setDate(rs.getDate("timestamp"));
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
