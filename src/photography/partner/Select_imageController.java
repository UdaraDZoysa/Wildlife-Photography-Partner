/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.ImageService;
import DataBaseOperations.User;
import DataBaseOperations.Images;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.sql.Date;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class Select_imageController implements Initializable {
    
    private User u;
    //List<Images> images = new ArrayList<>();
    
    static String locationText;
    static String categoryText;
    
    private ImageService imageService = new ImageService();
    
    @FXML
    private TilePane imgTilePane;
    
    @FXML
    private DatePicker fromPicker;
    
    @FXML
    private DatePicker toPicker;
    
    @FXML
    private DatePicker exactPicker;
    
    @FXML
    private TextField locationField;
    
    @FXML
    private TextField categoryField;

    @FXML
    private ImageView profilePicView;
    
    @FXML
    private Label uName;
    
    public void setUser(User user){
        if(user!=null){
            uName.setText("Hello,"+user.getUserName()+"!");
            Image image =new Image(new File(user.getProfilePic()).toURI().toString());
            profilePicView.setImage(image);
            u=user;
        }
    }
    
    @FXML
    public void handlehomeBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DashBoard2.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            DashBoard2Controller controller = loader.getController();
            controller.setUser(u);
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //select location
    
    @FXML
    public void handleWilpattuBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Wilpattu";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Wilpattu")){
                locationText += ", Wilpattu";
                locationField.setText(locationText);
            }
        }    
    }
    
    @FXML
    public void handleHecBtnAction(ActionEvent event){

        if(locationText==null || locationText.isEmpty()){
            locationText="Hurulu Eco Park";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Hurulu Eco Park")){
                locationText += ", Hurulu Eco Park";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleKaudullaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Kaudulla";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Kaudulla")){
                locationText += ", Kaudulla";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleMinneriyaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Minneriya";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Minneriya")){
                locationText += ", Minneriya";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleWasgamuwaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Wasgamuwa";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Wasgamuwa")){
                locationText += ", Wasgamuwa";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleHortonPlainsBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Horton Plains";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Horton Plains")){
                locationText += ", Horton Plains";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleGaloyaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Galoya";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Galoya")){
                locationText += ", Galoya";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleUdawalaweBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Udawalawe";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Udawalawe")){
                locationText += ", Udawalawe";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleSinharajaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Sinharaja";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Sinharaja")){
                locationText += ", Sinharaja";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleBundalaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Bundala";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Bundala")){
                locationText += ", Bundala";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleYalaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Yala";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Yala")){
                locationText += ", Yala";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleKumanaBtnAction(ActionEvent event){
        
        if(locationText==null || locationText.isEmpty()){
            locationText="Kumana";
            locationField.setText(locationText);
        }else{
            if(!locationText.contains("Kumana")){
                locationText += ", Kumana";
                locationField.setText(locationText);
            }
        }
    }
    
    @FXML
    public void handleSelectAllLocationBtnAction(ActionEvent event){
        locationField.setText("Wilpattu, Hurulu Eco Park, Kaudulla, Minneriya, Wasgamuwa, Horton Plains, Galoya, Udawalawe, Sinharaja, Bundala, Yala, Kumana");
    }
    
    
    //choose category
    
    @FXML
    private void handleMammalsBtnAction(ActionEvent event){
        
        if(categoryText==null || categoryText.isEmpty()){
            categoryText="Mammals";
            categoryField.setText(categoryText);
        }else{
            if(!categoryText.contains("Mammals")){
                categoryText += ", Mammals";
                categoryField.setText(categoryText);
            }
        }
    }
    
    @FXML
    private void handleBirdsBtnAction(ActionEvent event){
        
        if(categoryText==null || categoryText.isEmpty()){
            categoryText="Birds";
            categoryField.setText(categoryText);
        }else{
            if(!categoryText.contains("Birds")){
                categoryText += ", Birds";
                categoryField.setText(categoryText);
            }
        }
    }
    
    @FXML
    private void handleRAABtnAction(ActionEvent event){
        
        if(categoryText==null || categoryText.isEmpty()){
            categoryText="Reptils and Amphibians";
            categoryField.setText(categoryText);
        }else{
            if(!categoryText.contains("Reptils and Amphibians")){
                categoryText += ", Reptils and Amphibians";
                categoryField.setText(categoryText);
            }
        }
    }
    
    @FXML
    private void handleIAABtnAction(ActionEvent event){
        
        if(categoryText==null || categoryText.isEmpty()){
            categoryText="Insects and Arachnids";
            categoryField.setText(categoryText);
        }else{
            if(!categoryText.contains("Insects and Arachnids")){
                categoryText += ", Insects and Arachnids";
                categoryField.setText(categoryText);
            }
        }
    }
    
    @FXML
    private void handleAquaticBtnAction(ActionEvent event){
        
        if(categoryText==null || categoryText.isEmpty()){
            categoryText="Aquatic Life";
            categoryField.setText(categoryText);
        }else{
            if(!categoryText.contains("Aquatic Life")){
                categoryText += ", Aquatic Life";
                categoryField.setText(categoryText);
            }
        }
    }
    
    @FXML
    private void handleOtherBtnAction(ActionEvent event){
        
        if(categoryText==null || categoryText.isEmpty()){
            categoryText="Other";
            categoryField.setText(categoryText);
        }else{
            if(!categoryText.contains("Other")){
                categoryText += ", Other";
                categoryField.setText(categoryText);
            }
        }
    }
    
    @FXML
    private void handleSelectAllCategoriesBtnAction(ActionEvent event){
        categoryField.setText("Mammals, Birds, Reptils and Amphibians, Insects and Arachnids, Aquatic Life, Other");
    }
    
    @FXML
    private void handleSearchBtnAction(ActionEvent event){
        
        imgTilePane.getChildren().clear();
        
        String location = locationField.getText();
        String category = categoryField.getText();
        int userID = u.getUserID();
        
        if(location == null || category == null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText(null);
            alert.setContentText("Missing Some inputs!");
            alert.showAndWait();  
        }else{
            
            if(exactPicker.getValue() != null){
                if(fromPicker.getValue() == null && toPicker.getValue() == null){//selected only exact date
                    
                    Date date = Date.valueOf(exactPicker.getValue());
             
                    imageService.searchImageUsingExactDate(userID, date, location, category);
                    loadImageDynamically();
                    clearFieldValues();
                    
                }else{
                    errorDateSelectMessage();
                }
                
            }else if(fromPicker.getValue() != null || toPicker.getValue() != null){
                
                if(fromPicker.getValue() != null && toPicker.getValue() != null){//correctly selected date range
                    
                    Date startDate = Date.valueOf(fromPicker.getValue());
                    Date endDate = Date.valueOf(toPicker.getValue());
            
                    imageService.searchImageUsingDateRange(userID, startDate, endDate, location, category);
                    loadImageDynamically();
                    clearFieldValues();
                }else{
                    errorDateSelectMessage();
                }
            }else{
                //user not selected date
                imageService.searchImageWithoutDate(userID, location, category);
                loadImageDynamically();
                clearFieldValues();
                
            }
        }
        if(ImageService.images == null || ImageService.images.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Image Not Found");
            alert.setHeaderText(null);
            alert.setContentText("Can not Find Any Result With Given Inputs!");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void loadImageDynamically(){
        
        for(int i=0;i<ImageService.images.size();i++){
            try{
                File file = new File(ImageService.images.get(i).getImgPath());
                if(file.exists()){
                    Image image = new Image(file.toURI().toString());
                    ImageView imageView = new ImageView(image);
                    StackPane imageWrapper = new StackPane(imageView);
                    imageWrapper.setPrefSize(250, 250);
                    
                    imageView.setFitWidth(250);
                    imageView.setFitHeight(250);
                    imageView.setPreserveRatio(true);
                    imageView.getStyleClass().add("Tileimage-view");
                    
                    VBox card = new VBox(10);
                    card.getStyleClass().add("image-card");
                    card.setPadding(new Insets(10));
                    
                    card.getChildren().addAll(imageWrapper,createImageCard(i));
                    setupInterationsOfImages(card);
                    
                    imgTilePane.getChildren().add(card);
                    
                }
            }catch(Exception e){
                System.out.println("Error loading image:"+e.getMessage());
            }
        }
    }
    
    private Node createImageCard(int index){
        VBox detailsVbox = new VBox(5);
        detailsVbox.setPadding(new Insets(5,10,5,10));
        
        HBox dateBox = new HBox(10);
        Label dateTextLabel = new Label("Saved Date:");
        dateTextLabel.getStyleClass().add("text-label");
        Label dateLabel = new Label(ImageService.images.get(index).getDate().toString());
        dateLabel.getStyleClass().add("card-label");
        
        HBox locationBox = new HBox(10);
        Label locationTextLabel = new Label("Location:");
        locationTextLabel.getStyleClass().add("text-label");
        Label locationLabel = new Label(ImageService.images.get(index).getLocation());
        locationLabel.getStyleClass().add("card-label");
        
        HBox categoryBox = new HBox(10);
        Label categoryTextLabel = new Label("Category:");
        categoryTextLabel.getStyleClass().add("text-label"); 
        Label categoryLabel = new Label(ImageService.images.get(index).getCategory());
        categoryLabel.getStyleClass().add("card-label");
        
        Hyperlink moreHyperLink = new Hyperlink("more...");
        moreHyperLink.getStyleClass().add("hyperLink");
        
        moreHyperLink.setOnAction(event -> displayImageWithDetails(event,index));
        
        dateBox.getChildren().addAll(dateTextLabel,dateLabel);
        locationBox.getChildren().addAll(locationTextLabel,locationLabel);
        categoryBox.getChildren().addAll(categoryTextLabel,categoryLabel);
        detailsVbox.getChildren().addAll(dateBox,locationBox,categoryBox,moreHyperLink);
        
        return detailsVbox;
    }
    
    public void displayImageWithDetails(ActionEvent event,int index){
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSearchedImage.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            ViewSearchedImageController controller = loader.getController();
            controller.displayImageWithDetails(index,u);
        
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());
            
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    private void clearFieldValues(){
        locationField.setText(null);
        categoryField.setText(null);
        exactPicker.setValue(null);
        fromPicker.setValue(null);
        toPicker.setValue(null);
    }
    
    private void errorDateSelectMessage(){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Date Selection Error");
        alert.setHeaderText(null);
        alert.setContentText("Invalid Date input!");
        
        exactPicker.setValue(null);
        fromPicker.setValue(null);
        toPicker.setValue(null);
        
        alert.showAndWait();
    }
    
    private void setupInterationsOfImages(VBox card) {
        card.setOnMouseEntered(e -> {
        card.setScaleX(1.1);  // Scale up the wrapper
        card.setScaleY(1.1);
        });
        
        card.setOnMouseExited(e -> {
        card.setScaleX(1.0);  // Return to normal scale
        card.setScaleY(1.0);
        });

    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgTilePane.setAlignment(Pos.CENTER); // Ensures that the content is centered
        imgTilePane.setHgap(35); // Horizontal gap between children
        imgTilePane.setVgap(25); // Vertical gap between children
    }
}    
