package model;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.util.Observable;

public class Movie {

    private String movieTitle;
    private String description;


    private String rating;
    private String releaseType;
    private String urlOfImage;
    private ObservableList<String> listOfCinemas;


    public Movie(String movieTitle, String description, String rating, String releaseType, String urlOfImage) {
        this.movieTitle = movieTitle;
        this.description = description;
        this.rating = rating;
        this.releaseType = releaseType;
        this.urlOfImage = urlOfImage;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

}