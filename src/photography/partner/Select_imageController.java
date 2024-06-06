/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.ImageService;
import DataBaseOperations.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class Select_imageController implements Initializable {
    
    private User u;
    //List<Images> images = new ArrayList<>();
    
    String locationText;
    String categoryText;
    
    private ImageService imageService = new ImageService();
    LocalDate currentDate;
    
    @FXML
    private Label dateLabel;
    
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
    
    public void setUser(User user){
        if(user!=null){
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
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            repositionWindow(stage);
            
            // Display the updated stage
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
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
        if(ImageService.images.isEmpty()){
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
                    
                    //containter of Image and short details
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
    
    //If User Edit Image Details Call That Method
    @FXML
    public void loadEditedImageDynamically(int index){
        
        for(int i=0;i<ImageService.images.size();i++){
            if(i == index){
                continue;
            }else{
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
                        
                        //main container of Image and few details
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
    }
    
    private Node createImageCard(int index){
        
        //partial container of details
        VBox detailsVbox = new VBox(5);
        detailsVbox.setPadding(new Insets(5,10,5,10));
        
        //container of labels
        HBox dateBox = new HBox(10);
        Label dateTextLabel = new Label("Saved Date:");
        dateTextLabel.getStyleClass().add("text-label");
        Label newdateLabel = new Label(ImageService.images.get(index).getDate().toString());
        newdateLabel.getStyleClass().add("card-label");
        
        //container of labels
        HBox locationBox = new HBox(10);
        Label locationTextLabel = new Label("Location:");
        locationTextLabel.getStyleClass().add("text-label");
        Label locationLabel = new Label(ImageService.images.get(index).getLocation());
        locationLabel.getStyleClass().add("card-label");
        
        //container of labels
        HBox categoryBox = new HBox(10);
        Label categoryTextLabel = new Label("Category:");
        categoryTextLabel.getStyleClass().add("text-label"); 
        Label categoryLabel = new Label(ImageService.images.get(index).getCategory());
        categoryLabel.getStyleClass().add("card-label");
        
        Hyperlink moreHyperLink = new Hyperlink("more...");
        moreHyperLink.getStyleClass().add("hyperLink");
        
        moreHyperLink.setOnAction(event -> displayImageWithDetails(event,index));
        
        //add date labels to container
        dateBox.getChildren().addAll(dateTextLabel,newdateLabel);
        
        //add location labels to container
        locationBox.getChildren().addAll(locationTextLabel,locationLabel);
        
        //add category labels to container
        categoryBox.getChildren().addAll(categoryTextLabel,categoryLabel);
        
        //add label containers to partial container
        detailsVbox.getChildren().addAll(dateBox,locationBox,categoryBox,moreHyperLink);
        
        return detailsVbox;
    }
    
    public void displayImageWithDetails(ActionEvent event,int index){
        
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewSearchedImage.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            ViewSearchedImageController controller = loader.getController();
            controller.displayImageWithDetails(index,u,1);
        
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());
            
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            repositionWindow(stage);
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
        
    }
    
    private void repositionWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
    
    private void clearFieldValues(){
        locationField.clear();
        categoryField.clear();
        locationText = null;
        categoryText = null;
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
        // Scale transition for mouse enter
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), card);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        // Scale transition for mouse exit
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), card);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleDown.stop(); // Stop the other transition if it's running
            scaleUp.playFromStart(); // Play the scale-up transition
        });

        card.setOnMouseExited(e -> {
            scaleUp.stop(); // Stop the other transition if it's running
            scaleDown.playFromStart(); // Play the scale-down transition
        });

    } 
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    @FXML
    private void handleAddBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add_image.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the user
            Add_imageController controller = loader.getController();
            controller.setUser(u);
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handlePlanTripBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Plan_trip.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the user
            Plan_tripController controller = loader.getController();
            controller.setUser(u);
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handleMemoBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Memories.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            MemoriesController controller = loader.getController();
            controller.setUser(u);
            controller.loadFavouriteImages();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
                    
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @FXML
    private void handleTipsBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Tips.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the user
            TipsController controller = loader.getController();
            controller.setUser(u);
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgTilePane.setAlignment(Pos.CENTER); // Ensures that the content is centered
        imgTilePane.setHgap(25); // Horizontal gap between children
        imgTilePane.setVgap(30); // Vertical gap between children
        
        //get Local Date
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter); 
        dateLabel.setText(formattedDate);
    }
}    
