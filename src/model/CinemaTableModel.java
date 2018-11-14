package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Observable;

public class CinemaTableModel {

    ObservableList<Cinema> cinemas;
    private Cinema selectedCinema;

    public CinemaTableModel() {
        cinemas = FXCollections.observableArrayList();
    }

    public boolean isCinemaSelected() {
        return (selectedCinema != null) ? true : false;
    }

    public ObservableList<Cinema> getCinemas() {
        return cinemas;
    }

    public void setCinemas(ObservableList<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public Cinema getSelectedCinema() {
        return selectedCinema;
    }

    public void setSelectedCinema(Cinema selectedCinema) {
        this.selectedCinema = selectedCinema;
    }

    public void reset() {
        cinemas.clear();
        selectedCinema = null;
    }

    public void addCinema(Cinema cinema) {
        this.cinemas.add(cinema);
    }

    public void addCinema(int id, String cinemaName, String address) {
        Cinema cinema = new Cinema(id, cinemaName, address);
        this.cinemas.add(cinema);
    }

    public void removeSelectedCinema() {
        if (isCinemaSelected()) {
            cinemas.remove(selectedCinema);
            selectedCinema = null;
        }
    }

}
