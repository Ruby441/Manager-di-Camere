/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esempio.MdC_firstScreen;

import esempio.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 *
 * @author giuseppe.depietro
 */
public class MainApp extends Application{
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Manager di Camere");
        
        inizializza();
        
    }
    
     public static void main(String[] args) {
        launch(args);
    }
     

     public static void avviaLogIn() {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            try {
                new MainApp().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
     
     
    public void setFullScreen(){
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        
    }
     
    public void inizializza() {
        
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("schermataIniziale.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            //primaryStage.setFullScreen(true);

            
            Image favicon = new Image(getClass().getResourceAsStream("resources/MDC_nobg_onlyImg.png")) {};

            // Set the icon for the stage
            primaryStage.getIcons().add(favicon);

            
            primaryStage.setResizable(false);
            primaryStage.setMaximized(false);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}