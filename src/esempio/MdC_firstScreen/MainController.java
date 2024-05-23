//Manager di Camere - authors Ponzini Andrea, Rubinacci Antonio
//Ultima modifica 09/05/2024 - Versione 09.05 RELASE

package esempio.MdC_firstScreen;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


public class MainController {

    //Tutte le schermate che compongono il programma
    @FXML  private AnchorPane defaultLogin;
    @FXML  private AnchorPane firstStart;
    @FXML  private AnchorPane activateScreen;
    @FXML  private AnchorPane hotelInfoScreen;
    @FXML  private AnchorPane hotelRoomScreen;
    @FXML  private AnchorPane hotelAccountsScreen;
    @FXML  private AnchorPane endRegistrationScreen;
    @FXML  private AnchorPane loginScreen;
    @FXML  private AnchorPane EULAPane;
    
    @FXML  private Button continua1;
    @FXML  private Button loginFIRSTBUTTON;
    @FXML  private Button avviaMDCBtn;
    
    @FXML  private CheckBox acceptEULA;
    
    @FXML  private TextField serialNumber;
    
    @FXML  private TextField usernameLb;
    @FXML  private TextField passwordLb;
    
    //Registrazione utenti
    @FXML  private TableColumn usernameTb;
    @FXML  private TableColumn passwordTb;
    @FXML  private TableColumn privilegioTb;
    @FXML  private TableView tableView;
    @FXML  private TextField regUsernameLb;
    @FXML  private TextField regPasswordLb;
    @FXML  private ChoiceBox selezionePrivilegi;
    
    //Registrazione camere
    @FXML  private TableColumn numCameraTb;
    @FXML  private TableColumn pianoCameraTb;
    @FXML  private TableColumn tipoCameraTb;
    @FXML  private TableColumn numLettiCameraTb;
    @FXML  private TableView tableView1;
    @FXML  private TextField regNumCamera;
    @FXML  private TextField regPianoCamera;
    @FXML  private TextField regNumLettiCamera;
    @FXML  private ChoiceBox regTipoCamera;
    
    //@FXML  private TextField risultato;
    
    //@FXML  private Button somma;
    //@FXML  private Label autore;
    
    static boolean firstStartisVisible = false;
    
    @FXML
    private void initialize(){
        
        //Verifica la presenza di account utente nel file accounts.csv.
        if (isAccountsFileEmpty()){
            firstStart.setVisible(true);
            firstStartisVisible = true;
        }
        else {
            defaultLogin.setVisible(true);
        } 
        
        loginFIRSTBUTTON.setOnKeyPressed(event -> handleKeyPress(event));
        passwordLb.setOnKeyPressed(event -> handleKeyPress2(event));
        
        // Crea una lista degli elementi da inserire nella ChoiceBox e imposta predefinito
        ObservableList<String> options = FXCollections.observableArrayList("Standard", "Superior", "Suite");
        regTipoCamera.setItems(options);
        regTipoCamera.setValue(options.get(0));
        
    }
    
    @FXML
    private void continua1f(){
       firstStart.setVisible(false);
       activateScreen.setVisible(true); 
    }
    
    @FXML
    private void loginFirstf(){
       defaultLogin.setVisible(false);
       loginScreen.setVisible(true); 
    }
    
    @FXML
    private void info1f(){
       infoAlertBox("Questa è la schermata di avvio default del software MdC. Se non ti risulta essere il primo avvio, controlla l'integrità dei file di database. Se stai eseguendo una copia del programma, controlla di aver correttamente copiato i file di database.");
    }
    
    //Verifica della chiave. Qui impostare chiave definitiva di attivazione.
    @FXML
    private void continua2f(){
        
       //System.out.println(serialNumber.getText());
       if (Integer.parseInt(serialNumber.getText()) == 1234){
           
            if (acceptEULA.isSelected()){
                 activateScreen.setVisible(false);
                hotelInfoScreen.setVisible(true);  }
           
            else{
                errorAlertBox("Per procedere, devi leggere e accettare l'End User License Agreement.");
           }
       }
       else {
            errorAlertBox("La chiave inserita non è corretta. Controlla di averla copiata correttamente. Ricorda che la pirateria è un reato.");
       }
            
    }
    
    @FXML
    private void info2f(){
       infoAlertBox("In questa schermata va inserita la chiave che attesta l'acquisto del programma dalla software house produttrice di MdC. Se non hai una chiave, puoi procurartela sul sito ufficiale.");
    }
    
     @FXML
    private void viewEULA(){
        if (EULAPane.isVisible()){
            activateScreen.setVisible(true);
            EULAPane.setVisible(false);}
        else{
            activateScreen.setVisible(false);
            EULAPane.setVisible(true);}
        
    }
    
    //Implementare aggiunta delle informazioni dell'albergo al file testuale.
    @FXML
    private void continua3f(){
       hotelInfoScreen.setVisible(false);
       hotelRoomScreen.setVisible(true); 
    }
    
    @FXML
    private void info3f(){
       infoAlertBox("In questa schermata vanno inserite le informazioni del tuo albergo. Queste informazioni verranno utilizate per vari scopi dal software, come ad esempio le fatture. Nessun dato viene collezionato al di fuori del programma.");
    }
    
    //Passaggio a schermata aggiunta utenti nuovi.
    @FXML
    private void continua4f(){
       hotelRoomScreen.setVisible(false);
       hotelAccountsScreen.setVisible(true);
       selezionePrivilegi.getItems().addAll("Amministratore", "Receptionist", "Addetto pulizie");  
    }
    
    //Gestione del click sul bottone di salvataggio nuovo utente
    @FXML
    private void addUser() throws IOException, URISyntaxException{
        
        int privilegeDef = 0;
        if (selezionePrivilegi.getValue() == "Amministratore")privilegeDef = 1;
        else if (selezionePrivilegi.getValue() == "Receptionist")privilegeDef = 2;
        else if (selezionePrivilegi.getValue() == "Addetto pulizie")privilegeDef = 3;
        else errorAlertBox("Devi selezionare il livello di privilegi dell'utente.");
        
        
        if (regUsernameLb.getLength()!=0 && regPasswordLb.getLength()!=0 && privilegeDef!=0)
            addCredentialsToFile(regUsernameLb.getText(), regPasswordLb.getText(), privilegeDef);
        else errorAlertBox("I campi Username, Password e Privilegi non possono essere vuoti.");
        
    usernameTb.setCellValueFactory(new PropertyValueFactory<>("username"));
    passwordTb.setCellValueFactory(new PropertyValueFactory<>("password"));
    privilegioTb.setCellValueFactory(new PropertyValueFactory<>("privilegio"));
      
    }
    
     
    @FXML
    private void info4f(){
       infoAlertBox("In questa schermata vanno inserite le stanze del tuo albergo. Queste informazioni verranno utilizate per vari scopi dal software, come ad esempio il sistema di prenotazione, il checkin e le informazioni di pulizia. Nessun dato viene collezionato al di fuori del programma.");
    }

    //Implementare aggiunta degli account dell'albergo al file testuale. Verifica esistenza di almeno 1 account root.
    @FXML
    private void continua5f(){
       hotelAccountsScreen.setVisible(false);
       endRegistrationScreen.setVisible(true); 
    }
    @FXML
    private void info5f(){
       infoAlertBox("In questa schermata vanno inseriti gli account di tutti coloro che hanno accesso al software (MdC prevede l'accesso con tre privilegi: Amministratore, Receptionist e Pulitore. Consulta la documentazione per conoscere i privilegi. Queste informazioni verranno utilizate per vari scopi dal software, come l'accesso e la gestione dei privilegi. Nessun dato viene collezionato al di fuori del programma.");
    }    
    
    
//Implementare aggiunta degli account dell'albergo al file testuale. Verifica esistenza di almeno 1 account root.
    @FXML
    private void continua6f(){
       loginScreen.setVisible(true);
       endRegistrationScreen.setVisible(false); 
    }
    
    
    @FXML
    private void loginHelpf(){
        infoAlertBox("Password e/o username dimenticati? Il programma non dispone di un sistema di recupero password, in quanto ci è impossibile verificare che sia effettivamente TU a star cercando di eseguire l'accesso. Contatta la software house di MdC.");
    }
   
    //Gestione tasto invio
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Il tasto "Invio" è stato premuto
            System.out.println("debug - Premuto il tasto Invio!");

            // Chiamare la funzione che desideri eseguire quando viene premuto Invio
            loginFirstf();
        }
    }
    private void handleKeyPress2(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Il tasto "Invio" è stato premuto
            System.out.println("debug - Premuto il tasto Invio!");

            // Chiamare la funzione che desideri eseguire quando viene premuto Invio
              avviaMDC();
        }
    }

    
    //Funzione che avvia il programma principale
    @FXML
    private void avviaMDC() {
    
        String username = usernameLb.getText();
        String password = passwordLb.getText();
        
        String tipoPrivilegio = checkCredentialsInFile(username, password);
        
        if (tipoPrivilegio != "false") {
            System.out.println("debug - Credenziali valide.");
            // Avvia finestra principale MdC
            Stage stage = (Stage) avviaMDCBtn.getScene().getWindow();
            stage.close();
            int privilegio = Integer.parseInt(tipoPrivilegio);
            esempio.MainApp.avviaMDC(username, privilegio);
        } else {
            
            passwordLb.setText("");
            System.out.println("debug - Credenziali non valide.");
            errorAlertBox("Username e/o password errati!");
            
        }
    
        

    }
    
    
    //Verifica delle credenziali se presenti nel file
    private static String checkCredentialsInFile(String username, String password) {
        // Ottieni il percorso assoluto del file MainController.java
       File mainControllerFile = new File(MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
       String basePath = mainControllerFile.getParent();
       // Costruisci il percorso completo del file "data/accounts.csv"
       String filePath = Paths.get(basePath).toString();
       int index = filePath.indexOf("dist");   
           // Se "dist" è presente nella stringa, estrai la parte prima di "dist"
       String percorsoFile = null;
           if (index != -1) {
               String result = filePath.substring(0, index);
               percorsoFile = result + "src/esempio/MdC_firstScreen/resources/db/accounts.csv";
               System.out.println(percorsoFile);
           } 

       try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {

           String line;

           // Iterazione linee del file
           while ((line = reader.readLine()) != null) {
               // Divisione riga in campi usando la virgola come delimitatore
               String[] fields = line.split(",");

               System.out.println("debug - ut nel file: " + fields[0]);
               System.out.println("debug - password nel file: " + fields[1]);

               String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
               System.out.println("debug - Password hash: " + hashedPassword);

               // Verifica se username e password corrispondono
               if (fields.length == 3 && fields[0].equals(username) && BCrypt.checkpw(password, fields[1])) {
                   // Credenziali corrispondenti trovate
                   return fields[2];
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
           // Gestione dell'eccezione (ad esempio, registrazione o lancio di un'eccezione personalizzata)
       }
       // Nessuna corrispondenza trovata
       return "false";
   }


    // Verifica se il file accounts.csv è vuoto. Ritorna true se è vuoto.
    private static boolean isAccountsFileEmpty() {
        String nomeFile = "resources/db/accounts.csv";
        try (InputStream inputStream = MainController.class.getResourceAsStream(nomeFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            if (reader.readLine() == null){
            return true;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // In caso di eccezione o errore, consideriamo il file come non vuoto
        System.out.println("file non vuoto");
        return false;
}
    

    // Aggiunta credenziali al file e aggiornamento della tabella
    private void addCredentialsToFile(String username, String password, int val) throws IOException {
        // Ottieni il percorso assoluto del file MainController.java
        File mainControllerFile = new File(MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String basePath = mainControllerFile.getParent();
        // Costruisci il percorso completo del file "data/accounts.csv"
        String filePath = Paths.get(basePath).toString();
        int index = filePath.indexOf("dist");   
            // Se "dist" è presente nella stringa, estrai la parte prima di "dist"
        String percorsoFile = null;
            if (index != -1) {
                String result = filePath.substring(0, index);
                percorsoFile = result + "src/esempio/MdC_firstScreen/resources/db/accounts.csv";
                System.out.println(percorsoFile);
            } 

        // Controlla se l'utente è già presente nel file
        if (isUserAlreadyExists(username)) {
            // L'utente è già presente, gestisci di conseguenza (ad esempio, mostra un messaggio all'utente)
            errorAlertBox("L'utente è già stato registrato.");
            regUsernameLb.setText("");
            regPasswordLb.setText("");
            return;
        }

         try (PrintWriter writer = new PrintWriter(new FileWriter(percorsoFile, true))) {
                // Aggiunge una nuova riga con username e password separate da virgola
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                writer.println(username + "," + hashedPassword + "," + val);
                regUsernameLb.setText("");
                regPasswordLb.setText("");
                System.out.println("Utente aggiunto con successo al file CSV.");

    }

        User user = new User(username, password, String.valueOf(val));
        tableView.getItems().add(user);

    }


    // Controllo per verificare se un utente è già presente nel file. Ritorna true se già presente.
    private boolean isUserAlreadyExists(String username) throws IOException {
        // Ottieni il percorso assoluto del file MainController.java
        File mainControllerFile = new File(MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String basePath = mainControllerFile.getParent();
        // Costruisci il percorso completo del file "data/accounts.csv"
        String filePath = Paths.get(basePath).toString();
        int index = filePath.indexOf("dist");   
            // Se "dist" è presente nella stringa, estrai la parte prima di "dist"
        String percorsoFile = null;
            if (index != -1) {
                String result = filePath.substring(0, index);
                percorsoFile = result + "src/esempio/MdC_firstScreen/resources/db/accounts.csv";
                System.out.println(percorsoFile);
            } 

        try (BufferedReader reader = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0 && fields[0].equals(username)) {
                    // L'utente è già presente
                    return true;
                }
            }
        }
        // L'utente non è presente nel file
        return false;
    }

    
    // Aggiunta camere al file e aggiornamento della tabella
    @FXML
    private void addCameraToFile() throws IOException {
        // Ottieni il percorso assoluto del file Main.java
        File mainFile = new File(MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String basePath = mainFile.getParent();
        // Costruisci il percorso completo del file "data/accounts.csv"
        String filePath = Paths.get(basePath).toString();
        int index = filePath.indexOf("dist");
        // Se "dist" è presente nella stringa, estrai la parte prima di "dist"
        String percorsoFile = null;
        if (index != -1) {
            String result = filePath.substring(0, index);
            percorsoFile = result + "src/esempio/MdC_firstScreen/resources/db/camere.csv";
            System.out.println(percorsoFile);
        }

        // Aggiungi i dati della camera all'array
        String numCamera = regNumCamera.getText();
        String pianoCamera = regPianoCamera.getText();
        String tipoCamera = (String) regTipoCamera.getValue(); 
        String numLettiCamera = regNumLettiCamera.getText();

        // Verifica se esiste già una camera con lo stesso numero e piano nel file CSV
        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] rowData = line.split(",");
            if (rowData.length >= 2 && rowData[0].equals(numCamera) && rowData[1].equals(pianoCamera)) {
                // Mostra un messaggio di avviso
                errorAlertBox("Una camera con lo stesso numero e piano già esiste.");
                return;
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Scrivi i dati della camera nel file CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(percorsoFile, true))) {
            writer.println(numCamera + "," + pianoCamera + "," + tipoCamera + "," + numLettiCamera + ",0");
        }

        // Aggiungi i dati della camera alla tabella
        TableColumn<ObservableList<String>, String> numCameraColumn = (TableColumn<ObservableList<String>, String>) tableView1.getColumns().get(0);
        TableColumn<ObservableList<String>, String> pianoCameraColumn = (TableColumn<ObservableList<String>, String>) tableView1.getColumns().get(1);
        TableColumn<ObservableList<String>, String> tipoCameraColumn = (TableColumn<ObservableList<String>, String>) tableView1.getColumns().get(2);
        TableColumn<ObservableList<String>, String> numLettiCameraColumn = (TableColumn<ObservableList<String>, String>) tableView1.getColumns().get(3);

        numCameraColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        pianoCameraColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        tipoCameraColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        numLettiCameraColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));

        ObservableList<String> cameraData = FXCollections.observableArrayList(numCamera, pianoCamera, tipoCamera, numLettiCamera);
        tableView1.getItems().add(cameraData);

        // Pulisci i campi di input
        regNumCamera.clear();
        regPianoCamera.clear();
        regNumLettiCamera.clear();

        // Aggiorna la tabella
        tableView1.refresh();

        System.out.println("Camera aggiunta con successo al file CSV.");
}

    
    //Funzione da mettere in onKeyTiped delle TextField per consentire inserimento di soli numeri
    @FXML
    public void onlyNumberTextbox(KeyEvent event) {
        String character = event.getCharacter();
        // Verifica se il carattere digitato è un numero
        if (!character.matches("[0-9]")) {
            // Se non è un numero, consuma l'evento (ignora il carattere)
            event.consume();
        }
    }

    
    //Creazione dialogbox di info
    private void infoAlertBox(String informazione){

        try {
                // Creazione di un'istanza di Alert
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Aiuto");

                alert.setHeaderText("");
                alert.setContentText(informazione);

                // Visualizzazione della finestra di dialogo e attesa dell'utente
                alert.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    //Creazione dialogbox di errore
    private void errorAlertBox(String informazione){
    
    try {
            // Creazione di un'istanza di Alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            
            alert.setHeaderText("");
            alert.setContentText(informazione);

            // Visualizzazione della finestra di dialogo e attesa dell'utente
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}
