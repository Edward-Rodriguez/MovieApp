package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cinema {

    private String cinemaName;
    private String address;
    private int id;
    private HashMap<String, ArrayList<String>> movieShowtimesMap;
    private HashMap<String, String> movieShowtimesRatingMap;
    private ArrayList<String> releaseTypeArray;

    public Cinema(String cinemaName, String address) {
        releaseTypeArray = new ArrayList<String>();
        this.cinemaName = cinemaName;
        this.address = address;
        movieShowtimesMap = new HashMap<String, ArrayList<String>>();
        movieShowtimesRatingMap = new HashMap<String, String>();
    }

    public ArrayList<String> getReleaseTypeArray() {
        return releaseTypeArray;
    }

    public void setReleaseTypeArray(ArrayList<String> releaseTypeArray) {
        this.releaseTypeArray = releaseTypeArray;
    }

    public void addReleaseType(String releaseType) {
        releaseTypeArray.add(releaseType);
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addMovieShowtimesToMap(String movie, String showtime, String rating) {
        if(movieShowtimesMap.containsKey(movie))
            movieShowtimesMap.get(movie).add(showtime);
        else
            movieShowtimesMap.computeIfAbsent(movie, k -> new ArrayList<>()).add(showtime);
        movieShowtimesRatingMap.put(movie, rating);
    }

    public HashMap<String, ArrayList<String>> getMovieShowtimesMap() {
        return movieShowtimesMap;
    }

    public HashMap<String, String> getMovieShowtimesRatingMap() {
        return movieShowtimesRatingMap;
    }

    public void setMovieShowtimesMap(HashMap<String, ArrayList<String>> movieShowtimesMap) {
        this.movieShowtimesMap = movieShowtimesMap;
    }
}
