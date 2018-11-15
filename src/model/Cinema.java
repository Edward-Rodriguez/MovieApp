package model;

public class Cinema {

    private String cinemaName;
    private String address;
    private int id;

    public Cinema(String cinemaName, String address) {
        this.cinemaName = cinemaName;
        this.address = address;
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

}
