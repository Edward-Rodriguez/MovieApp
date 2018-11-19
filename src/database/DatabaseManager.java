package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.util.ArrayList;
import java.util.Date;

import model.MovieTableModel;

import java.sql.*;
import java.util.HashMap;

public class DatabaseManager {

    private static Connection conn;
    private MovieTableModel movieTableModel;
    private CinemaTableModel cinemaTableModel;

    public DatabaseManager(){
        movieTableModel = new MovieTableModel();
        cinemaTableModel = new CinemaTableModel();
    }

    //ESTABLISHES CONNECTION TO DATABASE
    //IN MY CASE, MY DATABASE NAME = CSCI370
    public void getConnection() throws Exception {
        try{
            String classpath = System.getProperty("java.class.path");
            System.out.println(classpath);

            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://csci370email.ctfatxog5vne.us-east-2.rds.amazonaws.com/CS370email?autoReconnect=true&useSSL=false";
            String username = "dbteam";
            String password = "opensesame420";
            Class.forName(driver);
            this.conn = DriverManager.getConnection(url,username,password);
        } catch(Exception e) {
            System.out.println(e); }
            retrieveMovies();
            retrieveCinemas();
        for (Cinema cinema : cinemaTableModel.getCinemas()) {
            cinema = retrieveCinemaShowtimesMovie(cinema);
        }
        for (Movie movie : movieTableModel.getMovies()) {
            movie = retrieveCinemasAndShowtimesForMovie(movie);
        }
    }

    public void createTable() throws Exception{
        try{
            //GETTING SQL STATEMENT READY FOR USE LATER
            PreparedStatement create = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS inbox(id int NOT NULL AUTO_INCREMENT, username VARCHAR(255), PRIMARY KEY(id))");
            create.executeUpdate();


        } catch(Exception e) {
            System.out.println(e);}
        finally {
            System.out.println("function completed");
        }
    }

    public void createNewMovie(
                                 String movieTitle,
                                 String rating,
                                 String releaseType,
                                 String imageURL,
                                 String summary) throws Exception{

        summary = summary.replaceAll("'", "''");
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "INSERT INTO `CS370email`.`" + "MovieList" + "` (`Title`, `Rating`, " +
                            "`ReleaseType`, `urlImage`, `Summary`) VALUES ('" + movieTitle + "','" + rating + "','" +
                            releaseType + "','" + imageURL + "','" + summary + "');");
            //POST NEW ENTRY
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void createNewCinema(
            String cinemaName,
            String address) throws Exception{
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "INSERT INTO `CS370email`.`" + "Cinemas" + "` (`cinemaName`, `Address`) VALUES ('" + cinemaName +
                            "','" + address + "');");
            //POST NEW ENTRY
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addMovieToCinema( String movieTitle, String cinemaName, String showtime) throws Exception{
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "INSERT INTO `CS370email`.`" + "movies-cinema" + "` (`movieNameID`, `cinemaNameID`, `showTimes`) VALUES ('" + movieTitle +
                            "','" + cinemaName +  "','" + showtime + "');");
            //POST NEW ENTRY
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void addCinemaMovieTypeAndRatings( String movieType, String cinemaName) throws Exception{
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "INSERT INTO `CS370email`.`" + "CinemasMovieTypes" + "` (`MovieType`,`cinemaID`) VALUES ('" + movieType +
                            "','" + cinemaName + "');");
            //POST NEW ENTRY
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateDraftMessage(String message,
                                   String recipient,
                                   String sender,
                                   String subject,
                                   int id) throws Exception{
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        message = message.replaceAll("'", "''");
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "UPDATE `CS370email`.`DraftTable` SET `Timestamp` = '" + timestamp + "', " +
                            "`Recipient` = '" + recipient + "', `Message` = '" + message + "', `Subject` = '" + subject +"' WHERE (`ID` = '" + id + "');");
            //POST NEW ENTRY
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteMovie(Movie movie) throws Exception{
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "DELETE FROM `CS370email`.`MovieList` WHERE (`Title` = '" + movie.getMovieTitle() + "');");
            //POST NEW ENTRY
            stmt.executeUpdate();
            movieTableModel.removeMovie(movie);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deleteCinema(Cinema cinema) throws Exception{
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "DELETE FROM `CS370email`.`Cinemas` WHERE (`cinemaName` = '" + cinema.getCinemaName() + "');");
            //POST NEW ENTRY
            stmt.executeUpdate();
            cinemaTableModel.removeCinema(cinema);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Cinema retrieveCinemaShowtimesMovie(Cinema cinema) {
        Cinema tempCinema = cinema;

        try {
            Statement stmt = this.conn.createStatement();

            String sql = "SELECT `showTimes`, `movieNameID`, `Rating` FROM `movies-cinema`, `MovieList` WHERE (`cinemaNameID` = '" +
                    cinema.getCinemaName() + "' AND `movieNameID` = `Title`);";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tempCinema.addMovieShowtimesToMap(
                        rs.getString("movieNameID"),
                        rs.getString("showTimes"),
                        rs.getString("Rating")
                );
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return tempCinema;
    }

    public Movie retrieveCinemasAndShowtimesForMovie(Movie movie) {
        Movie tempMovie = movie;

        try {
            Statement stmt = this.conn.createStatement();

            String sql = "SELECT `showTimes`, `cinemaNameID`, `Address` FROM `movies-cinema`, `Cinemas` " +
                    "WHERE (`cinemaNameID` = `cinemaName` AND `movieNameID` = '" + movie.getMovieTitle() + "');";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                movie.addShowtime (
                        rs.getString("cinemaNameID"),
                        rs.getString("showTimes"),
                        rs.getString("Address"));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return tempMovie;
    }

    //retrieve data from DataBase method
    public void retrieveMovies() throws Exception{
        try {
            movieTableModel.reset();
            Statement stmt = conn.createStatement();

            String sql = "SELECT `Title`, " +
                    "`Rating`, `ReleaseType`, `urlImage`, `Summary` FROM MovieList";
            ResultSet rs = stmt.executeQuery(sql);

            //System.out.println("So far so good");
            while(rs.next()){
                movieTableModel.addMovie(
                        rs.getString("Title"),
                        rs.getString("Rating"),
                        rs.getString("ReleaseType"),
                        rs.getString("urlImage"),
                        rs.getString("Summary")
                );
            }
            rs.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    //retrieve data from DataBase method
    public void retrieveCinemas() throws Exception{
        try {
            cinemaTableModel.reset();
            Statement stmt = conn.createStatement();

            String sql = "SELECT `cinemaName`, `Address` FROM Cinemas";
            ResultSet rs = stmt.executeQuery(sql);

            //System.out.println("So far so good");
            while(rs.next()){
                cinemaTableModel.addCinema(
                        rs.getString("cinemaName"),
                        rs.getString("Address")
                );
            }
            rs.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    public boolean checkIfMovieTitleExists(String movieTitle){
        int success;
        try{
            String sql = "SELECT EXISTS(SELECT 1 FROM MovieList WHERE Title = '" + movieTitle + "');";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            success = rs.getInt(1);
            if (success == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkIfCinemaExists(String cinemaName){
        int success;
        try{
            String sql = "SELECT EXISTS(SELECT 1 FROM Cinemas WHERE cinemaName = '" + cinemaName + "');";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            success = rs.getInt(1);
            if (success == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean checkIfMovieShowtimeExists(String cinemaName, String movieTitle, String showTime){
        int success;
        try{
            String sql = "SELECT EXISTS(SELECT 1 FROM `movies-cinema` WHERE showTimes = '" + showTime +
                    "' AND cinemaNameID = '" + cinemaName + "' and movieNameID = '" + movieTitle + "');";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            success = rs.getInt(1);
            if (success == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

//    public boolean isCorrectFormat(String username) {
//        // TRUNCATE DOMAIN NAME
//        String domainName;
//        String userName;
//        try {
//            domainName = username.split("@")[1];
//            userName = username.split("@")[0];
//            if (userName.equals("")) {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//        // TEST IF DOMAIN NAME IS ACCEPTABLE AND RETURN 1 IF TRUE 0 OTHERWISE
//        return checkDomainNameEligibility(domainName);
//    }

//    private boolean checkDomainNameEligibility(String userDomainName) {
//        for (int i = 0; i < AcceptableDomainNames.length; i++) {
//            if (userDomainName.equals(AcceptableDomainNames[i])) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public String[] getAcceptableDomainNames() {
//        return AcceptableDomainNames;
//    }
//
//    private void setCurrentUser (String username) {
//        currentUser = username;
//    }
//
//    public String getCurrentUser () {
//        return currentUser;
//    }
//
    public MovieTableModel getMovieTableModel() {
        return movieTableModel; }

    public CinemaTableModel getCinemaTableModel() {
        return cinemaTableModel;
    }
//
//    public MessageTableModel getDraftMessageList() {
//        return draftMessageList;
//    }
//
//    public MessageTableModel getOutboxMessageList() {
//        return outboxMessageList;
//    }
//
//    public MessageTableModel getSpamMessageList() {
//        return spamMessageList;
//    }
//
//    public void updateMessageTableModel(String tableName) {
//        messageTableModel.reset();
//        try{
//            retrieveMessages(tableName, messageTableModel);
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//    }
//
//    public void updateSpamMessageTableModel(String tableName) {
//        spamMessageList.reset();
//        try{
//            retrieveMessages(tableName, spamMessageList);
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//    }
//
//    public void updateOutboxMessageList(String tableName) {
//        outboxMessageList.reset();
//        try{
//            retrieveMessages(tableName, outboxMessageList);
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//    }
//
//    public void updateDraftMessageList() {
//        draftMessageList.reset();
//        draftMessageList.setDraftMessage(true);
//        try{
//            retrieveDraftMessages();
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//    }
}
