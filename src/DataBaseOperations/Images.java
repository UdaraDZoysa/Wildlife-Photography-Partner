/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.Date;

/**
 *
 * @author Harsha
 */
public class Images {
    
    private String imgPath;
    private String location;
    private String category;
    private String otherDetails;
    private String techDetails;
    private int userID;
    private int imgID;
    private Date date;
    private boolean vilibleAll;
    private boolean favourite;

    public Images(int userID,String imgPath, String location, String category, String otherDetails, String techDetails,boolean vilibleAll) {
        this.userID = userID;
        this.imgPath = imgPath;
        this.location = location;
        this.category = category;
        this.otherDetails = otherDetails;
        this.techDetails = techDetails;
        this.vilibleAll = vilibleAll;
    }
    
    public Images(){
    }
    
    public String getTechDetails() {
        return techDetails;
    }

    public void setTechDetails(String techDetails) {
        this.techDetails = techDetails;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isVilibleAll() {
        return vilibleAll;
    }

    public void setVilibleAll(boolean vilibleAll) {
        this.vilibleAll = vilibleAll;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
     
}
