/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package photography.partner;

import DataBaseOperations.ImageService;
import DataBaseOperations.Images;
import DataBaseOperations.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class ViewSearchedImageController implements Initializable {
    
    private User u;
    static boolean toggle;
    private static String category;
    private static String location;
    private static int i;//Use to memorise the image index
    private static int flag;//Use to indicate this class load from SelectImage(1) class of Memories Class(2)
    private ImageService imageService = new ImageService();
    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    public void displayImageWithDetails(int index,User user,int fromFlag){
        
        i=index;
        u=user;
        flag = fromFlag;
        toggle = ImageService.images.get(index).isFavourite();
        location = ImageService.images.get(index).getLocation();
        category = ImageService.images.get(index).getCategory();
        
        VBox card = new VBox(10);
        
        try{
                File file = new File(ImageService.images.get(index).getImgPath());
                if(file.exists()){
                    Image image = new Image(file.toURI().toString());
                    ImageView detailedImageView = new ImageView(image);
                    StackPane imageWrapper = new StackPane(detailedImageView);
                    imageWrapper.setPrefSize(600, 600);
                    
                    detailedImageView.setFitWidth(600);
                    detailedImageView.setFitHeight(600);
                    detailedImageView.setPreserveRatio(true);
                    detailedImageView.getStyleClass().add("Tileimage-view");

                    card.getStyleClass().add("detailed-image-card");
                    card.setPadding(new Insets(10));
                    
                    card.getChildren().addAll(imageWrapper);
                    
                }
            }catch(Exception e){
                System.out.println("Error loading image:"+e.getMessage());
            }
        
        Label dateTextLabel = new Label("Saved Date:");
        dateTextLabel.getStyleClass().add("detailed-card-Text");     
        Label dateLabel = new Label(ImageService.images.get(index).getDate().toString());
        dateLabel.getStyleClass().add("detailed-card-label");
        
        Label locationTextLabel = new Label("Location:");
        locationTextLabel.getStyleClass().add("detailed-card-Text");       
        Label locationLabel = new Label(ImageService.images.get(index).getLocation());
        locationLabel.getStyleClass().add("detailed-card-label");
        
        Label categoryTextLabel = new Label("Category:");
        categoryTextLabel.getStyleClass().add("detailed-card-Text");
        Label categoryLabel = new Label(ImageService.images.get(index).getCategory());
        categoryLabel.getStyleClass().add("detailed-card-label");
        
        Label privacyStatusTextLabel = new Label("Privacy Status:");
        privacyStatusTextLabel.getStyleClass().add("detailed-card-Text");
        
        String status;
        if(ImageService.images.get(index).isVilibleAll()){
            status = "Public Image";
        }else{
            status = "Private Image";
        }
        
        Label privacyStatusLabel = new Label(status);
        privacyStatusLabel.getStyleClass().add("detailed-card-label");
        
        Label OtherDetailsTextLabel = new Label("Other Details:");
        OtherDetailsTextLabel.getStyleClass().add("detailed-card-Text");
        GridPane.setValignment(OtherDetailsTextLabel, VPos.TOP);
        
        Text otherDetailsText = new Text(ImageService.images.get(index).getOtherDetails());
        otherDetailsText.setFill(Color.web("#21381B")); // Set text color
        otherDetailsText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        TextFlow otherDetailsTextFlow = new TextFlow(otherDetailsText);
        otherDetailsTextFlow.setStyle("-fx-padding: 4; -fx-background-color:white; -fx-border-color: #21381B; -fx-border-width: 0 0 3 5; -fx-border-style: solid; -fx-pref-width:450; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.42), 15, 0.0, 0, 10);");
        
        Label techDetailsTextLabel = new Label("Technical Details:");
        techDetailsTextLabel.getStyleClass().add("detailed-card-Text");
        GridPane.setValignment(techDetailsTextLabel, VPos.TOP);
        
        Text techDetailsText = new Text(ImageService.images.get(index).getTechDetails());
        techDetailsText.setFill(Color.web("#21381B")); // Set text color
        techDetailsText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        TextFlow techDetailsTextFlow = new TextFlow(techDetailsText);
        techDetailsTextFlow.setStyle("-fx-padding: 4; -fx-background-color:white; -fx-border-color: #21381B; -fx-border-width: 0 0 3 5; -fx-border-style: solid; -fx-pref-width:450; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.42), 15, 0.0, 0, 10);");
        
        //Favourite Button
        ToggleButton addFavouriteBtn = new ToggleButton("Favourite Image");
        addFavouriteBtn.setSelected(ImageService.images.get(index).isFavourite());
        addFavouriteBtn.getStyleClass().add("toggle-button");
        addFavouriteBtn.setOnAction(event -> handleAddFavouriteBtnAction());
        GridPane.setValignment(addFavouriteBtn, VPos.TOP);
        GridPane.setHalignment(addFavouriteBtn, HPos.RIGHT);
        
        //Back Button
        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("card-button");
        
        if(flag == 1){
            //Here I use 1 as flag that indicate select_imageController class
            backBtn.setOnAction(event -> handleBackToSelectImageBtnAction(event));
        }else if(flag == 2){
            //Here I use 2 as flag that indicate memoriesController class
            backBtn.setOnAction(event -> handleBackToMemoriesBtnAction(event));
        }
        
        //Edit Button
        Button editBtn = new Button("Edit Details");
        editBtn.getStyleClass().add("card-button");
        editBtn.setOnAction(event->editImage(ImageService.images.get(index),event));
        
        //Delete Button
        Button deleteBtn = new Button("Delete Image");
        deleteBtn.getStyleClass().add("card-button");
        deleteBtn.setOnAction(event -> handleDeleteAction(ImageService.images.get(index).getImgID(),event));
        
        HBox buttonBox = new HBox(60);
        buttonBox.setPadding(new Insets(40));
        
        buttonBox.getChildren().addAll(backBtn,editBtn,deleteBtn);
        
        //get Image Button
        Button getImgBtn = new Button("Get Image");
        getImgBtn.getStyleClass().add("get-img-button");
        getImgBtn.setOnAction(event->saveImageToFavoriteLocation(event, ImageService.images.get(index).getImgPath()));
        GridPane.setValignment(getImgBtn,VPos.TOP );
        GridPane.setHalignment(getImgBtn,HPos.CENTER );
        
        GridPane.setColumnSpan(buttonBox, 2);
        GridPane.setColumnIndex(getImgBtn, 2);
            
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 10, 5, 10));
        gridPane.setVgap(25);
        gridPane.setHgap(10);
        gridPane.getStyleClass().add("my-custom-scrollpane");
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(195);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(325);
        gridPane.getColumnConstraints().addAll(column1,column2);
        
        gridPane.add(addFavouriteBtn, 1, 0);
        gridPane.add(dateTextLabel, 0, 1);
        gridPane.add(dateLabel, 1, 1);
        gridPane.add(privacyStatusTextLabel, 0, 2);
        gridPane.add(privacyStatusLabel, 1, 2);
        gridPane.add(locationTextLabel, 0, 3);
        gridPane.add(locationLabel, 1, 3);
        gridPane.add(categoryTextLabel, 0, 4);
        gridPane.add(categoryLabel, 1, 4);
        gridPane.add(OtherDetailsTextLabel, 0, 5);
        gridPane.add(otherDetailsTextFlow, 1, 5);
        gridPane.add(techDetailsTextLabel, 0, 6);
        gridPane.add(techDetailsTextFlow, 1, 6);
        gridPane.add(buttonBox, 0, 7,2,1);
        gridPane.add(getImgBtn, 0, 8,2,1);
        
        
        TilePane detailedTile = new TilePane();
        detailedTile.getChildren().add(gridPane);
        detailedTile.setPrefTileWidth(500);
        detailedTile.setPrefTileHeight(1000);
        detailedTile.getStyleClass().add("my-custom-scrollpane");
        
        ScrollPane detailScrollPane = new ScrollPane(detailedTile);
        detailScrollPane.setPrefWidth(500);
        detailScrollPane.setPrefHeight(600);
        detailScrollPane.setFitToWidth(true);
        detailScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        detailScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        detailScrollPane.getStyleClass().add("my-custom-scrollpane");
       
        //container of Image card and tetails
        HBox detailedView = new HBox(20);
        detailedView.setPadding(new Insets(20, 20, 20, 20));
        detailedView.getStyleClass().add("my-custom-scrollpane");
        detailedView.getChildren().addAll(card,detailScrollPane);
        
        mainPane.getChildren().clear();
        mainPane.getChildren().add(detailedView);
    }
    
    
    //Once User switch to edit Image UI, call that method
    @FXML
    public void displayEditedImageWithDetails(Images img,User user){
        
        u = user;
        toggle = img.isFavourite();
        VBox card = new VBox(10);
        
        try{
                File file = new File(img.getImgPath());
                if(file.exists()){
                    Image image = new Image(file.toURI().toString());
                    ImageView detailedImageView = new ImageView(image);
                    StackPane imageWrapper = new StackPane(detailedImageView);
                    imageWrapper.setPrefSize(600, 600);
                    
                    detailedImageView.setFitWidth(600);
                    detailedImageView.setFitHeight(600);
                    detailedImageView.setPreserveRatio(true);
                    detailedImageView.getStyleClass().add("Tileimage-view");

                    card.getStyleClass().add("detailed-image-card");
                    card.setPadding(new Insets(10));
                    
                    card.getChildren().addAll(imageWrapper);
                    
                }
            }catch(Exception e){
                System.out.println("Error loading image:"+e.getMessage());
            }
        
        Label dateTextLabel = new Label("Saved Date:");
        dateTextLabel.getStyleClass().add("detailed-card-Text");     
        Label dateLabel = new Label(img.getDate().toString());
        dateLabel.getStyleClass().add("detailed-card-label");
        
        Label locationTextLabel = new Label("Location:");
        locationTextLabel.getStyleClass().add("detailed-card-Text");       
        Label locationLabel = new Label(img.getLocation());
        locationLabel.getStyleClass().add("detailed-card-label");
        
        Label categoryTextLabel = new Label("Category:");
        categoryTextLabel.getStyleClass().add("detailed-card-Text");
        Label categoryLabel = new Label(img.getCategory());
        categoryLabel.getStyleClass().add("detailed-card-label");
        
        Label privacyStatusTextLabel = new Label("Privacy Status:");
        privacyStatusTextLabel.getStyleClass().add("detailed-card-Text");
        
        String status;
        if(img.isVilibleAll()){
            status = "Public Image";
        }else{
            status = "Private Image";
        }
        
        Label privacyStatusLabel = new Label(status);
        privacyStatusLabel.getStyleClass().add("detailed-card-label");
        
        Label OtherDetailsTextLabel = new Label("Other Details:");
        OtherDetailsTextLabel.getStyleClass().add("detailed-card-Text");
        GridPane.setValignment(OtherDetailsTextLabel, VPos.TOP);
        
        Text otherDetailsText = new Text(img.getOtherDetails());
        otherDetailsText.setFill(Color.web("#21381B")); // Set text color
        otherDetailsText.setFont(Font.font("System", FontWeight.NORMAL, 18));
        TextFlow otherDetailsTextFlow = new TextFlow(otherDetailsText);
        otherDetailsTextFlow.setStyle("-fx-padding: 4; -fx-background-color:white; -fx-border-color: #21381B; -fx-border-width: 0 0 3 5; -fx-border-style: solid; -fx-pref-width:450; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.42), 15, 0.0, 0, 10);");
        
        Label techDetailsTextLabel = new Label("Technical Details:");
        techDetailsTextLabel.getStyleClass().add("detailed-card-Text");
        GridPane.setValignment(techDetailsTextLabel, VPos.TOP);
        
        Text techDetailsText = new Text(img.getTechDetails());
        techDetailsText.setFill(Color.web("#21381B")); // Set text color
        techDetailsText.setFont(Font.font("System", FontWeight.NORMAL, 18));
        TextFlow techDetailsTextFlow = new TextFlow(techDetailsText);
        techDetailsTextFlow.setStyle("-fx-padding: 4; -fx-background-color:white; -fx-border-color: #21381B; -fx-border-width: 0 0 3 5; -fx-border-style: solid; -fx-pref-width:450; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.42), 15, 0.0, 0, 10);");
        
        //Favourite Button
        ToggleButton addFavouriteBtn = new ToggleButton("Favourite Image");
        addFavouriteBtn.setSelected(img.isFavourite());
        addFavouriteBtn.getStyleClass().add("toggle-button");
        addFavouriteBtn.setOnAction(event -> handleAddFavouriteBtnAction());
        GridPane.setValignment(addFavouriteBtn, VPos.TOP);
        GridPane.setHalignment(addFavouriteBtn, HPos.RIGHT);
        
        //Back Button
        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("card-button"); 
        
        if(flag == 1){
            //Here I use 1 as flag that indicate select_imageController class
            backBtn.setOnAction(event -> handleEditedBackBtnAction(img,event));
        }else if(flag == 2){
            //Here I use 2 as flag that indicate memoriesController class
            backBtn.setOnAction(event -> handleBackToMemoriesBtnAction(event));
        }
        
        //Edit Button
        Button editBtn = new Button("Edit Details");
        editBtn.getStyleClass().add("card-button");
        editBtn.setOnAction(event->editImage(img,event));
        
        //Delete Button
        Button deleteBtn = new Button("Delete Image");
        deleteBtn.getStyleClass().add("card-button");
        deleteBtn.setOnAction(event -> handleDeleteAction(img.getImgID(),event));
        
        HBox buttonBox = new HBox(60);
        buttonBox.setPadding(new Insets(40));
        
        buttonBox.getChildren().addAll(backBtn,editBtn,deleteBtn);
        
        //get Image Button
        Button getImgBtn = new Button("Get Image");
        getImgBtn.getStyleClass().add("get-img-button");
        getImgBtn.setOnAction(event->saveImageToFavoriteLocation(event, ImageService.images.get(i).getImgPath()));
        GridPane.setValignment(getImgBtn,VPos.TOP );
        GridPane.setHalignment(getImgBtn,HPos.CENTER );
        
        GridPane.setColumnSpan(buttonBox, 2);
        GridPane.setColumnIndex(getImgBtn, 2);
            
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 10, 5, 10));
        gridPane.setVgap(25);
        gridPane.setHgap(10);
        gridPane.getStyleClass().add("my-custom-scrollpane");
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(195);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(325);
        gridPane.getColumnConstraints().addAll(column1,column2);

        gridPane.add(addFavouriteBtn, 1, 0);
        gridPane.add(dateTextLabel, 0, 1);
        gridPane.add(dateLabel, 1, 1);
        gridPane.add(privacyStatusTextLabel, 0, 2);
        gridPane.add(privacyStatusLabel, 1, 2);
        gridPane.add(locationTextLabel, 0, 3);
        gridPane.add(locationLabel, 1, 3);
        gridPane.add(categoryTextLabel, 0, 4);
        gridPane.add(categoryLabel, 1, 4);
        gridPane.add(OtherDetailsTextLabel, 0, 5);
        gridPane.add(otherDetailsTextFlow, 1, 5);
        gridPane.add(techDetailsTextLabel, 0, 6);
        gridPane.add(techDetailsTextFlow, 1, 6);
        gridPane.add(buttonBox, 0, 7,2,1);
        gridPane.add(getImgBtn, 0, 8,2,1);
        
        
        TilePane detailedTile = new TilePane();
        detailedTile.getChildren().add(gridPane);
        detailedTile.setPrefTileWidth(500);
        detailedTile.setPrefTileHeight(900);
        detailedTile.getStyleClass().add("my-custom-scrollpane");
        
        ScrollPane detailScrollPane = new ScrollPane(detailedTile);
        detailScrollPane.setPrefWidth(500);
        detailScrollPane.setPrefHeight(600);
        detailScrollPane.setFitToWidth(true);
        detailScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        detailScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        detailScrollPane.getStyleClass().add("my-custom-scrollpane");
       
        HBox detailedView = new HBox(20);
        detailedView.setPadding(new Insets(20, 20, 20, 20));
        detailedView.getStyleClass().add("my-custom-scrollpane");
        detailedView.getChildren().addAll(card,detailScrollPane);
        
        mainPane.getChildren().clear();
        mainPane.getChildren().add(detailedView);
    }
    
    public void editImage(Images img,ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditDetails.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            EditDetailsController controller = loader.getController();
            controller.setImage(img);
            controller.setUser(u);
        
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
            //scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());
            
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            repositionWindow(stage);
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
        
    }
    
    // Delete Action
    public void handleDeleteAction(int imgID,ActionEvent event){
 
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to Delete the Image?");
            
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // If user click ok, Update details
            //Delete Image
            boolean success = imageService.DeleteImage(imgID);
            
            if(success){
                Alert successAlert=new Alert(Alert.AlertType.CONFIRMATION);
                successAlert.setTitle("Image Deleted");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Image Deleted Successfully!");
                successAlert.showAndWait();
                
                //If Image deleted load Select Image UI using loadEditedImageDynamically() method. Because that method omit the ecently viewed image
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Select_image.fxml"));
                    Parent root =loader.load();
            
                    // Retrieve the controller associated with the FXML file and set the u
                    Select_imageController controller = loader.getController();
                    controller.setUser(u);
                    controller.loadEditedImageDynamically(i);
            
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
                
            }else{
                Alert failedAlert=new Alert(Alert.AlertType.ERROR);
                failedAlert.setTitle("Image not Deleted");
                failedAlert.setHeaderText(null);
                failedAlert.setContentText("Something Went Wrong!");
                failedAlert.showAndWait();  
            }
        }
        
    }
    
    //Before Image Edit Call That method to back to SelectImageController class 
    public void handleBackToSelectImageBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Select_image.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            Select_imageController controller = loader.getController();
            controller.loadImageDynamically();
            controller.setUser(u);
        
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
    
    //After Image Edit Call That method to back to SelectImageController class
    public void handleEditedBackBtnAction(Images img,ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Select_image.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            Select_imageController controller = loader.getController();
            controller.setUser(u);
            
            if(location.equals(img.getLocation()) && category.equals(img.getCategory())){
                //If Location and Category Not Changed
                controller.loadImageDynamically(); 
            }else{
                controller.loadEditedImageDynamically(i);
            }
            
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
    
    //Back to MemoriesController Class
    public void handleBackToMemoriesBtnAction(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Memories.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            MemoriesController controller = loader.getController();
            controller.setUser(u);
            controller.loadFavouriteImages();
        
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
    
    @FXML
    private void saveImageToFavoriteLocation(ActionEvent event, String imagePath) {
        FileChooser fileChooser = new FileChooser();// Create a new FileChooser object to select file location
        fileChooser.setTitle("Save Image As");
        
        // Set up a filter to only show image files (.png, .jpg, .jpeg) in the file chooser.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Open the save dialog and get the selected file location.
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            try {
                Files.copy(Paths.get(imagePath), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Error:"+e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save image.");
                alert.show();
            }
        }
    }

    
    public void handleAddFavouriteBtnAction(){
        toggle = !toggle;
        
        ImageService.images.get(i).setFavourite(toggle);
        
        boolean success = imageService.AddToFavourite(ImageService.images.get(i).getImgID(), toggle);
        if(success){
            if(toggle){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Added to Favourite");
                alert.setHeaderText(null);
                alert.setContentText("Image Added to Favourite Collection Successfully!");
                alert.showAndWait();
            }else{
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Removed from Favourite");
                alert.setHeaderText(null);
                alert.setContentText("Image Removed from Favourite Collection Successfully!");
                alert.showAndWait();
            }
            
        }
        
    }
    
    private void repositionWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
    }    
    
}
