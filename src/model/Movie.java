package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class Movie {

    private String movieTitle;
    private String rating;
    private int id;
    private String releaseType;
    private String urlOfImage;
    private String movieSummary;
    private ArrayList<String> tempArray;
    private CinemaListAndShowtime cinemaListAndShowtime;
    private CinemaListAndShowtime cinemaListAndAddress;

    public Movie(String movieTitle, String rating, String releaseType, String urlOfImage, String movieSummary) {
        this.movieTitle = movieTitle;
        this.rating = rating;
        this.releaseType = releaseType;
        this.urlOfImage = urlOfImage;
        this.movieSummary = movieSummary;
        tempArray = new ArrayList<String>();
        cinemaListAndShowtime = new CinemaListAndShowtime();
        cinemaListAndAddress = new CinemaListAndShowtime();
    }

    public ArrayList<String> getTempArray() {
        return tempArray;
    }

    public void setTempArray(ArrayList<String> tempArray) {
        this.tempArray = tempArray;
    }

    public CinemaListAndShowtime getCinemaListAndShowtime() {
        return cinemaListAndShowtime;
    }

    public void setCinemaListAndShowtime(CinemaListAndShowtime cinemaListAndShowtime) {
        this.cinemaListAndShowtime = cinemaListAndShowtime;
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

    public void addShowtime(String cinemaName, String showtime, String address) {
        cinemaListAndShowtime.addMovieShowtimesToMap(cinemaName, showtime, address);
    }

    public void addAddress(String address) {
        cinemaListAndShowtime.setAddress(address);
    }

    public class CinemaListAndShowtime {
        private String cinemaName;
        private String address;
        private HashMap<String, ArrayList<String>> movieShowtimesMap;
        private HashMap<String, String> movieCinemaAddressMap;

        public CinemaListAndShowtime() {
            movieShowtimesMap = new HashMap<String, ArrayList<String>>();
            movieCinemaAddressMap = new HashMap<String, String>();
        }

        public void addMovieShowtimesToMap(String cinema, String showtime, String address) {
            if(movieShowtimesMap.containsKey(cinema))
                movieShowtimesMap.get(cinema).add(showtime);
            else
                movieShowtimesMap.computeIfAbsent(cinema, k -> new ArrayList<>()).add(showtime);
            movieCinemaAddressMap.put(cinema, address);
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public HashMap<String, ArrayList<String>> getMovieShowtimesMap() {
            return movieShowtimesMap;
        }

        public void setMovieShowtimesMap(HashMap<String, ArrayList<String>> movieShowtimesMap) {
            this.movieShowtimesMap = movieShowtimesMap;
        }

        public HashMap<String, String> getMovieCinemaAddressMap() {
            return movieCinemaAddressMap;
        }

        public void setMovieCinemaAddressMap(HashMap<String, String> movieCinemaAddressMap) {
            this.movieCinemaAddressMap = movieCinemaAddressMap;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }

}