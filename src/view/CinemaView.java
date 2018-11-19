package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CinemaView extends VBox {

    Movie movie;
    GridPane gridPane = new GridPane();
    Label address;
    Separator cinemaDividor;
    Movie.CinemaListAndShowtime cinemaListAndShowtime;

    public CinemaView(Movie movie) {

        this.cinemaListAndShowtime = movie.getCinemaListAndShowtime();
        this.movie = movie;
        this.setSpacing(25);
        this.setPadding(new Insets(20,20,20,20));

        createShowtimeLabels();
    }

    private void createShowtimeLabels() {
        for (Map.Entry<String, ArrayList<String>> entry : cinemaListAndShowtime.getMovieShowtimesMap().entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();

            HBox container = new HBox();
            Label cinemaName = new Label(key);
            cinemaName.setStyle("-fx-alignment: center; -fx-font: normal bold 20px 'Arial'; -fx-text-fill: #FFFFFF");
            cinemaName.setMinWidth(250);
            cinemaName.setAlignment(Pos.BOTTOM_LEFT);
            cinemaName.setPadding(new Insets(10,10,10,10));
            container.getChildren().add(cinemaName);

            Label addressLabel = new Label(cinemaListAndShowtime.getMovieCinemaAddressMap().get(key));
            addressLabel.setStyle("-fx-alignment: center; -fx-font: normal bold 20px 'Arial'; -fx-text-fill: #FFFFFF");
            addressLabel.setMinWidth(100);
            addressLabel.setAlignment(Pos.BOTTOM_LEFT);
            addressLabel.setPadding(new Insets(10,10,10,10));
            container.getChildren().add(addressLabel);

            Separator lineSeparator = new Separator();
            lineSeparator.setMaxWidth(300);
            Label showTimesLabel = new Label("Showtimes");
            showTimesLabel.setStyle("-fx-alignment: CENTER; -fx-font: normal bold 15px 'Arial'; -fx-text-fill: #FFFFFF");
            showTimesLabel.setAlignment(Pos.CENTER);
            showTimesLabel.setPadding(new Insets(0, 0, 0, 110));

            VBox showtimesLayout = new VBox();
            FlowPane showtimesPane = new FlowPane();
            showtimesPane.setPadding(new Insets(5,5,5,5));

            for(String aString : value){
                Button tempShowtimeButton = new Button(aString);
                tempShowtimeButton.setMinWidth(100);
                tempShowtimeButton.setPadding(new Insets(5,5,5,5));
                showtimesPane.getChildren().add(tempShowtimeButton);
                //System.out.println("key : " + key + " value : " + aString);
            }
            showtimesLayout.getChildren().addAll(showTimesLabel, lineSeparator, showtimesPane);
            container.getChildren().add(showtimesLayout);
            this.getChildren().add(container);
        }
    }
}
