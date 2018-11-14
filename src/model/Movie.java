package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.util.Observable;

public class Movie {

    private String movieTitle;
    private String rating;
    private int id;
    private String releaseType;
    private String urlOfImage;
    private String movieSummary;

    private ObservableList<String> listOfCinemas;

    public Movie(String movieTitle, String rating, String releaseType, String urlOfImage, String movieSummary) {
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.releaseType = releaseType;
        this.urlOfImage = urlOfImage;
        this.movieSummary = movieSummary;
        listOfCinemas = FXCollections.observableArrayList();
    }

    public String getRating() {
        return rating;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(String releaseType) {
        this.releaseType = releaseType;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public ObservableList<String> getListOfCinemas() {
        return listOfCinemas;
    }

    public void setListOfCinemas(ObservableList<String> listOfCinemas) {
        this.listOfCinemas = listOfCinemas;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }


    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMovieSummary() {
        return movieSummary;
    }

    public void setMovieSummary(String movieSummary) {
        this.movieSummary = movieSummary;
    }

}