package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Movie;

public class CinemaView extends HBox {

    Movie movie;
    String[] listOfShowtimes = {"10:30am", "12:30pm", "3:00pm", "5:00pm", "7:15pm", "10:30pm" };
    GridPane gridPane = new GridPane();
    FlowPane showtimesPane;
    Label cinemaName;
    Label address;
    Separator lineSeparator;
    Label showTimesLabel;
    VBox showtimesLayout;
    Separator cinemaDividor;

    public CinemaView(Movie movie) {
        this.movie = movie;
        showtimesPane = new FlowPane();
        showtimesPane.setPadding(new Insets(10, 10, 10, 10));
        showtimesPane.setMaxWidth(250);

        cinemaName = new Label(movie.getCinemaPlaying());
        cinemaName.setStyle("-fx-alignment: center; -fx-font: normal bold 20px 'Arial'; -fx-text-fill: #000000");
        cinemaName.setMinWidth(200);
        cinemaName.setAlignment(Pos.CENTER_LEFT);

        lineSeparator = new Separator();
        showTimesLabel = new Label("Showtimes");
        showTimesLabel.setStyle("-fx-alignment: CENTER; -fx-font: normal bold 15px 'Arial'; -fx-text-fill: #000000");
        showTimesLabel.setAlignment(Pos.CENTER);
        showTimesLabel.setPadding(new Insets(0, 0, 0, 70));

        showtimesLayout = new VBox();
        showtimesLayout.getChildren().addAll(showTimesLabel, showtimesPane);

//        cinemaDividor = new Separator();
//        cinemaDividor.setMaxWidth(600);

        createShowtimeLabels();
        this.getChildren().addAll(cinemaName, showtimesLayout);
    }

    private void createShowtimeLabels() {
        for (int i = 0; i < listOfShowtimes.length; ++i) {
            Button button = new Button(listOfShowtimes[i]);
            button.setMinWidth(100);
            button.setPadding(new Insets(5,5,5,5));
            showtimesPane.getChildren().add(button);
        }
    }
}
