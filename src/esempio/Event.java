package esempio;

import java.time.LocalDate;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Event {
    private final ObjectProperty<LocalDate> date;
    private final StringProperty numCamera;
    private final IntegerProperty numAdulti;
    private final IntegerProperty numBambini;

    public Event(LocalDate date, String numCamera, int numAdulti, int numBambini) {
        this.date = new SimpleObjectProperty<>(date);
        this.numCamera = new SimpleStringProperty(numCamera);
        this.numAdulti = new SimpleIntegerProperty(numAdulti);
        this.numBambini = new SimpleIntegerProperty(numBambini);
        
    }
    public int getDayOfMonth() {
        return date.get().getDayOfMonth();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getNumCamera() {
        return numCamera.get();
    }

    public StringProperty numCameraProperty() {
        return numCamera;
    }





    public int getNumAdulti() {
        return numAdulti.get();
    }

    public IntegerProperty numAdultiProperty() {
        return numAdulti;
    }

    public int getNumBambini() {
        return numBambini.get();
    }

    public IntegerProperty numBambiniProperty() {
        return numBambini;
    }

    
}
