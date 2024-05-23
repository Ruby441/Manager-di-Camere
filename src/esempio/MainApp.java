//Manager di Camere - authors Ponzini Andrea, Rubinacci Antonio
//Ultima modifica 09/05/2024 - Versione 09.05 RELASE

package esempio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) {
        // Imposta il primaryStage solo se non è stato già impostato
        if (MainApp.primaryStage == null) {
            MainApp.primaryStage = primaryStage;
            MainApp.primaryStage.setTitle("MdC - Manager di Camere");
        }

        // Non chiamare inizializza() qui, poiché sarà chiamato manualmente
    }

    private static String username2;
    private static int privilegio2;
    public static void avviaMDC(String username, int privilegio) {
        username2 = username;
        privilegio2 = privilegio;
        // Inizializza manualmente l'interfaccia
        inizializza();
        //System.out.println(username);
        
        
    }
    
    //Funzioni d'appoggio per ottenere informazioni nel Controller
    public static String getUsername(){
        return username2;
    }
    public static int getPrivilegio(){
        return privilegio2;
    }
    
    
    public static void inizializza() {
        try {
            // Assicurati che primaryStage sia stato inizializzato
            if (primaryStage == null) {
                primaryStage = new Stage();
                primaryStage.setTitle("Manager di Camere");
            }

            // Carica l'FXML per l'interfaccia principale
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("GestionaleMDC.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();

            // Crea la scena
            Scene scene = new Scene(rootLayout);

            // Imposta la scena sulla finestra principale
            primaryStage.setScene(scene);
            
            
            Class<?> clazz = MainApp.class;
            Image favicon = new Image(clazz.getResourceAsStream("MdC_firstScreen/resources/MDC_nobg_onlyImg.png"));

            // Set the icon for the stage
            primaryStage.getIcons().add(favicon);
            
            // Mostra la finestra
            primaryStage.setMaximized(true);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
