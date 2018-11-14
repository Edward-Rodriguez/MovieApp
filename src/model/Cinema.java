package model;

public class Cinema {

    private String cinemaName;
    private String address;
    private int id;

    public Cinema(int id, String cinemaName, String address) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
