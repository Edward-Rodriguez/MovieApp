package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Edward Rodriguez & _____________
 */
import javafx.scene.image.Image;

public class MovieTableModel {

    ObservableList<Movie> movies;

    private Movie selectedMovie;

    public MovieTableModel() {
        //ui = initUI;
        movies = FXCollections.observableArrayList();
        //  isDraft = false;
        // isOutboxMessage = false;
        reset();
    }
    // ACCESSOR METHODS

    public boolean isMovieSelected() {
        return (selectedMovie != null) ? true : false;
    }
    public boolean isSelectedMovie(Movie TestMovie) {
        return selectedMovie == TestMovie;
    }
    public ObservableList<Movie> getMovies() {
        return movies;
    }
    public Movie getSelectedMovie() {
        return selectedMovie;
    }


    // MUTATOR METHODS
    public void setSelectedMovie(Movie initSelectedMovie) {
        selectedMovie = initSelectedMovie;
    }
	    /*
	    public void setDraftMessage(boolean draftMessage) {
	        isDraft = draftMessage;
	    }

	    public void setOutboxMessage (boolean outMessage) {
	        isOutboxMessage = outMessage;
	    }
	    // SERVICE METHODS
	    /**
	     * Resets the table to have no messages
	     */


    public void reset() {
        movies.clear();
        selectedMovie = null;
    }
    /**
     * Adds a movie to the moviePane.
     */

    public void addMovie( Movie movie) {
        this.movies.add(movie);
    }

    public void addMovie(String movieTitle, String rating, String releaseType, String urlOfImage, String summary) {

        Movie movie = new Movie(movieTitle, rating, releaseType, urlOfImage, summary);
        movies.add(0, movie);
    }
    /**
     * Removes the currently selected message from list
     * updates the display.
     */
    public void removeSelectedMovie() {
        if (isMovieSelected()) {
            movies.remove(selectedMovie);
            selectedMovie = null;
            //ui.reloadMessagePane();
        }
    }

    public void removeMovie(Movie movie) {
        movies.remove(movie);
    }

}