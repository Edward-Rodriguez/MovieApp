package view;

import database.DatabaseManager;
import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Cinema;
import model.CinemaTableModel;
import model.Movie;

import java.util.Hashtable;

public class AdminPage extends GridPane {

    // LABEL FOR MOVIE EDIT SECTION
    Label movieLabel;

    // MOVIE TO ADD
    Movie movieToAdd;
    String movieTitle;
    String movieRating;
    String movieReleaseType;
    String urlImageOfPoster;
    String sypnosis;
    String ratingDescription;

    // MOVIE ADD LABELS AND COMPONENTS
    Label movieTitleLabel;
    TextField movieTitleField;
    Label ratingLabel;
    ChoiceBox<String> ratingChoiceBox;
    Label releaseTypeLabel;
    ChoiceBox<String> releaseTypeChoiceBox;
    Label urlLabel;
    TextField urlField;
    Label synopsisLabel;
    TextArea synopsisTextArea;
    Button addMovieButton;
    Label warningLabel;

    // LIST OF CINEMAS PANE AND COMPONENTS
    VBox cinemasList;
    Label cinemasPlayingAtLabel;
    ComboBox<String> showtimesChoiceBox;

    // CINEMA TO ADD AND COMPONENTS
    Cinema cinemaToAdd;
    String cinemaAddress;
    String cinemaName;
    Button addCinemaButton;

    DatabaseManager db;
    CinemaTableModel cinemaTableModel;
    ObservableList<String> cinemaNameList;

    private int numOfCinemas;
    private String[] listOfCinemaNames;

    // STATIC VARIABLES
    private static final String[] ratingsList = {"G", "PG", "PG-13", "R", "NC-17", "Not Rated"};
    private static final String[] releaseTypeList = {"General", "Limited"};
    private ComboBox showtimesComboBox;
    private ComboBox showtimesComboBox2;
    private ComboBox showtimesComboBox3;

    CinemaShowtimesSelectionBox[] cinemaArray;

    Hashtable<String, String> ratingDescriptionTable = new Hashtable<String, String>();


    public AdminPage(DatabaseManager db){
        this.db = db;
        cinemaTableModel = db.getCinemaTableModel();
        numOfCinemas = 0;
        getNumOfCinemas();
        listOfCinemaNames = new String[numOfCinemas];
        getNamesOfCinemas();
        cinemaArray = new CinemaShowtimesSelectionBox[numOfCinemas];

        this.setHgap(10);
        this.setVgap(5);
        this.setPadding(new Insets(25, 25, 25, 25));

        // HEADING 'MOVIES'
        movieLabel = new Label("Add Movies");
        movieLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(movieLabel, 0, 0, 2, 1);

        // MOVIE TITLE LABEL & FIELD
        movieTitleLabel = new Label("Title:");
        this.add(movieTitleLabel, 0, 1);
        movieTitleField = new TextField();
        this.add(movieTitleField, 1, 1);

        // RATING LABEL & CHOICEBOX
        ratingLabel = new Label("Rating:");
        this.add(ratingLabel, 0, 2);
        ratingChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
                ratingsList
        ));
        ratingChoiceBox.getSelectionModel().selectFirst();
        this.add(ratingChoiceBox,1,2);

        // RELEASETYPE LABEL AND CHOICEBOX
        releaseTypeLabel = new Label("Release Type:");
        this.add(releaseTypeLabel, 0, 3);
        releaseTypeChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
                releaseTypeList
        ));
        releaseTypeChoiceBox.setPrefWidth(70);
        releaseTypeChoiceBox.getSelectionModel().selectFirst();
        this.add(releaseTypeChoiceBox,1,3);

        // URL LABEL & FIELD
        urlLabel = new Label("URL(Poster Image):");
        this.add(urlLabel, 0, 4);
        urlField = new TextField();
        urlField.setPromptText("Optional");
        this.add(urlField, 1, 4);

        // SYNOPSIS LABEL & FIELD
        synopsisLabel = new Label("Synopsis:");
        this.add(synopsisLabel, 0, 5);
        synopsisTextArea = new TextArea();
        synopsisTextArea.setPromptText("Optional");
        this.add(synopsisTextArea, 1, 5);

        // CINEMA & SHOWTIMES SELECTION
        cinemasPlayingAtLabel = new Label("Select Cinemas and Showtimes:");
        this.add(cinemasPlayingAtLabel, 0, 6, 2, 1);

        cinemasList = new VBox(5);
        createCinemaListCheckboxes();
        this.add(cinemasList, 0, 7, 6, 1);

        //ADD MOVIE BUTTON
        addMovieButton = new Button("Add Movie");
        this.add(addMovieButton, 1, 8, 2, 1);
        addMovieButton.setStyle("-fx-background-color: #7BCC70; -fx-padding:10;");

        warningLabel = new Label("Movie Title Already Exists!");
        this.add(warningLabel, 8, 3);
        warningLabel.setTextFill(Color.rgb(210, 17, 14));
        warningLabel.setVisible(false);

        initEventHandlers();
    }

    private void addMovie() {
        movieTitle = movieTitleField.getText();
        movieRating = ratingChoiceBox.getValue();
        movieReleaseType = releaseTypeChoiceBox.getValue();
        urlImageOfPoster = urlField.getText();
        sypnosis = synopsisTextArea.getText();



        if (movieTitle != null && movieRating != null && movieReleaseType != null) {

            if (!db.checkIfMovieTitleExists(movieTitle)) {
                try {
                    db.createNewMovie(movieTitle, movieRating, movieReleaseType, urlImageOfPoster, sypnosis);
                    for (int i = 0; i < numOfCinemas; ++i) {
                        if(cinemaArray[i].getCinemaCheckBox().isSelected()) {
                            db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox1().getValue().toString());
                            db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox2().getValue().toString());
                            db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox3().getValue().toString());
                            System.out.println("SUCCESS");
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                warningLabel.setVisible(true);
            }
        } else {
            warningLabel.setText("Error check fields!");
            warningLabel.setVisible(true);
        }
    }

    private void initEventHandlers() {
        addMovieButton.setOnAction(e -> {
            addMovie();
        });
    }

    private void createCinemaListCheckboxes() {
        for (int i = 0; i < numOfCinemas; ++i) {
            cinemaArray[i] = new CinemaShowtimesSelectionBox(listOfCinemaNames[i]);
            cinemasList.getChildren().add(cinemaArray[i]);
        }
    }

    private void getNumOfCinemas() {
        for (Cinema cinema : cinemaTableModel.getCinemas()) {
                numOfCinemas++;
        }
    }

    private void getNamesOfCinemas() {
        int i = 0;
        for (Cinema cinema : cinemaTableModel.getCinemas()) {
                listOfCinemaNames[i++] = cinema.getCinemaName();
        }
    }



//    private void initEventHandlers() {
//        registerButton.setOnAction(v ->{
//            if (validPassword != null && validUsername != null &&
//                    validPassword.equals(reEnteredPassword) && nickNameisValid) {
//                try {
//                    db.CreateNewUser(validUsername, validPassword, validNickName);
//                    incorrectLoginWarningLabel.setText("Account Created Successfully! " +
//                            "You may now Login \nWindow closing in 3 seconds...");
//                    registerButton.setDisable(true);
//                    cancelButton.setDisable(true);
//                    incorrectLoginWarningLabel.setTextFill(Color.rgb(0, 117, 0));
//                    incorrectLoginWarningLabel.setVisible(true);
//                    delay.setOnFinished(e -> this.close());
//                    delay.play();
//                } catch (Exception e) {
//                    System.err.println(e);
//                }
//            } else
//            {
//                incorrectLoginWarningLabel.setText("Account Creation Unsuccesful! \nCheck Username/Password");
//                incorrectLoginWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//                incorrectLoginWarningLabel.setVisible(true);
//            }
//        });
//
//        cancelButton.setOnAction(n->{
//            this.close();
//        });
//
//        cb.getSelectionModel().selectedItemProperty().addListener
//                ( (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//                    tempDomainName = "@" + newValue;
//
//                    // NEXT FEW LINES ARE TO 'REFRESH' TEXTFIELD AFTER CHOOSING FROM DROP DOWN
//                    String oldUsername = userNameTextField.getText();
//                    userNameTextField.setText( oldUsername + " ");
//                    userNameTextField.setText(  oldUsername );
//                });
//
//        userNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            tempDomainName = "@" + cb.getValue();
//
//            if(newValue.contains(" ")) {
//                userNameWarningLabel.setText("Sorry no spaces allowed!");
//                userNameWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//                validUsername = null;
//            } else if(!newValue.matches("^(?!\\.)(?!.*\\.$)(?!.*?\\.\\.)[a-zA-Z0-9_.]+$")) {
//                userNameWarningLabel.setText("Invalid characters");
//                userNameWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//                validUsername = null;
//            } else if(newValue.length() > 20 ) {
//                userNameWarningLabel.setText("Username must be a maximum of 20 characters");
//                userNameWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//                validUsername = null;
//            } else if (!db.isCorrectFormat(newValue + tempDomainName)) {
//                userNameWarningLabel.setText("Username must contain a valid domain name");
//                userNameWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//                validUsername = null;
//            } else if (db.checkIfUserNameExists(newValue + tempDomainName)) {
//                userNameWarningLabel.setText("Username already exists");
//                userNameWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//                validUsername = null;
//            } else {
//                userNameWarningLabel.setText("Username available");
//                userNameWarningLabel.setTextFill(Color.rgb(0, 117, 0));
//                validUsername = newValue + tempDomainName;
//            }
//        });
//
//
//        nickNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue.length() > 20) {
//                invalidNicknameWarningLabel.setVisible(true);
//                validNickName = null;
//                nickNameisValid = false;
//            } else {
//                invalidNicknameWarningLabel.setVisible(false);
//                validNickName = newValue;
//                nickNameisValid = true;
//            }
//        });
//
//        pwBox.textProperty().addListener((observable, oldValue, newValue) -> {
//            if(newValue.contains(" ")) {
//                pwWarningLabel.setText("Oops nice try! No spaces allowed!");
//                pwWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//            } else if(newValue.length() < 4 ) {
//                pwWarningLabel.setText("Password must have at least 4 characters");
//                pwWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//            } else if (newValue.length() > 12){
//                pwWarningLabel.setText("Password must be less than 13 characters");
//                pwWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//            }
//            else {
//                pwWarningLabel.setText("Password meets criteria");
//                pwWarningLabel.setTextFill(Color.rgb(0, 117, 0));
//                validPassword = newValue;
//            }
//        });
//
//        rpwBox.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (!newValue.equals(pwBox.getText())) {
//                nonMatchWarningLabel.setText("Passwords do not match");
//                nonMatchWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//                reEnteredPassword = null;
//            } else {
//                reEnteredPassword = newValue;
//                nonMatchWarningLabel.setText("Passwords match");
//                nonMatchWarningLabel.setTextFill(Color.rgb(0, 117, 14));
//            }
//        });
//    }
}
