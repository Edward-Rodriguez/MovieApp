package controller;

import javafx.event.ActionEvent;
import model.Movie;
import model.MovieTableModel;
import view.MovieAppView;

public class MovieController {

    MovieAppView ui;
    Movie movie;
    //MovieTableModel model;

    public MovieController (MovieAppView view) {
        ui = view;
    }
//
//    public void handleAllCheckbox(ActionEvent a) {
//        if (view.)
//            GBox.setSelected(true);
//            PGBox.setSelected(true);
//            PG13Box.setSelected(true);
//            RBox.setSelected(true);
//            NC17Box.setSelected(true);
//    }
    public void processAllCheckBox() {
        MovieTableModel model = ui.getMovieList();
        if (ui.allCheckBoxIsSelected()) {

        }
    }

    public void processGCheckBox() {
        MovieTableModel model = ui.getMovieList();
//        model.f
    }

}
