package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.Iterator;

public class CinemaShowtimesSelectionBox extends HBox {

    private CheckBox cinemaCheckBox;
    private ComboBox showtimesBox1;
    private ComboBox showtimesBox2;
    private ComboBox showtimesBox3;
    private String cinemaName;
    private boolean checkboxTicked;
    ObservableList<String> showtimesList;


    public CinemaShowtimesSelectionBox(String cinemaName) {
        checkboxTicked = false;
        this.cinemaName = cinemaName;
        showtimesList = FXCollections.observableArrayList();
        showtimesList.addAll("9:00am", "9:30am", "10:00am", "10:30am",
                "11:00am", "11:30am", "12:00pm", "12:30pm",
                "1:00pm", "1:30pm", "2:00pm", "2:30pm",
                "3:00pm", "3:30pm", "4:00pm", "4:30pm",
                "5:00pm", "5:30pm", "6:00pm", "6:30pm",
                "7:00pm", "7:30pm", "8:00pm", "8:30pm",
                "9:00pm", "9:30pm", "10:00pm", "10:30pm",
                "11:00pm", "11:30pm", "12:00am");

        for (Iterator i = showtimesList.iterator(); i.hasNext();)
            if (i.next().equals("Corn")) {
                i.remove();
            }


        cinemaCheckBox = new CheckBox(this.cinemaName);
        cinemaCheckBox.setMinWidth(150);

        showtimesBox1 = new ComboBox(showtimesList);
        showtimesBox2 = new ComboBox(showtimesList);
        showtimesBox3 = new ComboBox(showtimesList);
        showtimesBox1.setDisable(true);
        showtimesBox2.setDisable(true);
        showtimesBox3.setDisable(true);

        ConnectedComboBox<String> connectedComboBox = new ConnectedComboBox<>(showtimesList);
        connectedComboBox.addComboBox(showtimesBox1);
        connectedComboBox.addComboBox(showtimesBox2);
        connectedComboBox.addComboBox(showtimesBox3);

        cinemaCheckBox.setOnAction(e -> {
            if (checkboxTicked) {
                showtimesBox1.setDisable(true);
                showtimesBox2.setDisable(true);
                showtimesBox3.setDisable(true);
            } else {
                showtimesBox1.setDisable(false);
                showtimesBox2.setDisable(false);
                showtimesBox3.setDisable(false);
            }
        });

        this.setMinWidth(200);

        this.getChildren().addAll(cinemaCheckBox, showtimesBox1, showtimesBox2, showtimesBox3);
    }

    public CheckBox getCinemaCheckBox() {
        return cinemaCheckBox;
    }

    public void setCinemaCheckBox(CheckBox cinemaCheckBox) {
        this.cinemaCheckBox = cinemaCheckBox;
    }

    public ComboBox getShowtimesBox1() {
        return showtimesBox1;
    }

    public void setShowtimesBox1(ComboBox showtimesBox1) {
        this.showtimesBox1 = showtimesBox1;
    }

    public ComboBox getShowtimesBox2() {
        return showtimesBox2;
    }

    public void setShowtimesBox2(ComboBox showtimesBox2) {
        this.showtimesBox2 = showtimesBox2;
    }

    public ComboBox getShowtimesBox3() {
        return showtimesBox3;
    }

    public void setShowtimesBox3(ComboBox showtimesBox3) {
        this.showtimesBox3 = showtimesBox3;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
