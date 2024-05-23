/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Harsha
 */
public class ImageService {
    
    private ImageRepository imageRepository = new ImageRepository();
    public static List<Images> images;
    
    //this method handle Add Image opreration
    //arguments coming from Add_imageCOntroller class
    //if image add successfully return true
    public boolean addImage(int userID,String imgPath, String location, String category, String otherDetails, String techDetails,boolean vilibleAll){
        
        Images img = new Images(userID, imgPath, location, category, otherDetails, techDetails,vilibleAll);
        
        return imageRepository.AddImage(img);
        
    }
    
    public Images UpdateImage(String location, String category, String otherDetails, String techDetails,boolean vilibleAll,int imgID){
       
        return imageRepository.UpdateImage(location, category, otherDetails, techDetails,vilibleAll,imgID);
        
    }
    
    public boolean AddToFavourite(int imgID,boolean favourite){
        return imageRepository.SetAddFavouriteStatus(imgID, favourite);
    }
    
    public boolean DeleteImage(int imgID){
        return imageRepository.DeleteImage(imgID);
    }
    
    public void searchImageUsingExactDate(int userID,Date date,String location,String category){
        
        images = imageRepository.SearchImagesUsingExactDate(userID, date, location, category);
    }
    
    public void searchImageUsingDateRange(int userID,Date startDate,Date endDate,String location,String category){
        images = imageRepository.SearchImagesUsingDateRange(userID, startDate, endDate, location, category);
    }
    
    public void searchImageWithoutDate(int userID,String location,String category){
        
        images = imageRepository.SearchImagesWithoutDate(userID, location, category);
    }

}
