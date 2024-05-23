//Manager di Camere - authors Ponzini Andrea, Rubinacci Antonio
//Ultima modifica 19/05/2024 - Versione 19.05 RELASE

package esempio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;


public class MainController {
    @FXML  private MenuButton usernameLabel;
    
    //Tutti i pannelli che compongono il programma
    @FXML  private AnchorPane firstPane;
    @FXML  private AnchorPane homePane;
    @FXML  private AnchorPane prenotaPane;
    @FXML  private AnchorPane listaCamerePane;
    @FXML  private AnchorPane listaPrenotazioniPane;
    @FXML  private AnchorPane aiutoPane;
    @FXML  private AnchorPane listaCheckInArrivoOggi;
    @FXML  private AnchorPane effettuaCheckIn;
    @FXML  private AnchorPane timelinePane;
    @FXML  private AnchorPane cleaningPane;
    
    //Bottoni del menu in alto al programma
    @FXML  private MenuButton receptionMenuButton;
    @FXML  private Button realtimeMenuButton;
    @FXML  private MenuButton camereMenuButton;
    @FXML  private MenuButton pagamentiMenuButton;
    
    @FXML private ImageView tickImageBtn;            
    @FXML private ImageView binImageBtn;            
    
    @FXML  private Text benvenutoName;
    
    //Variabili passate dal firstScreen
    private String username;
    public int privilegiUtente;
      
    
    //Schermata di prenotazione stanza
    @FXML  private DatePicker dataInizioPrenotazione;
    @FXML  private DatePicker dataFinePrenotazione;
    @FXML  private TextField numAdultiPrenotazione;
    @FXML  private TextField numBambiniPrenotazione;
    @FXML  private ChoiceBox tipoStanzaPrenotazione;
    @FXML  private ChoiceBox selezioneStanzaPrenotazione;
    @FXML  private ChoiceBox selezioneTariffaPrenotazione;
    @FXML  private TextField totalePrenotazione;
    @FXML  private Label clockDataPrenotazione;
    @FXML  private Label clockOraPrenotazione;
    
    //Schermata di elenco camere e modifica camere
    @FXML   private TableView<String[]> elencoCamereTab;
    @FXML   private TableColumn<String[], String> elencoTabNumCamera;
    @FXML   private TableColumn<String[], String> elencoTabPianoCamera;
    @FXML   private TableColumn<String[], String> elencoTabTipoCamera;
    @FXML   private TableColumn<String[], String> elencoTabNumLetti;
    @FXML   private Label elencoTotaleCamereLbl;
    @FXML   private AnchorPane elencoCamere_filtriTab;
    @FXML   private AnchorPane elencoCamere_aggiungiTab;
    @FXML   private AnchorPane elencoCamere_errorNoAdmin;
    @FXML   private ChoiceBox regTipoCamera;
    @FXML   private TextField regNumCamera;
    @FXML   private TextField regPianoCamera;
    @FXML   private TextField regNumLettiCamera;
    @FXML   private Button deleteRowBtn;
    @FXML   private Label deleteRowStr;
    @FXML   private TextField filterCameraPiano;
    @FXML   private TextField filterCameraNumero;
    @FXML   private TextField filterCameraNumLetti;
    @FXML   private ChoiceBox filterCameraTipo;
    
    //Schermata lista prenotazioni
    @FXML   private TableView<String[]> elencoPrenotazioniTab;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniNumCamera;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniDataInizio;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniDataFine;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniIDCliente;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniAdulti;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniBambini;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniTariffa;
    @FXML   private TableColumn<String[], String> elencoPrenotazioniTotale;
    @FXML   private Label elencoTotalePrenotazioniLbl;
    
    //Schermata checkIn
    @FXML   private TableView<String[]> elencoCheckInTab;
    @FXML   private TableColumn<String[], String> elencoCheckInNumCamera;
    @FXML   private TableColumn<String[], String> elencoCheckInDataInizio;
    @FXML   private TableColumn<String[], String> elencoCheckInDataFine;
    @FXML   private TableColumn<String[], String> elencoCheckInIDCliente;
    @FXML   private TableColumn<String[], String> elencoCheckInAdulti;
    @FXML   private TableColumn<String[], String> elencoCheckInBambini;
    @FXML   private TableColumn<String[], String> elencoCheckInTariffa;
    @FXML   private TableColumn<String[], String> elencoCheckInTotale;
    @FXML   private Label elencoCheckInLb1;
    @FXML   private Label elencoCheckInLbTitle;
    @FXML   private AnchorPane buttonRectangle;

    //Schermata checkIn bis
    @FXML   private Label ckInIDClienteLb;
    @FXML   private Label ckInDataInizioLb;
    @FXML   private Label ckInDataFineLb;
    @FXML   private Label ckInNumPers;
    @FXML   private ImageView tickImageBtn2;
    @FXML   private TextField ckInNome;
    @FXML   private TextField ckInCognome;
    
    //Schermata realtime
    @FXML   private Label realtimeNumCamereOccupate;
    @FXML   private Label realtimeNumTotalePersone;
    @FXML   private Label realtimeRatioOccupazione;
    @FXML   private Label realtimeNumCamereOccupate2;
    @FXML   private Label realtimeNumCamereLibere;
    @FXML   private Label realtimeNumPrenotazioniPerOggi;
    @FXML   private Label realtimeNumClientiInArrivo;
    @FXML   private Label realtimeNumCheckOutOggi;
    @FXML   private Label stanzePuliteLbl2;
    @FXML   private Label stanzeDaPulireLbl2;
    
    //Schermata TIMELINE
     @FXML
    private TableView<String[]> tableViewRealtime;
    @FXML
    private TableColumn<String[], String> dayColumn;
    @FXML
    private TableColumn<String[], String> numCameraColumn;
    @FXML
    private TableColumn<String[], String> numAdultiColumn;
    @FXML
    private TableColumn<String[], String> numBambiniColumn;
    @FXML
    private Button previousMonthButton;
    @FXML
    private Button nextMonthButton;
    @FXML
    private Label monthLabel;

    private LocalDate currentDate = LocalDate.now();
    
    //Schermata realtime PULIZIA
    @FXML   private Label stanzePuliteLbl;
    @FXML   private Label stanzeDaPulireLbl;
    @FXML   private Label stanzeNonDisturbareLbl;
    @FXML   private ChoiceBox selezionaStanzaPulizia;
    @FXML  private Button puliziaPulitaButton;
    @FXML  private Button puliziaDaPulireButton;
    @FXML  private Button puliziaNonDistButton;
    
    private ScheduledExecutorService executorService;
    
    @FXML
    private void initialize(){
        
        username = MainApp.getUsername();
        privilegiUtente = MainApp.getPrivilegio();
        benvenutoName.setText("Benvenuto in MdC, " + username);
        System.out.println("debug - Accesso con utente" + username);
        String user2 = username;
        if (user2 != null)
            usernameLabel.setText(user2);
        
        if (privilegiUtente == 3){
            receptionMenuButton.setVisible(false);
            realtimeMenuButton.setVisible(false);
            camereMenuButton.setVisible(false);
            pagamentiMenuButton.setVisible(false);
        }
        
        
        // Crea una lista degli elementi da inserire nella ChoiceBox e imposta predefinito
        ObservableList<String> options = FXCollections.observableArrayList("Standard", "Superior", "Suite");
        ObservableList<String> optionsFilter = FXCollections.observableArrayList("-Seleziona-", "Standard", "Superior", "Suite");
        regTipoCamera.setItems(options);
        regTipoCamera.setValue(options.get(0));
        tipoStanzaPrenotazione.setItems(options);
        tipoStanzaPrenotazione.setValue(options.get(0));
        filterCameraTipo.setItems(optionsFilter);
        filterCameraTipo.setValue(optionsFilter.get(0));
        
        //Listener righe selezionate schermata Aggiungi/Elimina camere
        deleteRowBtn.setVisible(false);
        elencoCamereTab.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                deleteRowStr.setVisible(false);
                deleteRowBtn.setVisible(true);
            } else {
                deleteRowStr.setVisible(true);
                deleteRowBtn.setVisible(false);
            }
        });
    
        
        
        firstPane.setVisible(true);
        
      updateClock();
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Attendi un secondo
                    Platform.runLater(this::updateClock); // Modifica per eseguire updateClock sul thread JavaFX
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();       
        
        dayColumn.setCellValueFactory(cellData -> {
            String date = cellData.getValue()[1]; // Supponendo che il giorno sia nella seconda colonna
            return new SimpleStringProperty(date.substring(date.lastIndexOf('-') + 1));
        });
        numCameraColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0])); // Supponendo che il numero della camera sia nella terza colonna
        numAdultiColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4])); // Supponendo che il numero degli adulti sia nella quarta colonna
        numBambiniColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5])); // Supponendo che il numero dei bambini sia nella quinta colonna
        
    }
    
    private void nascondiTuttiPannelli(){
        homePane.setVisible(false);
        prenotaPane.setVisible(false);
        firstPane.setVisible(false);
        listaCamerePane.setVisible(false);
        listaPrenotazioniPane.setVisible(false);
        aiutoPane.setVisible(false);
        listaCheckInArrivoOggi.setVisible(false);
        effettuaCheckIn.setVisible(false);
        timelinePane.setVisible(false);
        cleaningPane.setVisible(false);
    }
    
    @FXML
    private void tickDragOver() {  
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
        percorsoFile = result + "src/esempio/resources/icon/tick2.png";
    } 
        
        try {
            // Apre un FileInputStream per leggere l'immagine dal percorso
            FileInputStream inputStream = new FileInputStream(percorsoFile);

            // Crea un oggetto Image utilizzando l'InputStream
            Image image = new Image(inputStream);
            
            // Imposta l'immagine nella ImageView
            tickImageBtn.setImage(image);
            tickImageBtn2.setImage(image);

            // Chiude l'InputStream
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void tickDragOff() {  
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
        percorsoFile = result + "src/esempio/resources/icon/tick.png";
    } 
        
        try {
            // Apre un FileInputStream per leggere l'immagine dal percorso
            FileInputStream inputStream = new FileInputStream(percorsoFile);

            // Crea un oggetto Image utilizzando l'InputStream
            Image image = new Image(inputStream);
            
            // Imposta l'immagine nella ImageView
            tickImageBtn.setImage(image);
            tickImageBtn2.setImage(image);

            // Chiude l'InputStream
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void binDragOver() {  
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
        percorsoFile = result + "src/esempio/resources/icon/bin2.png";
    } 
        
        try {
            // Apre un FileInputStream per leggere l'immagine dal percorso
            FileInputStream inputStream = new FileInputStream(percorsoFile);

            // Crea un oggetto Image utilizzando l'InputStream
            Image image = new Image(inputStream);
            
            // Imposta l'immagine nella ImageView
            binImageBtn.setImage(image);

            // Chiude l'InputStream
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void binDragOff() {  
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
        percorsoFile = result + "src/esempio/resources/icon/bin.png";
    } 
        
        try {
            // Apre un FileInputStream per leggere l'immagine dal percorso
            FileInputStream inputStream = new FileInputStream(percorsoFile);

            // Crea un oggetto Image utilizzando l'InputStream
            Image image = new Image(inputStream);
            
            // Imposta l'immagine nella ImageView
            binImageBtn.setImage(image);

            // Chiude l'InputStream
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    @FXML
    private void esciDaUtente(){
        Stage stage = (Stage) usernameLabel.getScene().getWindow();
        stage.close();
        esempio.MdC_firstScreen.MainApp.avviaLogIn();
    }
     
    @FXML
    private void apriTodayRealTime(){
        nascondiTuttiPannelli();
        homePane.setVisible(true);
        setRealtimeScreen();
    }
    
    public int meseAttualeTimeline = 0;
    @FXML
    private void apriTimelinePane() throws IOException{
        nascondiTuttiPannelli();
        timelinePane.setVisible(true);
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();
        String monthName = currentMonth.getDisplayName(java.time.format.TextStyle.FULL, Locale.ITALIAN);
        monthLabel.setText(monthName + " " + currentYear);
        meseAttualeTimeline = currentMonth.getValue();
        caricaDati(meseAttualeTimeline);
    }
   
    
    @FXML
    private void apriPrenotaPane(){
        nascondiTuttiPannelli();
        prenotaPane.setVisible(true);
        updateClock();  
    }
    
    @FXML
    private void apriCleaningPane(){
        nascondiTuttiPannelli();
        cleaningPane.setVisible(true);
        countCamereDaPulire();
        selezionaStanzaPulizia.setValue(null);
        puliziaDaPulireButton.setDisable(true);
        puliziaPulitaButton.setDisable(true);
        puliziaNonDistButton.setDisable(true);
        
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ObservableList<String> cameraNumbers = FXCollections.observableArrayList();
        cameraNumbers.add("-Seleziona-");
        String percorsoFile = ottieniPercorsoFile("camere.csv");
        
         try (BufferedReader camereReader = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            while ((line = camereReader.readLine()) != null) {
                // Split della riga per ottenere il numero della camera
                String[] parts = line.split(",");
                String numCamera = parts[0];
                cameraNumbers.add(numCamera);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        selezionaStanzaPulizia.setItems(cameraNumbers);
    }
    
    @FXML
    private void apriAiutoPane(){
    nascondiTuttiPannelli();
    aiutoPane.setVisible(true);
    }
    
     public String formatinglese;
     
    //Funzione per gestire gli orologi presenti nel SW
     private void updateClock() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterOra = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formatterInglese = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataformat = now.format(formatter);
        String oraformat = now.format(formatterOra);
        formatinglese = now.format(formatterInglese);
        clockDataPrenotazione.setText(dataformat);
        clockOraPrenotazione.setText(oraformat);
    }
    
    @FXML
    private void apriListaCamerePane(){
        nascondiTuttiPannelli();
        listaCamerePane.setVisible(true);
        loadElencoCameraTable();
        elencoCamere_filtriTab.setVisible(true);
        elencoCamere_aggiungiTab.setVisible(false);
        elencoCamere_errorNoAdmin.setVisible(false);
        
    }
    
    @FXML
    private void apriListaPrenotazioniPane(){
        nascondiTuttiPannelli();
        listaPrenotazioniPane.setVisible(true);
        loadElencoPrenotazioniTable();
        
    }
    
    @FXML
    private void apriListaCheckinArrivo(){
        nascondiTuttiPannelli();
        elencoCheckInLbTitle.setText("Check-in in arrivo");
        buttonRectangle.setVisible(true);
        listaCheckInArrivoOggi.setVisible(true);
        loadElencoCheckInTable();  
        
    }
   
    @FXML
    private void apriListaStoricoCheckIn(){
        nascondiTuttiPannelli();
        listaCheckInArrivoOggi.setVisible(true);
        loadAllCheckInTable();
        elencoCheckInLbTitle.setText("Storico Check-in");
        buttonRectangle.setVisible(false);
    }
    
    @FXML
    private void apriAggiungiListaCamerePane(){
        nascondiTuttiPannelli();
        listaCamerePane.setVisible(true);
        loadElencoCameraTable();
        elencoCamere_filtriTab.setVisible(false);
        
        if (privilegiUtente == 1)
            elencoCamere_aggiungiTab.setVisible(true);
        else
            elencoCamere_errorNoAdmin.setVisible(true);
        
    }
    
    
    //Funzione per svuotare i campi nel Pane Prenotazione camera
    @FXML
    private void cancellaDatiPrenotazione(){
        dataInizioPrenotazione.setValue(null);
        dataFinePrenotazione.setValue(null);
        numAdultiPrenotazione.setText(null);
        numBambiniPrenotazione.setText(null);
        selezioneStanzaPrenotazione.setValue(null);
        selezioneTariffaPrenotazione.setValue(null);
        totalePrenotazione.setText(null);
    }
    
    // Aggiunta dati della prenotazione al file
    @FXML
    private void salvaDatiPrenotazione() throws IOException{      
        String percorsoFile = ottieniPercorsoFile("prenotazioni.csv");

        int numCamera = 0;
        if (selezioneStanzaPrenotazione.getValue() != null){
            numCamera = (int)selezioneStanzaPrenotazione.getValue();
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataDiOggi = LocalDate.parse(formatinglese, formatter);

        LocalDate dataInizioPrenotazioneLD = dataInizioPrenotazione.getValue();
        LocalDate dataFinePrenotazioneLD = dataFinePrenotazione.getValue();

        boolean erroredata = false;
        boolean errorepersone = false;

        if (dataInizioPrenotazioneLD == null) erroredata = true;
        if (dataFinePrenotazioneLD == null) erroredata = true;


        if (erroredata == false){
         if (dataInizioPrenotazioneLD.isBefore(dataDiOggi)) {
                erroredata = true;
            }}
        if (erroredata == false){
        if (dataFinePrenotazioneLD.isBefore(dataInizioPrenotazioneLD)) {
                erroredata = true;
            }}
        if (erroredata == false){
        if (dataInizioPrenotazioneLD.isEqual(dataFinePrenotazioneLD)) {
                erroredata = true;
            }}

        int IDCliente = 0;

        if (numAdultiPrenotazione.getText().isEmpty()) {
           errorepersone = true;}

        if (errorepersone == false){
        if (Integer.parseInt(numAdultiPrenotazione.getText()) < 1){
            errorepersone = true;
        }}

        if (numBambiniPrenotazione.getText() == null) numBambiniPrenotazione.setText("0");

        if (numCamera == 0) erroredata = true;
        if (totalePrenotazione.getText().isEmpty()) {
                totalePrenotazione.setText("0");
        }


        if (erroredata == false && errorepersone == false){

         try (PrintWriter writer = new PrintWriter(new FileWriter(percorsoFile, true))) {
                writer.println(numCamera + "," + dataInizioPrenotazione.getValue() + "," + dataFinePrenotazione.getValue() + "," + IDCliente + "," + numAdultiPrenotazione.getText() + "," + numBambiniPrenotazione.getText() + "," + selezioneTariffaPrenotazione.getValue() + "," + totalePrenotazione.getText());
                infoAlertBox("Prenotazione salvata con successo.");
                cancellaDatiPrenotazione();

                verificaDisponibilita(dataInizioPrenotazioneLD, dataFinePrenotazioneLD, 3);
         }}

        else if (erroredata == true) 
            errorAlertBox("Non puoi impostare una data di inizio precedente a quella di oggi e la data di fine soggiorno non deve essere antecente o uguale a quella di inizio.");

        else if (errorepersone == true)
             errorAlertBox("Il campo NUMERO ADULTI non può essere vuoto o inferiore a 1.");
    }
   
    
    //Funzione che, quando vengono cambiate le DataPicker e le TextField della tabella prenotazione, verifica
    //se sono piene e quali camere sono libere per mostrarle nella ChoiceBox.
    @FXML
    private void datiPrenotazioneAggiornati() {
        String percorsoFile = ottieniPercorsoFile("camere.csv");

        // Verifica se è stato selezionato un tipo di stanza
        String tipoStanzaSelezionato = (String) tipoStanzaPrenotazione.getValue();
        if (tipoStanzaSelezionato == null) {
            return; 
        }

        // Verifica se sono state inserite tutte le informazioni necessarie
        if (dataInizioPrenotazione.getValue() != null && dataFinePrenotazione.getValue() != null &&
                !numAdultiPrenotazione.getText().isEmpty() && !numBambiniPrenotazione.getText().isEmpty()) {

            LocalDate dataInizioPrenotazioneLD = dataInizioPrenotazione.getValue();
            LocalDate dataFinePrenotazioneLD = dataFinePrenotazione.getValue();
            int numTotalePrenotanti = Integer.parseInt(numAdultiPrenotazione.getText()) + Integer.parseInt(numBambiniPrenotazione.getText());

            // Verifica correttezza date inserite
            if (dataFinePrenotazioneLD.isBefore(dataInizioPrenotazioneLD) || dataFinePrenotazioneLD.isEqual(dataInizioPrenotazioneLD)) {
                errorAlertBox("La data di fine prenotazione deve essere successiva alla data di inizio.");
                return;
            } else if (dataInizioPrenotazioneLD.isBefore(LocalDate.now())) {
                errorAlertBox("La data di inizio prenotazione non può essere antecedente a oggi.");
                return;
            }

            // Popola la tendina delle camere in base al tipo selezionato
            try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
                String line;
                ObservableList<Integer> camereDisponibili = FXCollections.observableArrayList();

                while ((line = br.readLine()) != null) {
                    String[] rowData = line.split(",");
                    if (rowData.length >= 3 && rowData[2].equals(tipoStanzaSelezionato)) {
                        // La terza colonna corrisponde al tipo di stanza
                        // Aggiungi il numero della camera disponibile alla lista solo se è presente nella lista ottenuta dalla funzione verificaDisponibilita
                        int numCamera = Integer.parseInt(rowData[0]);
                        if (verificaDisponibilita(dataInizioPrenotazioneLD, dataFinePrenotazioneLD, numTotalePrenotanti).contains(numCamera)) {
                            camereDisponibili.add(numCamera); // Assumendo che il numero della camera sia nella prima colonna
                        }
                    }
                }
                selezioneStanzaPrenotazione.setItems(camereDisponibili);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            return; 
        }
    }

    //Funzione che date le date di Inizio, Fine e il numPersone verifica la disponibilità delle camere
    //basandosi sui file prenotazioni.csv e camere.csv. Verifica se è occupata e se ha abbastanza letti.
    public List<Integer> verificaDisponibilita(LocalDate dataInizio, LocalDate dataFine, int numPersone) {
        List<Integer> camereDisponibili = new ArrayList<>();
       
        String percorsoFile = ottieniPercorsoFile("camere.csv");
        String percorsoFile2 = ottieniPercorsoFile("prenotazioni.csv");

        String camereFile = percorsoFile;
        String prenotazioniFile = percorsoFile2;

        try (BufferedReader camereReader = new BufferedReader(new FileReader(camereFile))) {
            String line;

            while ((line = camereReader.readLine()) != null) {
                String[] cameraData = line.split(",");
                int numCamera = Integer.parseInt(cameraData[0]);
                int numLetti = Integer.parseInt(cameraData[3]);

                // Riapertura del file prenotazioni.csv per ogni camera
                try (BufferedReader prenotazioniReader = new BufferedReader(new FileReader(prenotazioniFile))) {
                    boolean disponibile = true;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    if (numLetti >= numPersone) {
                        while ((line = prenotazioniReader.readLine()) != null) {
                            String[] prenotazioneData = line.split(",");
                            int numCameraPrenotata = Integer.parseInt(prenotazioneData[0]);
                            LocalDate prenotazioneDataInizio = LocalDate.parse(prenotazioneData[1], formatter);
                            LocalDate prenotazioneDataFine = LocalDate.parse(prenotazioneData[2], formatter);

                            if (numCamera == numCameraPrenotata && (
                                    (dataInizio.isBefore(prenotazioneDataFine) && dataFine.isAfter(prenotazioneDataInizio)) ||
                                            (dataInizio.isEqual(prenotazioneDataFine) || dataFine.isEqual(prenotazioneDataInizio)))) {
                                disponibile = false;
                                break;
                            }
                        }

                        if (disponibile) {
                            camereDisponibili.add(numCamera);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return camereDisponibili;
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
    
    //Funzione per mostrare la lista di tutte le camere nella tabella nella sezione ELENCO CAMERE.
    private void loadElencoCameraTable() {
        ObservableList<String[]> data = FXCollections.observableArrayList();

        String percorsoFile = ottieniPercorsoFile("camere.csv");
        
        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            int count = 0; // Contatore per il numero totale di righe nel file
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts);
                count++;
            }
             elencoTotaleCamereLbl.setText("Sono presenti in totale " + count + " camere.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        elencoCamereTab.setItems(data);

        elencoTabNumCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        elencoTabPianoCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        elencoTabTipoCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
        elencoTabNumLetti.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
    }

    //Funzione per filtrare
    @FXML
    private void loadElencoCameraFilter() {
    ObservableList<String[]> data = FXCollections.observableArrayList();

    String percorsoFile = ottieniPercorsoFile("camere.csv");

    try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
        String line;
        int count = 0; // Contatore per il numero totale di righe nel file
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");

            // Filtra i dati in base ai valori dei campi di testo
            boolean matches = true;
            if (!filterCameraPiano.getText().isEmpty() && !parts[1].equalsIgnoreCase(filterCameraPiano.getText())) {
                matches = false;
            }
            if (!filterCameraNumero.getText().isEmpty() && !parts[0].equalsIgnoreCase(filterCameraNumero.getText())) {
                matches = false;
            }
            if (!(filterCameraTipo.getValue() == "-Seleziona-") && !parts[2].equalsIgnoreCase((String) filterCameraTipo.getValue())) {
                matches = false;
           }
            if (!filterCameraNumLetti.getText().isEmpty() && !parts[3].equalsIgnoreCase(filterCameraNumLetti.getText())) {
                matches = false;
            }

            if (matches) {
                data.add(parts);
                count++;
            }
        }
        elencoTotaleCamereLbl.setText("Sono presenti in totale " + count + " camere.");
    } catch (IOException e) {
        e.printStackTrace();
    }

    elencoCamereTab.setItems(data);

    elencoTabNumCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
    elencoTabPianoCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
    elencoTabTipoCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
    elencoTabNumLetti.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
}

    //Funzione per svuotare i filtri
    @FXML
    private void svuotaFiltri(){
        filterCameraNumLetti.setText("");
        filterCameraNumero.setText("");
        filterCameraPiano.setText("");
        filterCameraTipo.setValue("-Seleziona-");
        loadElencoCameraFilter();
        }
    
    @FXML
    private void loadElencoPrenotazioniTable() {
        ObservableList<String[]> data = FXCollections.observableArrayList();

        String percorsoFile = ottieniPercorsoFile("prenotazioni.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            int count = 0;
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts);
                count++;
            }

            if (count == 0) {
                errorAlertBox("Non sono presenti prenotazioni.");
                return;
            }

            elencoTotalePrenotazioniLbl.setText("Sono presenti in totale " + count + " prenotazioni.");

            // Imposta il colore di sfondo delle righe in base alla data di inizio della prenotazione
            elencoPrenotazioniTab.setRowFactory(row -> new TableRow<String[]>() {
                @Override
                protected void updateItem(String[] item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setStyle("");
                    } else {
                        LocalDate dataInizio = LocalDate.parse(item[1], formatter);
                        if (dataInizio.isBefore(today)) {
                            // Data di inizio precedente alla data attuale (sfondo blu)
                            setStyle("-fx-background-color: #add8e6;"); // Colore blu chiaro
                        } else if (dataInizio.isEqual(today)) {
                            // Data di inizio uguale alla data attuale (sfondo viola)
                            setStyle("-fx-background-color: #9370db;"); // Colore viola
                        } else {
                            // Data di inizio successiva alla data attuale (sfondo verde)
                            setStyle("-fx-background-color: #98fb98;"); // Colore verde chiaro
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        elencoPrenotazioniTab.setItems(data);

        elencoPrenotazioniNumCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        elencoPrenotazioniDataInizio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        elencoPrenotazioniDataFine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
        elencoPrenotazioniIDCliente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
        elencoPrenotazioniAdulti.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[4]));
        elencoPrenotazioniBambini.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[5]));
        elencoPrenotazioniTariffa.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[6]));
        elencoPrenotazioniTotale.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[7]));
    }


    
    @FXML
    private void loadElencoPrenotazioniFutureTable() {
        ObservableList<String[]> data = FXCollections.observableArrayList();

        String percorsoFile = ottieniPercorsoFile("prenotazioni.csv");

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            int count = 0; 
            LocalDate today = LocalDate.now(); // Data attuale
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formato della data nel file

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDate dataInizio = LocalDate.parse(parts[1], formatter); // Data di inizio della prenotazione
                if (dataInizio.isEqual(today) || dataInizio.isAfter(today)) { // Controlla se la data di inizio è oggi o in futuro
                    data.add(parts);
                    count++;
                }

            }
             elencoTotalePrenotazioniLbl.setText("A partire dalla data odierna sono presenti " + count + " prenotazioni.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        elencoPrenotazioniTab.setItems(data);

        // Imposta i valori delle celle della tabella
        elencoPrenotazioniNumCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
        elencoPrenotazioniDataInizio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
        elencoPrenotazioniDataFine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
        elencoPrenotazioniIDCliente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
        elencoPrenotazioniAdulti.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[4]));
        elencoPrenotazioniBambini.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[5]));
        elencoPrenotazioniTariffa.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[6]));
        elencoPrenotazioniTotale.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[7]));
    }

    @FXML
    private void ordinaPerDataInizio() {
        // Ottieni la lista dei dati dalla tabella e ordina
        ObservableList<String[]> data = elencoPrenotazioniTab.getItems();
        Collections.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] row1, String[] row2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataInizio1 = LocalDate.parse(row1[1], formatter); // Data di inizio della prima riga
                LocalDate dataInizio2 = LocalDate.parse(row2[1], formatter); // Data di inizio della seconda riga
                return dataInizio1.compareTo(dataInizio2); // Confronta le date di inizio
            }
        });

        // Aggiorna la tabella con i dati ordinati
        elencoPrenotazioniTab.setItems(data);
    }

    
   //Funzione che carica nella tabella dei CHECK IN IN ARRIVO le prenotazioni con inizio oggi.
   @FXML
   private void loadElencoCheckInTable() {

       // Rinomina colonne e imposta visibilità
       elencoCheckInNumCamera.setVisible(true);
       elencoCheckInBambini.setVisible(true);
       elencoCheckInAdulti.setText("Adulti");
       elencoCheckInTariffa.setText("Tariffa");
       elencoCheckInTotale.setText("Totale");

       ObservableList<String[]> data = FXCollections.observableArrayList();
       elencoCheckInTab.getItems().clear(); // Svuota la tabella


       String prenotazioniFilePath = ottieniPercorsoFile("prenotazioni.csv");
       String checkinFilePath = ottieniPercorsoFile("checkin.csv");

       try (BufferedReader prenotazioniReader = new BufferedReader(new FileReader(prenotazioniFilePath));
            BufferedReader checkinReader = new BufferedReader(new FileReader(checkinFilePath))) {

           List<String> checkinLines = checkinReader.lines().collect(Collectors.toList());

           String line;
           int count = 0;
           LocalDate today = LocalDate.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

           while ((line = prenotazioniReader.readLine()) != null) {
               String[] prenotazioneParts = line.split(",");
               LocalDate dataInizio = LocalDate.parse(prenotazioneParts[1], formatter); // Data di inizio della prenotazione
               if (dataInizio.isEqual(today)) { // Controlla se la data di inizio è oggi o in futuro
                   boolean alreadyCheckedIn = false;
                   for (String checkinLine : checkinLines) {
                       String[] checkinParts = checkinLine.split(",");
                       // Confronta le colonne specificate
                       if (checkinParts.length > 3 &&
                           prenotazioneParts.length > 2 &&
                           checkinParts[0].equals(prenotazioneParts[3]) && // Confronta la colonna 0 di checkin con la colonna 3 di prenotazioni
                           checkinParts[1].equals(prenotazioneParts[1]) && // Confronta la colonna 1 di checkin con la colonna 1 di prenotazioni
                           checkinParts[2].equals(prenotazioneParts[2])) { // Confronta la colonna 2 di checkin con la colonna 2 di prenotazioni
                           alreadyCheckedIn = true;
                           break;
                       }
                   }

                   if (!alreadyCheckedIn) {
                       data.add(prenotazioneParts);
                       count++;
                   }
               }
           }

           if (count == 0) {
               elencoCheckInLb1.setText("Non ci sono Check-in in arrivo oggi.");
               infoAlertBox("Non ci sono Check-in in arrivo oggi.");
               return;
           }

           elencoCheckInLb1.setText("Sono presenti " + count + " Check-in in arrivo oggi.");

       } catch (IOException e) {
           e.printStackTrace();
       }

       elencoCheckInTab.setItems(data);

       elencoCheckInNumCamera.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
       elencoCheckInDataInizio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
       elencoCheckInDataFine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
       elencoCheckInIDCliente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
       elencoCheckInAdulti.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[4]));
       elencoCheckInBambini.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[5]));
       elencoCheckInTariffa.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[6]));
       elencoCheckInTotale.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[7]));
   }



   @FXML
   private void loadAllCheckInTable() {
       ObservableList<String[]> data = FXCollections.observableArrayList();
       elencoCheckInTab.getItems().clear(); // Svuota la tabella

       String percorsoFile = ottieniPercorsoFile("checkin.csv");

       try (BufferedReader checkinReader = new BufferedReader(new FileReader(percorsoFile))) {
           String line;

           while ((line = checkinReader.readLine()) != null) {
               String[] checkinParts = line.split(",");
               data.add(checkinParts);
           }

           if (data.isEmpty()) {
               elencoCheckInLb1.setText("Non ci sono Check-in.");
               infoAlertBox("Non ci sono Check-in.");
               return;
           }

           elencoCheckInLb1.setText("Sono presenti " + data.size() + " Check-in.");

       } catch (IOException e) {
           e.printStackTrace();
       }

       elencoCheckInTab.setItems(data);

       // Nascondi colonne non necessarie e rinomina le altre
       elencoCheckInNumCamera.setVisible(false);
       elencoCheckInBambini.setVisible(false);

       elencoCheckInAdulti.setText("Tot. persone");
       elencoCheckInTariffa.setText("Nome");
       elencoCheckInTotale.setText("Cognome");

       elencoCheckInIDCliente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));  
       elencoCheckInDataInizio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
       elencoCheckInDataFine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
       elencoCheckInAdulti.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
       elencoCheckInTariffa.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[4]));
       elencoCheckInTotale.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[5]));


   }

    
    @FXML
    private void effettuaCheckIn() {
    // Ottieni l'indice della riga selezionata
    int selectedIndex = elencoCheckInTab.getSelectionModel().getSelectedIndex();
    if (selectedIndex != -1) { // Controlla se è stata selezionata una riga
        // Ottieni l'array di stringhe corrispondente alla riga selezionata
        String[] rowData = elencoCheckInTab.getItems().get(selectedIndex);
        // Assicurati che l'array abbia almeno 3 elementi prima di accedere agli elementi delle colonne
        if (rowData.length >= 3) {
            // Imposta le label con i valori delle colonne 1, 2 e 3 della riga selezionata
            ckInIDClienteLb.setText(rowData[3]);
            ckInDataInizioLb.setText(rowData[1]);
            ckInDataFineLb.setText(rowData[2]);
            ckInNumPers.setText(String.valueOf(Integer.parseInt(rowData[4]) + Integer.parseInt(rowData[5])));
        } else {
            // Se l'array non ha almeno 3 elementi, mostra un messaggio di avviso o gestisci l'errore in altro modo
            errorAlertBox("Errore di lettura.");
            return;
        }
    } else {
        // Se non è stata selezionata nessuna riga, mostra un messaggio di avviso o gestisci l'errore in altro modo
        errorAlertBox("Seleziona una prenotazione.");
        return;
    }
    
    nascondiTuttiPannelli();
    effettuaCheckIn.setVisible(true);
    
}
    
    // Aggiunta dati della prenotazione al file
    @FXML
    private void salvaDatiCheckIn() throws IOException{
        String percorsoFile = ottieniPercorsoFile("checkin.csv");
    
       
        if (ckInNome.getText().isEmpty() || ckInCognome.getText().isEmpty()){
            errorAlertBox("Devi riempire i campi Nome e Cognome."); 
            return;
            }
    
     try (PrintWriter writer = new PrintWriter(new FileWriter(percorsoFile, true))) {
            writer.println(ckInIDClienteLb.getText() + "," + ckInDataInizioLb.getText() + "," + ckInDataFineLb.getText() + "," + ckInNumPers.getText() + "," + ckInNome.getText() + "," + ckInCognome.getText());   
     }
            infoAlertBox("Check-in avvenuto con successo.");
            apriListaCheckinArrivo();
}
    
    // Imposta i dati visualizzati nella schermata Realtime.
    public void setRealtimeScreen() {    
        String percorsoFile = ottieniPercorsoFile("checkin.csv");
        String percorsoCamereFile = ottieniPercorsoFile("camere.csv");
        String percorsoPrenotazioniFile = ottieniPercorsoFile("prenotazioni.csv");

        int camereOccupate = 0;
        int totalePersone = 0;
        int totalNumberOfRooms = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Verifica se ci sono abbastanza elementi nella riga e che la riga non sia vuota
                if (data.length >= 5 && !line.trim().isEmpty()) {
                    LocalDate dataInizio = LocalDate.parse(data[1]);
                    LocalDate dataFine = LocalDate.parse(data[2]);
                    int numPersone = Integer.parseInt(data[3]);
                    String nome = data[4];
                    String cognome = data[5];

                    LocalDate dataOdierna = LocalDate.now();

                    // Verifica se la data odierna è compresa tra la data di inizio e la data di fine
                    if ((dataOdierna.isEqual(dataInizio) || dataOdierna.isAfter(dataInizio)) && dataOdierna.isBefore(dataFine)) {
                        camereOccupate++;
                        totalePersone = totalePersone + numPersone;
                    }
                }
            }

            // Stampare il numero totale di righe in camere.csv
            
            try (BufferedReader brCamere = new BufferedReader(new FileReader(percorsoCamereFile))) {
                String lineCamere;
                while ((lineCamere = brCamere.readLine()) != null) {
                    if (!lineCamere.trim().isEmpty()) {
                        totalNumberOfRooms++;
                    }
                }
                float ratioOccupazione = ((float)camereOccupate/(float)totalNumberOfRooms)*100;
                realtimeRatioOccupazione.setText(String.format("%.1f", ratioOccupazione));
            } catch (IOException e) {
                e.printStackTrace(); // Gestione dell'eccezione
            }

            realtimeNumCamereOccupate.setText(String.valueOf(camereOccupate));
            realtimeNumCamereOccupate2.setText(String.valueOf(camereOccupate));
            realtimeNumTotalePersone.setText(String.valueOf(totalePersone));
            realtimeNumCamereLibere.setText(String.valueOf(totalNumberOfRooms - camereOccupate));
            
            //Parte che conta quanti checkin sono in arrivo oggi e quanta gente arriva
            
            LocalDate oggi = LocalDate.now();

            int prenotazioniPerOggi = 0;
            int personeInArrivo = 0;
            int checkOutOggi = 0;

            try (BufferedReader brb = new BufferedReader(new FileReader(percorsoPrenotazioniFile))) {
                while ((line = brb.readLine()) != null) {
                    String[] rowData = line.split(",");
                    if (rowData.length >= 3) {
                        LocalDate dataInizio = LocalDate.parse(rowData[1]);
                        LocalDate dataFine = LocalDate.parse(rowData[2]);

                        // Conta le prenotazioni per il giorno corrente
                        if (dataInizio.equals(oggi)) {
                            prenotazioniPerOggi++;
                                int numAdulti = Integer.parseInt(rowData[4]);
                                int numBambini = Integer.parseInt(rowData[5]);
                                personeInArrivo += numAdulti + numBambini;
                        }
                        if (dataFine.equals(oggi)) {
                            checkOutOggi++;
                        }
                    }
                }
                realtimeNumPrenotazioniPerOggi.setText(String.valueOf(prenotazioniPerOggi));
                realtimeNumClientiInArrivo.setText(String.valueOf(personeInArrivo));
                realtimeNumCheckOutOggi.setText(String.valueOf(checkOutOggi));
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (IOException e) {
            e.printStackTrace(); // Gestione dell'eccezione
        }
        
        String line = "";
        String cvsSplitBy = ",";
        int countZero = 0;
        int countOne = 0;
        int countTwo = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ottieniPercorsoFile("camere.csv")))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                int lastInt = Integer.parseInt(values[4]);
                switch (lastInt) {
                    case 0:
                        countZero++;
                        break;
                    case 1:
                        countOne++;
                        break;
                    case 2:
                        countTwo++;
                        break;
                    default:
                        // Gestione caso non previsto
                        break;
                }
            }
            stanzePuliteLbl2.setText(Integer.toString(countZero));
            stanzeDaPulireLbl2.setText(Integer.toString(countOne));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
        
        
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    // Aggiunta camere al file e aggiornamento della tabella
    @FXML
    private void addCameraToFile() throws IOException {
        String percorsoFile = ottieniPercorsoFile("camere.csv");

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

        // Pulisci i campi di input
        regNumCamera.clear();
        regPianoCamera.clear();
        regNumLettiCamera.clear();
        
        //Richiama funzione per aggiornare tabella
        loadElencoCameraTable();
}
    
    //Funzione per cancellare camera
    @FXML
    private void deleteSelectedRow() throws IOException {
        String percorsoFile = ottieniPercorsoFile("camere.csv");
        
        // Ottieni l'indice della riga selezionata nella tabella
        int selectedIndex = elencoCamereTab.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            // Nessuna riga selezionata, esci dalla funzione
            return;
        }

        // Leggi il contenuto attuale del file CSV e salva tutte le righe tranne quella selezionata
        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            StringBuilder newData = new StringBuilder();
            String line;
            int currentIndex = 0;
            while ((line = br.readLine()) != null) {
                if (currentIndex != selectedIndex) {
                    newData.append(line).append("\n");
                }
                currentIndex++;
            }

            // Sovrascrivi il contenuto del file CSV con le righe rimanenti
            try (PrintWriter writer = new PrintWriter(new FileWriter(percorsoFile))) {
                writer.print(newData.toString());
            }
        }

        // Aggiorna la tabella dopo la cancellazione
        loadElencoCameraTable();
    }

    //Funzione per contare le camere da pulire e popolare la schermata realtime pulizia
    public void countCamereDaPulire() {
        String line = "";
        String cvsSplitBy = ",";
        int countZero = 0;
        int countOne = 0;
        int countTwo = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ottieniPercorsoFile("camere.csv")))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                int lastInt = Integer.parseInt(values[4]);
                switch (lastInt) {
                    case 0:
                        countZero++;
                        break;
                    case 1:
                        countOne++;
                        break;
                    case 2:
                        countTwo++;
                        break;
                    default:
                        break;
                }
            }
            stanzePuliteLbl.setText(Integer.toString(countZero));
            stanzeDaPulireLbl.setText(Integer.toString(countOne));
            stanzeNonDisturbareLbl.setText(Integer.toString(countTwo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Funzione che gestisce i bottoni della schermata pulizia
    @FXML
    private void selezioneCameraDaModificarePulizia() {
        String percorsoFile = ottieniPercorsoFile("camere.csv");
        String stanzaSelezionata = (String) selezionaStanzaPulizia.getValue();
        
        if (stanzaSelezionata.equals("-Seleziona-")){
            puliziaPulitaButton.setDisable(true);
            puliziaDaPulireButton.setDisable(true);
            puliziaNonDistButton.setDisable(true);
            return;
            }
        
        if (stanzaSelezionata == null) {
            return;
        }

        try (BufferedReader camereReader = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            while ((line = camereReader.readLine()) != null) {
                // Split della riga per ottenere i parametri
                String[] parts = line.split(",");
                String numeroStanza = parts[0].trim();

                if (numeroStanza.equals(stanzaSelezionata)) {
                    String pulizia = parts[parts.length - 1].trim(); // Rimuovi eventuali spazi
                    
                    // Verifica il valore di "Pulizia" e aggiorna i pulsanti
                    switch (pulizia) {
                        case "0":
                            puliziaPulitaButton.setDisable(true);
                            puliziaDaPulireButton.setDisable(false);
                            puliziaNonDistButton.setDisable(false);
                            break;
                        case "1":
                            puliziaPulitaButton.setDisable(false);
                            puliziaDaPulireButton.setDisable(true);
                            puliziaNonDistButton.setDisable(false);
                            break;
                        case "2":
                            puliziaPulitaButton.setDisable(false);
                            puliziaDaPulireButton.setDisable(false);
                            puliziaNonDistButton.setDisable(true);
                            break;
                    }
                    // Una volta trovata la stanza corrispondente, esci dal ciclo
                    break;
                }
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
       
    //Funzioni di chiamata dal codice, per evitare la ripetizione della stessa funzione 3 volte.
    @FXML
    private void updateCameraDaPulire() throws IOException{updateCameraStatus("1");}
    @FXML
    private void updateCameraPulita() throws IOException{updateCameraStatus("0");}
    @FXML
    private void updateCameraNonDisturbare() throws IOException{updateCameraStatus("2");}
        
    
        
    //Funzione per modificare lo stato della pulizia di una camera nel file.
    //parametro il valore di pulizia da assuemre.
    private void updateCameraStatus(String NuovoValore) throws IOException {
        String percorsoFile = ottieniPercorsoFile("camere.csv");
        List<String[]> allData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(percorsoFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(selezionaStanzaPulizia.getValue())) {
                    values[4] = NuovoValore;
                }
                allData.add(values);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(percorsoFile))) {
            for (String[] row : allData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
        countCamereDaPulire();
        selezionaStanzaPulizia.setValue("-Seleziona-");
        selezioneCameraDaModificarePulizia();
    }
    
    //Funzione che carica i dati nella tabella della timeline
    //Parametro di ingresso mese come int
    private void caricaDati(int mese) throws IOException {
        String filePath = ottieniPercorsoFile("prenotazioni.csv");
        Map<LocalDate, List<String[]>> prenotazioniPerGiorno = leggiPrenotazioni(filePath, mese);

        tableViewRealtime.getItems().clear(); // Pulisce i dati esistenti

        // Aggiungere i dati alla tabella, ordinati per giorno
        prenotazioniPerGiorno.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> tableViewRealtime.getItems().addAll(entry.getValue()));
    }

    private Map<LocalDate, List<String[]>> leggiPrenotazioni(String filePath, int mese) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1) // Salta l'intestazione
                    .map(line -> line.split(","))
                    .filter(parts -> {
                        LocalDate date = LocalDate.parse(parts[1], formatter);
                        return date.getMonthValue() == mese;
                    })
                    .collect(Collectors.groupingBy(parts -> LocalDate.parse(parts[1], formatter)));
        }
    }
    
    //Funzione del bottone meseSuccessivo, incrementa il mese e aggiorna la label
    @FXML
    private void timelineMeseSuccessivo() throws IOException{
        meseAttualeTimeline++;
        int currentYear = currentDate.getYear();
        Month mese = Month.of(meseAttualeTimeline);     
        String monthName = mese.getDisplayName(java.time.format.TextStyle.FULL, Locale.ITALIAN);
        monthLabel.setText(monthName + " " + currentYear);
        caricaDati(meseAttualeTimeline);      
        }
    //Funzione del bottone mesePrecedente, decrementa il mese e aggiorna la label
    @FXML
    private void timelineMesePrecedente() throws IOException{
        meseAttualeTimeline--;
        int currentYear = currentDate.getYear();
        Month mese = Month.of(meseAttualeTimeline);      
        String monthName = mese.getDisplayName(java.time.format.TextStyle.FULL, Locale.ITALIAN);
        monthLabel.setText(monthName + " " + currentYear);
        caricaDati(meseAttualeTimeline);             
        }
    
    
    //Funzione per ottenere i percorsi assoluti dei file
    private static String ottieniPercorsoFile(String nomeFile){
        File mainFile = new File(MainController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String basePath = mainFile.getParent();
        // Costruisci il percorso completo del file "data/accounts.csv"
        String filePath = Paths.get(basePath).toString();
        int index = filePath.indexOf("dist");
        // Se "dist" è presente nella stringa, estrai la parte prima di "dist"
        String percorsoFile = null;
        if (index != -1) {
            String result = filePath.substring(0, index);
            percorsoFile = result + "src/esempio/MdC_firstScreen/resources/db/" + nomeFile;
        }
        return percorsoFile;
    }
    
    //Funzione per impostare lo schermo intero
    @FXML
    private void setFullScreen(){
        Stage stage = (Stage) firstPane.getScene().getWindow();
        if (stage.isFullScreen() == false){
            stage.setFullScreenExitHint("Esci dalla modalità Schermo intero attraverso il tasto ESC oppure dal menu Utente>Schermo intero.");
            stage.setFullScreen(true);}
        else {
            stage.setFullScreen(false);
        }
        MainApp mainApp = new MainApp();   
    }
    
    //Creazione dialogbox di errore
    private void errorAlertBox(String informazione){  
        try {
            // Creazione di un'istanza di Alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");

            alert.setHeaderText("");
            alert.setContentText(informazione);

            // Visualizzazione della finestra di dialogo e attesa dell'utente
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     //Creazione dialogbox di errore
    private void infoAlertBox(String informazione){
        try {
            // Creazione di un'istanza di Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informazione");
            
            alert.setHeaderText("");
            alert.setContentText(informazione);

            // Visualizzazione della finestra di dialogo e attesa dell'utente
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
}