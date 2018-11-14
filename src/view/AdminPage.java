package view;

import database.DatabaseManager;
import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    // STATIC VARIABLES
    private static final String[] ratingsList = {"G", "PG", "PG-13", "R", "NC-17", "Not Rated"};
    private static final String[] releaseTypeList = {"General", "Limited"};
    ObservableList <String> showtimesList;
    private ComboBox showtimesComboBox;
    private ComboBox showtimesComboBox2;
    private ComboBox showtimesComboBox3;

    public AdminPage(DatabaseManager db){
        this.db = db;
        cinemaTableModel = db.getCinemaTableModel();
        getListOfCinemaNames();


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
        this.add(cinemasList, 0, 7, 6, 4);

//        //ADD MOVIE BUTTON
//        addMovieButton = new Button("Add Movie");
//        this.add(addMovieButton, 1, 6, 2, 1);
//        addMovieButton.setStyle("-fx-background-color: #7BCC70;");

//        userNameWarningLabel = new Label();
//        userNameWarningLabel.setFont(new Font("Arial", 11));
//        this.add(userNameWarningLabel, 0,2, 2,1 );
//
//        nickName = new Label("Nick Name (Optional):");
//        this.add(nickName, 0, 3);
//
//        nickNameTextField = new TextField();
//        this.add(nickNameTextField, 1, 3);
//
//        invalidNicknameWarningLabel = new Label();
//        invalidNicknameWarningLabel.setText("Nickname must be less than 21 characters");
//        invalidNicknameWarningLabel.setFont(new Font("Arial", 11));
//        invalidNicknameWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//        invalidNicknameWarningLabel.setVisible(false);
//        this.add(invalidNicknameWarningLabel, 0, 4,2,1);
//
//        pw = new Label("Password:");
//        this.add(pw, 0, 5);
//
//        pwBox = new PasswordField();
//        this.add(pwBox, 1, 5);
//
//        pwWarningLabel = new Label();
//        pwWarningLabel.setFont(new Font("Arial", 11));
//        this.add(pwWarningLabel, 0, 6, 2, 1);
//
//        rpw = new Label ("Re-enter Password:");
//        this.add(rpw, 0, 7);
//
//        rpwBox = new PasswordField();
//        this.add(rpwBox, 1, 7);
//
//        registerButton = new Button("Register");
//        cancelButton = new Button("Cancel");
//        HBox hbox = new HBox(10);
//        hbox.getChildren().addAll(registerButton, cancelButton);
//        this.add(hbox, 1, 9);
//
//        incorrectLoginWarningLabel = new Label();
//        incorrectLoginWarningLabel.setVisible(false);
//        this.add(incorrectLoginWarningLabel, 0, 10, 2 ,1);
//        incorrectLoginWarningLabel.setTextFill(Color.rgb(210, 17, 14));
//
//        nonMatchWarningLabel = new Label();
//        nonMatchWarningLabel.setFont(new Font("Arial", 11));
//        this.add(nonMatchWarningLabel, 0, 8, 2, 1);

        // INITIALIZE EVENT HANDLERS
//        initEventHandlers();
    }

    private void createCinemaListCheckboxes() {
        for (Cinema cinema : cinemaTableModel.getCinemas()) {
            showtimesList = FXCollections.observableArrayList();
            showtimesList.addAll("9:00am", "9:30am", "10:00am", "10:30am",
                    "11:00am", "11:30am", "12:00pm", "12:30pm",
                    "1:00pm", "1:30pm", "2:00pm", "2:30pm",
                    "3:00pm", "3:30pm", "4:00pm", "4:30pm",
                    "5:00pm", "5:30pm", "6:00pm", "6:30pm",
                    "7:00pm", "7:30pm", "8:00pm", "8:30pm",
                    "9:00pm", "9:30pm", "10:00pm", "10:30pm",
                    "11:00pm", "11:30pm", "12:00am");
            showtimesComboBox  = new ComboBox(showtimesList);
            showtimesComboBox2  = new ComboBox(showtimesList);
            showtimesComboBox3  = new ComboBox(showtimesList);
            HBox cinemaShowtimeBox = new HBox();
            CheckBox cinemaOption = new CheckBox(cinema.getCinemaName());
            cinemaOption.setMinWidth(150);
            ComboBox tempBox = new ComboBox();
            tempBox = showtimesComboBox;
            ComboBox tempBox2 = new ComboBox();
            tempBox2 = showtimesComboBox2;
            ComboBox tempBox3 = new ComboBox();
            tempBox3 = showtimesComboBox3;

            cinemaShowtimeBox.setMinWidth(200);

            cinemaShowtimeBox.getChildren().addAll(cinemaOption, tempBox, tempBox2, tempBox3);
            cinemasList.getChildren().add(cinemaShowtimeBox);
        }
    }

    private void getListOfCinemaNames() {
        cinemaNameList = FXCollections.observableArrayList();

        for (Cinema cinema : cinemaTableModel.getCinemas()) {
            cinemaNameList.add(cinema.getCinemaName());
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
