package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import model.Movie;
import model.MovieTableModel;
import view.MovieAppView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieController {

    MovieAppView ui;
    MovieTableModel model;
    MovieTableModel newModel;
    HashMap<Movie, String> movieMap;
    HashMap<String, Movie> movieRatingMap;
    CheckBox[] checkBoxArray;

    public MovieController (MovieAppView view) {
        ui = view;
        model = ui.getMovieList();
        newModel = new MovieTableModel();
        movieMap = new HashMap<Movie, String>();
        movieRatingMap = new HashMap<String, Movie>();
        checkBoxArray = new CheckBox[6];
        checkBoxArray[0] = ui.getAllCheckBox();
        checkBoxArray[1] = ui.getgRatingCheckBox();
        checkBoxArray[2] = ui.getPgRatingCheckBox();
        checkBoxArray[3] = ui.getPg13RatingCheckBox();
        checkBoxArray[4] = ui.getgRatingCheckBox();
        checkBoxArray[5] = ui.getNc17RatingCheckBox();
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

    public void processCheckboxes() {
        newModel.reset();
        movieRatingMap.clear();
        movieMap.clear();
        for(Movie movie : model.getMovies()) {
            if (checkBoxArray[1].isSelected())
                if (movie.getRating().equals("G"))
                    movieMap.put(movie, "G");
                else if (movieMap.containsKey(movie))
                    movieMap.remove(movie);
            if (checkBoxArray[2].isSelected())
                if (movie.getRating().equals("PG"))
                    movieMap.put(movie, "PG");
                else if (movieMap.containsKey(movie))
                    movieMap.remove(movie);
            if (checkBoxArray[3].isSelected())
                if (movie.getRating().equals("PG-13"))
                    movieMap.put(movie, "PG-13");
                else if (movieMap.containsKey(movie))
                    movieMap.remove(movie);
            if (checkBoxArray[4].isSelected())
                if (movie.getRating().equals("R"))
                    movieMap.put(movie, "R");
                else if (movieMap.containsKey(movie))
                    movieMap.remove(movie);
            if (checkBoxArray[5].isSelected())
                if (movie.getRating().equals("NC-17"))
                    movieMap.put(movie, "NC-17");
                else if (movieMap.containsKey(movie))
                    movieMap.remove(movie);
        }
        for (Map.Entry<Movie, String> entry : movieMap.entrySet()) {
            String key = entry.getValue();
            Movie value = entry.getKey();

            newModel.addMovie(value);
        }
        ui.reloadMovieListPane(newModel);
    }


    public void processAllCheckBox() {
        if (ui.allCheckBoxIsSelected()) {
            ui.reloadMovieListPane(model);
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
