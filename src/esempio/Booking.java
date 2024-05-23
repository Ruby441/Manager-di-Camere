//Manager di Camere - authors Ponzini Andrea, Rubinacci Antonio
//Ultima modifica 09/05/2024 - Versione 09.05 RELASE

package esempio;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Booking {
    private final StringProperty number;
    private final StringProperty type;
    private final StringProperty status;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Booking(String number, String type, String status, LocalDate startDate, LocalDate endDate) {
        this.number = new SimpleStringProperty(number);
        this.type = new SimpleStringProperty(type);
        this.status = new SimpleStringProperty(status);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public StringProperty getBookingForDate(LocalDate date) {
        if (date.isAfter(endDate) || date.isBefore(startDate)) {
            return new SimpleStringProperty(""); // Nessuna prenotazione per questa data
        } else {
            return status; // Restituisce lo stato della prenotazione per la data specificata
        }
    }
}
