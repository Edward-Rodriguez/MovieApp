package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cinema {

    private String cinemaName;
    private String address;
    private int id;
    private HashMap<String, ArrayList<String>> movieShowtimesMap;

    public Cinema(String cinemaName, String address) {
        this.cinemaName = cinemaName;
        this.address = address;
        movieShowtimesMap = new HashMap<String, ArrayList<String>>();
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

    public void addMovieShowtimesToMap(String movie, String showtime) {
        if(movieShowtimesMap.containsKey(movie))
            movieShowtimesMap.get(movie).add(showtime);
        else
            movieShowtimesMap.computeIfAbsent(movie, k -> new ArrayList<>()).add(showtime);
    }

    public HashMap<String, ArrayList<String>> getMovieShowtimesMap() {
        return movieShowtimesMap;
    }

    public void setMovieShowtimesMap(HashMap<String, ArrayList<String>> movieShowtimesMap) {
        this.movieShowtimesMap = movieShowtimesMap;
    }
}
