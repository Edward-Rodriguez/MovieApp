package view;

import database.DatabaseManager;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Cinema;
import model.CinemaTableModel;
import model.Movie;
import model.MovieTableModel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

import static model.StartupConstants.CSS_CLASS_ADMIN_DELETE_BUTTON;

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
    int xCoord;
    int yCoord;

    // CINEMA LABELS AND COMPONENTS
    Label cinemaHeader;
    Label cinemaNameLabel;
    TextField cinemaNameField;
    Label addressLabel;
    TextField addressField;
    Button addCinemaButton;
    Label movieTypesPlayingAtCinema;
    VBox movieTypesCheckboxPane;
    CheckBox generalReleaseCheckbox;
    CheckBox limitedReleaseCheckbox;
    CheckBox gCheckbox;
    CheckBox pgCheckbox;
    CheckBox pg13Checkbox;
    CheckBox rCheckbox;
    CheckBox nc17Checkbox;
    CheckBox notRatedCheckbox;
    Label cinemaWarningLabel;

    // ADD SHOWTIMES LABELS/DROPDOWNS/PANE
    Label addShowtimesLabel;
    ChoiceBox<String> movieListChoiceBox;
    ChoiceBox<String> cinemaListChoiceBox;
    ComboBox<String> timeListComboBox;
    ObservableList<String> showtimeList;
    Button addShowtimeButton;
    private String showtime;
    private String movieSelection;
    private String cinemaSelection;
    Label showtimeWarningLabel;
    HBox movieCinemaDropdownPane;

    DatabaseManager db;
    CinemaTableModel cinemaTableModel;
    MovieTableModel movieTableModel;
    ObservableList<String> cinemaNameList;
    Stage window;

    private int numOfCinemas;
    private int numOfMovies;
    private String[] listOfCinemaNames;
    private String[] listOfMovieTitles;
    TextField xCoordField;
    TextField yCoordField;

    // MOVIELIST PANE, WILL GO AT BOTTOM
    VBox movieListPane;

    // MOVIELIST PANE, WILL GO AT BOTTOM
    VBox cinemaListPane;

    // STATIC VARIABLES
    private static final String G = "G";
    private static final String PG = "PG";
    private static final String PG13 = "PG-13";
    private static final String R = "R";
    private static final String NC17 = "NC-17";
    private static final String NOT_RATED = "Not Rated";
    private static final String GENERAL = "General";
    private static final String LIMITED = "Limited";
    private static final String[] ratingsList = {G, PG, PG13, R, NC17, NOT_RATED};
    private static final String[] releaseTypeList = {GENERAL, LIMITED};
    ObservableList<String> cinemaRatings;
    CinemaShowtimesSelectionBox[] cinemaArray;

    public AdminPage(DatabaseManager db){
        this.db = db;
        cinemaTableModel = db.getCinemaTableModel();
        movieTableModel = db.getMovieTableModel();
        getNamesOfCinemas();
        getTitlesOfMovies();
        cinemaArray = new CinemaShowtimesSelectionBox[numOfCinemas];
        cinemaRatings = FXCollections.observableArrayList();
        Separator lineSeparator = new Separator();
        lineSeparator.setPadding(new Insets(20, 0 , 0 , 0));

        this.setHgap(10);
        this.setVgap(5);
        this.setPadding(new Insets(25, 25, 25, 25));
        setGridpaneConstraints();

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
        synopsisLabel.setAlignment(Pos.TOP_LEFT);
        synopsisTextArea = new TextArea();
        synopsisTextArea.setPromptText("Optional");
        synopsisTextArea.setWrapText(true);
        synopsisTextArea.setPrefRowCount(3);
        this.add(synopsisTextArea, 1, 5);

        // CINEMA & SHOWTIMES SELECTION
        cinemasPlayingAtLabel = new Label("Select Cinemas and Showtimes:");
        this.add(cinemasPlayingAtLabel, 0, 6, 2, 1);

        cinemasList = new VBox(5);
        createCinemaListCheckboxes();
        this.add(cinemasList, 0, 7, 6, 1);

        //ADD MOVIE BUTTON
        addMovieButton = new Button("Add Movie");
        this.add(addMovieButton, 0, 8, 1, 1);
        addMovieButton.setStyle("-fx-background-color: #7BCC70; -fx-padding:10;");
        addMovieButton.setPadding(new Insets(0,0,20,100));

        warningLabel = new Label("Movie Title Already Exists!");
        this.add(warningLabel, 1, 8);
        warningLabel.setTextFill(Color.rgb(210, 17, 14));
        warningLabel.setVisible(false);


        /**********************ADD SHOWTIMES PANE BELOW*******************/
        VBox addshowTimesPane = new VBox(10);
        movieCinemaDropdownPane = new HBox();
        addShowtimesLabel = new Label("Add Showtimes");
        addShowtimesLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        addShowtimesLabel.setPadding(new Insets(10, 0, 5, 0));

        movieListChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
                listOfMovieTitles));
        movieListChoiceBox.getSelectionModel().selectFirst();
        cinemaListChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
                listOfCinemaNames));
        cinemaListChoiceBox.getSelectionModel().selectFirst();
        showtimeList = FXCollections.observableArrayList();
        showtimeList.addAll("9:00am", "9:30am", "10:00am", "10:30am",
                "11:00am", "11:30am", "12:00pm", "12:30pm",
                "1:00pm", "1:30pm", "2:00pm", "2:30pm",
                "3:00pm", "3:30pm", "4:00pm", "4:30pm",
                "5:00pm", "5:30pm", "6:00pm", "6:30pm",
                "7:00pm", "7:30pm", "8:00pm", "8:30pm",
                "9:00pm", "9:30pm", "10:00pm", "10:30pm",
                "11:00pm", "11:30pm", "12:00am");
        timeListComboBox = new ComboBox<>(showtimeList);
        timeListComboBox.getSelectionModel().selectFirst();
        addShowtimeButton = new Button("Add Showtime");
        addShowtimeButton.setStyle("-fx-background-color: #7BCC70; -fx-padding:10;");
        showtimeWarningLabel = new Label();
        showtimeWarningLabel.setVisible(false);
        Separator sep = new Separator();
        sep.setPadding(new Insets(20, 0 , 0 , 0));
        movieCinemaDropdownPane.getChildren().addAll(movieListChoiceBox, cinemaListChoiceBox, timeListComboBox);
        HBox buttonPane = new HBox(10);
        buttonPane.getChildren().addAll(addShowtimeButton, showtimeWarningLabel);
        addshowTimesPane.getChildren().addAll(sep,addShowtimesLabel, movieCinemaDropdownPane, buttonPane);
        this.add(addshowTimesPane, 0,9,2,1);


        /**********************CINEMA CONFIGURATION BELOW*******************/
        // HEADING 'CINEMAS'
        cinemaHeader = new Label("Add Cinemas");
        cinemaHeader.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(cinemaHeader, 4, 0, 2, 1);

        // CINEMA NAME LABEL & FIELD
        cinemaNameLabel = new Label("Cinema Name:");
        this.add(cinemaNameLabel, 4, 1);
        cinemaNameField = new TextField();
        this.add(cinemaNameField, 5, 1);

        // CINEMA ADDRESS LABEL & SPINNERS FOR COORDINATES
        addressLabel = new Label("Address:");
        this.add(addressLabel, 4, 2);

        Label xLabel = new Label("X:");
        Label yLabel = new Label("Y:");
        xCoordField = new TextField();
        yCoordField = new TextField();
        xCoordField.setPromptText("0-100");
        yCoordField.setPromptText("0-100");
        xCoordField.setPrefWidth(50);
        yCoordField.setPrefWidth(50);

        HBox xyCoordinateBox = new HBox(5);
        xyCoordinateBox.getChildren().addAll(xLabel,xCoordField, yLabel,yCoordField);

        this.add(xyCoordinateBox, 5, 2);

        // CINEMA MOVIE TYPES
        movieTypesPlayingAtCinema = new Label("Movie Types:");
        this.add(movieTypesPlayingAtCinema, 4, 3);
        generalReleaseCheckbox = new CheckBox(GENERAL);
        this.add(generalReleaseCheckbox, 5, 3);
        limitedReleaseCheckbox = new CheckBox(LIMITED);
        this.add(limitedReleaseCheckbox, 6, 3);
        gCheckbox = new CheckBox(G);
        this.add(gCheckbox, 5, 4);
        pgCheckbox = new CheckBox(PG);
        this.add(pgCheckbox, 6, 4);
        pg13Checkbox = new CheckBox(PG13);
        this.add(pg13Checkbox, 5, 5);
        rCheckbox = new CheckBox(R);
        this.add(rCheckbox, 6, 5);
        nc17Checkbox = new CheckBox(NC17);
        this.add(nc17Checkbox, 5, 6);
        notRatedCheckbox = new CheckBox(NOT_RATED);
        this.add(notRatedCheckbox, 6, 6);

        //ADD CINEMA BUTTON
        addCinemaButton = new Button("Add Cinema");
        this.add(addCinemaButton, 5, 7);
        addCinemaButton.setStyle("-fx-background-color: #7BCC70; -fx-padding:10;");

        cinemaWarningLabel = new Label("Cinema Already Exists!");
        this.add(cinemaWarningLabel, 6, 7);
        cinemaWarningLabel.setTextFill(Color.rgb(210, 17, 14));
        cinemaWarningLabel.setVisible(false);


        /***********************MOVIE LIST (DELETABLE)**********************/
        movieListPane = new VBox();
        Label movieListLabel = new Label("Current Movies:");
        movieListLabel.setUnderline(true);
        movieListLabel.setPadding(new Insets(5, 0, 15, 0));
        movieListLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        reloadMovieListPane();
        this.add(lineSeparator, 0, 10, 3, 1);
        this.add(movieListLabel, 1, 11, 3, 1);
        this.add(movieListPane, 0, 12, 3, 1);


        /***********************CINEMA LIST (DELETABLE)**********************/
        cinemaListPane = new VBox();
        Label cinemaListLabel = new Label("Current Cinemas:");
        cinemaListLabel.setUnderline(true);
        cinemaListLabel.setPadding(new Insets(5, 0, 15, 0));
        cinemaListLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        reloadCinemaListPane();
        Separator sep3 = new Separator();
        sep3.setPadding(new Insets(20, 0 , 0 , 0));
        this.add(sep3, 4, 10, 3, 1);
        this.add(cinemaListLabel, 5, 11, 3, 1);
        this.add(cinemaListPane, 4, 12, 3, 1);

        initEventHandlers();
    }

    private void addMovie() {
        movieTitle = movieTitleField.getText();
        movieRating = ratingChoiceBox.getValue();
        movieReleaseType = releaseTypeChoiceBox.getValue();
        sypnosis = synopsisTextArea.getText();
        urlImageOfPoster = urlField.getText();

        if (urlImageOfPoster.isEmpty() || checkIfURLExists(urlImageOfPoster) == false) {
            // then set to default picture
            urlImageOfPoster = "img/posterNA.jpg";
        }
        if (movieTitle != null && movieRating != null && movieReleaseType != null) {
            if (!db.checkIfMovieTitleExists(movieTitle)) {
                try {
                    Movie tempMovie = new Movie(movieTitle, movieRating, movieReleaseType, urlImageOfPoster, sypnosis);
                    db.createNewMovie(movieTitle, movieRating, movieReleaseType, urlImageOfPoster, sypnosis);
                    if (validShowtimeSelections(movieTitle)) {
                        for (int i = 0; i < numOfCinemas; ++i) {
                            if (cinemaArray[i].getCinemaCheckBox().isSelected()) {
                                if (cinemaArray[i].getShowtimesBox1().getValue() != null)
                                db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox1().getValue().toString());
                                if (cinemaArray[i].getShowtimesBox2().getValue() != null)
                                db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox2().getValue().toString());
                                if (cinemaArray[i].getShowtimesBox3().getValue() != null)
                                db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox3().getValue().toString());
                                System.out.println("SUCCESS");
                                warningLabel.setText("Movie added successfully!");
                                warningLabel.setTextFill(Color.LIGHTGREEN);
                                warningLabel.setVisible(true);
                                updateMovieUIComponents();
                                }
                            }
                        } else db.deleteMovie(tempMovie);
                    } catch (Exception e) {
                        System.err.println(e);
                    }

            } else {
                warningLabel.setText("Movie Title already exists!");
                warningLabel.setTextFill(Color.RED);
                warningLabel.setVisible(true);
            }
        } else {
            warningLabel.setText("Error check fields!");
            warningLabel.setTextFill(Color.RED);
            warningLabel.setVisible(true);
        }
    }

    private void addCinema() {
        cinemaName = cinemaNameField.getText();

        if (!xCoordField.getText().isEmpty() && !yCoordField.getText().isEmpty() && !cinemaName.isEmpty()) {
            xCoord = Integer.parseInt(xCoordField.getText());
            yCoord = Integer.parseInt(yCoordField.getText());
            String cineAddress = "(" + xCoord + "," + yCoord + ")";
            if ((xCoord >= 0 && xCoord <= 100) && (yCoord >= 0 && yCoord <= 100)) {
                if (!db.checkIfCinemaExists(cinemaName)) {
                    if (emptyCheckedRatingBoxes())setFailureWarningMessage("Choose Movie Type/Rating!");
                    else {
                        try {
                            db.createNewCinema(cinemaName, cineAddress);
                            for (String rating : cinemaRatings) {
                                db.addCinemaMovieTypeAndRatings(rating, cinemaName);
                            }
                            setSuccessWarningMessage("Added Cinema Successfully!");
                            updateCinemaUIComponents();
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                } else setFailureWarningMessage("Cinema name taken!");
            } else setFailureWarningMessage("Choose Coordinates b/w 0-100!");
        } else setFailureWarningMessage("Error check fields!");
    }

    private void updateCinemaUIComponents() {
        try {
            db.retrieveCinemas();
        } catch (Exception e) {
            System.err.println(e);
        }
        cinemaTableModel = db.getCinemaTableModel();
        cinemasList.getChildren().clear();
        createCinemaListCheckboxes();
        reloadCinemaListPane();
        cinemaListChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
                listOfCinemaNames));
        cinemaListChoiceBox.getSelectionModel().selectFirst();
        movieCinemaDropdownPane.getChildren().clear();
        movieCinemaDropdownPane.getChildren().addAll(movieListChoiceBox, cinemaListChoiceBox, timeListComboBox);
    }

    private void updateMovieUIComponents() {
        try {
            db.retrieveMovies();
        } catch (Exception e) {
            System.err.println(e);
        }
        movieTableModel = db.getMovieTableModel();
        reloadMovieListPane();
        getTitlesOfMovies();
        movieListChoiceBox = new ChoiceBox<String>(FXCollections.observableArrayList(
                listOfMovieTitles));
        movieListChoiceBox.getSelectionModel().selectFirst();
        movieCinemaDropdownPane.getChildren().clear();
        movieCinemaDropdownPane.getChildren().addAll(movieListChoiceBox, cinemaListChoiceBox, timeListComboBox);
    }

    private void addShowtime() {
        movieSelection = movieListChoiceBox.getValue();
        cinemaSelection = cinemaListChoiceBox.getValue();
        showtime = timeListComboBox.getValue();

        if (!db.checkIfMovieShowtimeExists(cinemaSelection, movieSelection, showtime)) {
            try {
                db.addMovieToCinema(movieSelection, cinemaSelection, showtime);
                showtimeWarningLabel.setText("Showtime added!");
                showtimeWarningLabel.setTextFill(Color.DARKGREEN);
                showtimeWarningLabel.setVisible(true);
            } catch (Exception e) {
                System.err.println(e);
            }
        } else {
            showtimeWarningLabel.setText("Showtime already exists!");
            showtimeWarningLabel.setTextFill(Color.RED);
            showtimeWarningLabel.setVisible(true);
        }
    }

    // RETURN TRUE IF NON OF THE CHECKBOXES WERE SELECTED
    private boolean emptyCheckedRatingBoxes() {
        cinemaRatings.clear();
        boolean nonSelected = true;
        if (generalReleaseCheckbox.isSelected()) { cinemaRatings.add(GENERAL); nonSelected = false; }
        if (limitedReleaseCheckbox.isSelected()) { cinemaRatings.add(LIMITED); nonSelected = false; }
        if (gCheckbox.isSelected()) { cinemaRatings.add(G); nonSelected = false; }
        if (pgCheckbox.isSelected()) { cinemaRatings.add(PG); nonSelected = false; }
        if (pg13Checkbox.isSelected()) { cinemaRatings.add(PG13); nonSelected = false; }
        if (rCheckbox.isSelected()) { cinemaRatings.add(R); nonSelected = false; }
        if (nc17Checkbox.isSelected()) { cinemaRatings.add(NC17); nonSelected = false; }
        if (notRatedCheckbox.isSelected()) { cinemaRatings.add(NOT_RATED); nonSelected = false; }
        return nonSelected;
    }

    // CHECK IF MOVIE CHECKBOXES ARE CHECKED AND SHOWTIME ARE NOT ALREADY IN DB
    private boolean validShowtimeSelections(String movieName) {
       boolean isValid = false;

       // FIRST CHECK IF CINEMA CHECKBOXES ARE CHECKED
        for (int i = 0; i < numOfCinemas; ++i) {
            if(cinemaArray[i].getCinemaCheckBox().isSelected()) {
                if (cinemaArray[i].getShowtimesBox1().getValue() == null &&
                    cinemaArray[i].getShowtimesBox2().getValue() == null &&
                    cinemaArray[i].getShowtimesBox3().getValue() == null)
                {
                    warningLabel.setText("Select 1 or more showtimes!");
                    warningLabel.setTextFill(Color.RED);
                    warningLabel.setVisible(true);
                    return false;
                } else {
                    if(cinemaArray[i].getShowtimesBox1().getValue() != null) {
                        if (db.checkIfMovieShowtimeExists(listOfCinemaNames[i], movieName, cinemaArray[i].getShowtimesBox1().getValue().toString())) { ;
                            warningLabel.setText("One or more showtimes already exists!");
                            warningLabel.setTextFill(Color.RED);
                            warningLabel.setVisible(true);
                            return false;
                        }  else isValid = true;
                    }
                    if(cinemaArray[i].getShowtimesBox2().getValue() != null) {
                        if(db.checkIfMovieShowtimeExists(listOfCinemaNames[i], movieName, cinemaArray[i].getShowtimesBox2().getValue().toString()))  {
                            warningLabel.setText("One or more showtimes already exists!");
                            warningLabel.setTextFill(Color.RED);
                            warningLabel.setVisible(true);
                            return false;
                        } else isValid = true;
                    }
                    if(cinemaArray[i].getShowtimesBox3().getValue() != null) {
                        if(db.checkIfMovieShowtimeExists(listOfCinemaNames[i], movieName, cinemaArray[i].getShowtimesBox3().getValue().toString()))  {
                            warningLabel.setText("One or more showtimes already exists!");
                            warningLabel.setTextFill(Color.RED);
                            warningLabel.setVisible(true);
                            return false;
                        } else isValid = true;
                    }
                }
            }

        }
        return isValid;
    }

    private void setFailureWarningMessage(String message) {
        cinemaWarningLabel.setText(message);
        cinemaWarningLabel.setTextFill(Color.rgb(210, 17, 14));
        cinemaWarningLabel.setVisible(true);
    }

    private void setSuccessWarningMessage(String message) {
        cinemaWarningLabel.setText(message);
        cinemaWarningLabel.setTextFill(Color.rgb(17, 210, 14));
        cinemaWarningLabel.setVisible(true);
    }

    private void initEventHandlers() {
        // force the coordinates field to be numeric only
        xCoordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("(100)|(0*\\d{1,2})")) {
                    xCoordField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        yCoordField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("(100)|(0*\\d{1,2})")) {
                    yCoordField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        addMovieButton.setOnAction(e -> {
            addMovie();
        });
        addCinemaButton.setOnAction(e -> {
            addCinema();
        });
        addShowtimeButton.setOnAction(e -> {
            addShowtime();
        });
    }

    private void createCinemaListCheckboxes() {
        getNamesOfCinemas();
        cinemaArray = new CinemaShowtimesSelectionBox[numOfCinemas];
        for (int i = 0; i < numOfCinemas; ++i) {
            cinemaArray[i] = new CinemaShowtimesSelectionBox(listOfCinemaNames[i]);
            cinemasList.getChildren().add(cinemaArray[i]);
        }
    }

    private void getNumOfCinemas() {
        numOfCinemas = 0;
        for (Cinema cinema : cinemaTableModel.getCinemas()) {
                numOfCinemas++;
        }
    }

    private void getNumOfMovies() {
        numOfMovies = 0;
        for (Movie movie : movieTableModel.getMovies()) {
            numOfMovies++;
        }
    }

    private void getNamesOfCinemas() {
        getNumOfCinemas();
        listOfCinemaNames = new String[numOfCinemas];
        int i = 0;
        for (Cinema cinema : cinemaTableModel.getCinemas()) {
                listOfCinemaNames[i++] = cinema.getCinemaName();
        }
    }

    private void getTitlesOfMovies() {
        getNumOfMovies();
        int i = 0;
        listOfMovieTitles = new String[numOfMovies];
        for (Movie movie : movieTableModel.getMovies()) {
            listOfMovieTitles[i++] = movie.getMovieTitle();
        }
    }

    // CHECK IF URL EXISTS
    private boolean checkIfURLExists(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int rc = conn.getResponseCode();
            conn.disconnect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // CREATE LIST OF MOVIES WITH DELETE OPTION
    private void reloadMovieListPane() {
        movieListPane.getChildren().clear();
        for (Movie movie : movieTableModel.getMovies()) {
            SingleMovieContainerView movieListEditor = new SingleMovieContainerView(movie);
            if(movieTableModel.isSelectedMovie(movie)) {
                movieListEditor.getDeleteButton().setVisible(true);
            }
            movieListPane.getChildren().add(movieListEditor);

            // EVENT HANDLERS
            movieListEditor.setOnMouseClicked(e -> {
                movieTableModel.setSelectedMovie(movie);
            });
            movieListEditor.setOnMouseEntered(e -> {
                movieListEditor.getDeleteButton().setVisible(true);
            });
            movieListEditor.setOnMouseExited(e -> {
                movieListEditor.getDeleteButton().setVisible(false);
            });
            movieListEditor.getDeleteButton().setOnAction(e -> {
                try{
                    db.deleteMovie(movie);
                    updateMovieUIComponents();
                } catch (Exception event) {
                    System.err.println(event);
                }
            });
        }
    }

    // CREATE LIST OF MOVIES WITH DELETE OPTION
    private void reloadCinemaListPane() {
        cinemaListPane.getChildren().clear();
        for (Cinema cinema : cinemaTableModel.getCinemas()) {
            SingleCinemaContainerView cinemaListEditor = new SingleCinemaContainerView(cinema);
            if(cinemaTableModel.isSelectedCinema(cinema)) {
                cinemaListEditor.getDeleteButton().setVisible(true);
            }
            cinemaListPane.getChildren().add(cinemaListEditor);

            // EVENT HANDLERS
            cinemaListEditor.setOnMouseClicked(e -> {
                cinemaTableModel.setSelectedCinema(cinema);
            });
            cinemaListEditor.setOnMouseEntered(e -> {
                cinemaListEditor.getDeleteButton().setVisible(true);
            });
            cinemaListEditor.setOnMouseExited(e -> {
                cinemaListEditor.getDeleteButton().setVisible(false);
            });
            cinemaListEditor.getDeleteButton().setOnAction(e -> {
                try{
                    db.deleteCinema(cinema);
                    updateCinemaUIComponents();
                } catch (Exception event) {
                    System.err.println(event);
                }
            });
        }
    }

    // SET WIDTH TO EACH COLUMN OF THIS PANE
    private void setGridpaneConstraints() {
        GridPane gridpane = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMaxWidth(160);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setMaxWidth(300);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setMaxWidth(130);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setMaxWidth(200);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setMaxWidth(100);
        this.getColumnConstraints().addAll(col1,col2,col3,col4,col5);
    }

    // INER CLASS FOR MOVIE LIST VIEW
    private class SingleCinemaContainerView extends HBox{
        private Cinema cinema;
        private Button deleteButton;
        private Image imageDelete;
        private Label cinemaName;

        public SingleCinemaContainerView(Cinema cinema) {
            this.cinema = cinema;

            // CINEMA NAME
            cinemaName = new Label(this.cinema.getCinemaName());
            cinemaName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            cinemaName.setPrefWidth(200);

            imageDelete = new Image(getClass().getResourceAsStream("../img/trash.png"));
            deleteButton = new Button();
            deleteButton.setPadding(Insets.EMPTY);
            deleteButton.setGraphic(new ImageView(imageDelete));
            deleteButton.setVisible(false);

            Region spacer = new Region();

            HBox.setHgrow(spacer, Priority.ALWAYS);
            spacer.setMaxWidth(150);

            deleteButton.getStyleClass().add(CSS_CLASS_ADMIN_DELETE_BUTTON);

            getChildren().addAll(cinemaName, spacer, deleteButton);
            this.setStyle("-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #D1EEEE;");
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }
}
