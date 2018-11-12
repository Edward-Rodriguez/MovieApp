package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import model.Movie;
import model.MovieTableModel;
import view.MovieAppView;

public class MovieController {

    MovieAppView ui;
    MovieTableModel model;
    MovieTableModel newModel;

    public MovieController (MovieAppView view) {
        ui = view;
        model = ui.getMovieList();
        newModel = new MovieTableModel();
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
        if (ui.allCheckBoxIsSelected()) {

        }
    }

    public void processGCheckBox() {
        for (Movie movie : model.getMovies()) {
            if (movie.getRating().equals("G")) {
                newModel.addMovie(movie);
            }
        }
        ui.reloadMovieListPane(newModel);
    }

    public void processPGCheckBox() {
        newModel.reset();
        for (Movie movie : model.getMovies()) {
            if (movie.getRating().equals("PG")) {
                newModel.addMovie(movie);
            }
        }
        ui.reloadMovieListPane(newModel);
    }

}
