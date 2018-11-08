package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Edward Rodriguez & _____________
 */
import javafx.scene.image.Image;

public class MovieTableModel {

    ObservableList<Movie> movie;
    private Movie selectedMovie;

    public MovieTableModel() {
        //ui = initUI;
        movie = FXCollections.observableArrayList();
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
        return movie;
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
        movie.clear();
        selectedMovie = null;
    }
    /**
     * Adds a movie to the moviePane.
     */
    public void addMovie( String url, String Title, String rating, String description, String releaseType ) {
        Movie movieToAdd = new Movie( Title, description, rating, releaseType, url);
        movie.add(0, movieToAdd);
        //ui.reloadMessagePane();
    }

    public void addMovie( Movie movie) {
        this.movie.add(movie);
    }
    /**
     * Removes the currently selected message from list
     * updates the display.
     */
    public void removeSelectedMovie() {
        if (isMovieSelected()) {
            movie.remove(selectedMovie);
            selectedMovie = null;
            //ui.reloadMessagePane();
        }
    }

    public void removeMessage(Movie message) {
        movie.remove(movie);
    }

}