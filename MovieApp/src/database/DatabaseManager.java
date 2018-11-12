package database;

import model.MovieTableModel;

import java.util.Date;

import model.MovieTableModel;

import java.sql.*;

public class DatabaseManager {

    private static Connection conn;
    private MovieTableModel movieTableModel;

    public DatabaseManager(){
        movieTableModel = new MovieTableModel();

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

    public void createNewMovie(  int id,
                                 String movieTitle,
                                 String description,
                                 String rating,
                                 String releaseType,
                                 String location,
                                 String imageURL ) throws Exception{

        description = description.replaceAll("'", "''");
        try{
            PreparedStatement stmt = this.conn.prepareStatement(
                    "INSERT INTO `CS370email`.`" + "MovieList" + "` (`ID`, `Title`, `Description`, `Rating`, " +
                            "`ReleaseType`, `Location`, `urlImage`) VALUES ('" + id + "','" + movieTitle + "','" +
                            description + "','" + rating + "','" + releaseType + "','" + location + "','" +
                            imageURL + "');");
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

//    public void deleteDraftMessage(Message message) throws Exception{
//        try{
//            PreparedStatement stmt = this.conn.prepareStatement(
//                    "DELETE FROM `CS370email`.`DraftTable` WHERE (`ID` = '" + message.getID() + "');");
//            //POST NEW ENTRY
//            stmt.executeUpdate();
//            draftMessageList.removeMessage(message);
//        }catch (Exception e){
//            System.out.println(e);
//        }
//    }
//
//    public void deleteMessage(Message message, String table, MessageTableModel model) throws Exception{
//        try{
//            PreparedStatement stmt = this.conn.prepareStatement(
//                    "DELETE FROM `CS370email`.`" + table + "` WHERE (`ID` = '" + message.getID() + "');");
//            //POST NEW ENTRY
//            stmt.executeUpdate();
//            model.removeMessage(message);
//        }catch (Exception e){
//            System.out.println(e);
//        }
//    }

    //retrieve data from DataBase method
    public void retrieveMovies() throws Exception{
        try {
            movieTableModel.reset();
            Statement stmt = conn.createStatement();

            String sql = "SELECT `ID`, `Title`, " +
                    "`Description`, `Rating`, `ReleaseType`, `Location`, `urlImage` FROM MovieList";
            ResultSet rs = stmt.executeQuery(sql);

            //System.out.println("So far so good");
            while(rs.next()){
                movieTableModel.addMovie(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Rating"),
                        rs.getString("ReleaseType"),
                        rs.getString("Location"),
                        rs.getString("urlImage")
                );
            }
            rs.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }
    
    /*public void retrieveCinemaList(String tableName, MovieTableModel model) throws Exception{
        try {
            model.reset();
            Statement stmt = conn.createStatement();
            
            String user = "Recipient";

            String sql = "SELECT `Location` ` " +

       //             "FROM " + tableName + " WHERE " + user + " = '" + currentUser + "';";

            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("So far so good");
            while(rs.next()){
                movieTableModel.addMovie(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Rating"),
                        rs.getString("ReleaseType"),
                        rs.getString("Location"),
                        rs.getString("urlImage")
                );
            }
            rs.close();
        }
        catch (Exception e){
            System.err.println(e);
        }
    }
*/

    //retrieve data from DataBase method
//    public void retrieveDraftMessages() throws Exception{
//        try {
//            draftMessageList.reset();
//            Statement stmt = conn.createStatement();
//
//            String sql = "SELECT DATE_FORMAT(Timestamp, '%a %b %e %l:%i %p') As Time_stamp, `Sender`, `Recipient`, " +
//                    "`Subject`, `Message`, `ID` FROM DraftTable WHERE Sender = '" + currentUser + "' " +
//                    "ORDER BY `Timestamp`;";
//            ResultSet rs = stmt.executeQuery(sql);
//
//            //System.out.println("So far so good");
//            while(rs.next()){
//                draftMessageList.addMessage(
//                        rs.getString("Sender"),
//                        rs.getString("Recipient"),
//                        rs.getString("Subject"),
//                        rs.getString("Time_stamp"),
//                        rs.getString("Message"),
//                        rs.getInt("ID")
//                );
//            }
//            draftMessageList.setDraftMessage(true);
//            rs.close();
//        }
//        catch (Exception e){
//            System.err.println(e);
//        }
//    }


    // CHECK IF USER EXISTS AFTER SIGN-IN ATTEMPT AND IF CREDENTIALS ARE CORRECT
    // RETURN O OTHERWISE
//    public boolean Authenticate(String username, String password){
//        int success;
//        try{
//            String sql = "SELECT EXISTS(SELECT 1 FROM login WHERE username = '" + username +
//                    "' AND password = '" + password + "');";
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            rs.next();
//            success = rs.getInt(1);
//            if (success == 1) {
//                setCurrentUser(username);
//                retrieveMessages("Inbox", messageTableModel);
//                return true;
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        return false;
//    }

    public boolean checkIfUserNameExists(String username){
        int success;
        try{
            String sql = "SELECT EXISTS(SELECT 1 FROM login WHERE username = '" + username + "');";
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
