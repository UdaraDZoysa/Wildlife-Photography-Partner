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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Harsha
 */
public class ViewSearchedImageController implements Initializable {
    
    private User u;
    private String category;
    private String location;
    private int i;
    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    public void displayImageWithDetails(int index,User user){
        
        i=index;
        u=user;
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
        
        Label OtherDetailsTextLabel = new Label("Other Details:");
        OtherDetailsTextLabel.getStyleClass().add("detailed-card-Text");
        GridPane.setValignment(OtherDetailsTextLabel, VPos.TOP);
        
        Text otherDetailsText = new Text(ImageService.images.get(index).getOtherDetails());
        otherDetailsText.setFill(Color.web("#21381B")); // Set text color
        otherDetailsText.setFont(Font.font("System", FontWeight.NORMAL, 18));
        TextFlow otherDetailsTextFlow = new TextFlow(otherDetailsText);
        otherDetailsTextFlow.setStyle("-fx-padding: 4; -fx-background-color:white; -fx-border-color: #21381B; -fx-border-width: 0 0 3 5; -fx-border-style: solid; -fx-pref-width:450; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.42), 15, 0.0, 0, 10);");
        
        Label techDetailsTextLabel = new Label("Technical Details:");
        techDetailsTextLabel.getStyleClass().add("detailed-card-Text");
        GridPane.setValignment(techDetailsTextLabel, VPos.TOP);
        
        Text techDetailsText = new Text(ImageService.images.get(index).getTechDetails());
        techDetailsText.setFill(Color.web("#21381B")); // Set text color
        techDetailsText.setFont(Font.font("System", FontWeight.NORMAL, 18));
        TextFlow techDetailsTextFlow = new TextFlow(techDetailsText);
        techDetailsTextFlow.setStyle("-fx-padding: 4; -fx-background-color:white; -fx-border-color: #21381B; -fx-border-width: 0 0 3 5; -fx-border-style: solid; -fx-pref-width:450; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.42), 15, 0.0, 0, 10);");
        
        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("card-button");
        backBtn.setOnAction(event -> handleBackBtnAction(event));
        GridPane.setValignment(backBtn, VPos.BOTTOM);
        GridPane.setHalignment(backBtn, HPos.CENTER);
        
        Button editBtn = new Button("Edit Detail");
        editBtn.getStyleClass().add("card-button");
        editBtn.setOnAction(event->ImageService.images.get(index));
        GridPane.setValignment(editBtn, VPos.BOTTOM);
        GridPane.setHalignment(editBtn, HPos.CENTER);
            
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

        
        gridPane.add(dateTextLabel, 0, 0);
        gridPane.add(dateLabel, 1, 0);
        gridPane.add(locationTextLabel, 0, 1);
        gridPane.add(locationLabel, 1, 1);
        gridPane.add(categoryTextLabel, 0, 2);
        gridPane.add(categoryLabel, 1, 2);
        gridPane.add(OtherDetailsTextLabel, 0, 3);
        gridPane.add(otherDetailsTextFlow, 1, 3);
        gridPane.add(techDetailsTextLabel, 0, 4);
        gridPane.add(techDetailsTextFlow, 1, 4);
        gridPane.add(backBtn, 0, 6);
        gridPane.add(editBtn, 1, 6);
        
        
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
    
    @FXML
    public void displayEditedImageWithDetails(Images img){
        
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
        
        Button backBtn = new Button("Back");
        backBtn.getStyleClass().add("card-button");
        backBtn.setOnAction(event -> handleEditedBackBtnAction(img,event));
        GridPane.setValignment(backBtn, VPos.BOTTOM);
        GridPane.setHalignment(backBtn, HPos.CENTER);
        
        Button editBtn = new Button("Edit Detail");
        editBtn.getStyleClass().add("card-button");
        editBtn.setOnAction(event->editImage(img));
        GridPane.setValignment(editBtn, VPos.BOTTOM);
        GridPane.setHalignment(editBtn, HPos.CENTER);
            
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

        
        gridPane.add(dateTextLabel, 0, 0);
        gridPane.add(dateLabel, 1, 0);
        gridPane.add(locationTextLabel, 0, 1);
        gridPane.add(locationLabel, 1, 1);
        gridPane.add(categoryTextLabel, 0, 2);
        gridPane.add(categoryLabel, 1, 2);
        gridPane.add(OtherDetailsTextLabel, 0, 3);
        gridPane.add(otherDetailsTextFlow, 1, 3);
        gridPane.add(techDetailsTextLabel, 0, 4);
        gridPane.add(techDetailsTextFlow, 1, 4);
        gridPane.add(backBtn, 0, 6);
        gridPane.add(editBtn, 1, 6);
        
        
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
    
    public void editImage(Images img){
    }
    
    public void handleBackBtnAction(ActionEvent event){
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
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
    
    public void handleEditedBackBtnAction(Images img,ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Select_image.fxml"));
            Parent root =loader.load();
            
            // Retrieve the controller associated with the FXML file and set the u
            Select_imageController controller = loader.getController();
            controller.setUser(u);
            
            if(location.equals(img.getLocation()) && category.equals(img.getCategory())){
                controller.loadImageDynamically(); 
            }else{
                controller.loadEditedImageDynamically(i);
            }
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Styles/region_style.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/Styles/button_Style.css").toExternalForm());
            
            
            Stage stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            stage.show();
        }catch(IOException e){
            System.out.println("Error:"+e.getMessage());
        }
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
    }    
    
}
