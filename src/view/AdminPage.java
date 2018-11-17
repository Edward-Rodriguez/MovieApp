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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    DatabaseManager db;
    CinemaTableModel cinemaTableModel;
    MovieTableModel movieTableModel;
    ObservableList<String> cinemaNameList;
    Stage window;

    private int numOfCinemas;
    private String[] listOfCinemaNames;
    TextField xCoordField;
    TextField yCoordField;

    // MOVIELIST PANE, WILL GO AT BOTTOM
    VBox movieListPane = new VBox();

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
        numOfCinemas = 0;
        getNumOfCinemas();
        listOfCinemaNames = new String[numOfCinemas];
        getNamesOfCinemas();
        cinemaArray = new CinemaShowtimesSelectionBox[numOfCinemas];
        cinemaRatings = FXCollections.observableArrayList();

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
        addMovieButton.setPadding(new Insets(0,0,0,100));

        warningLabel = new Label("Movie Title Already Exists!");
        this.add(warningLabel, 8, 3);
        warningLabel.setTextFill(Color.rgb(210, 17, 14));
        warningLabel.setVisible(false);

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

        movieListPane = new VBox();
        Separator lineSeparator = new Separator();
        lineSeparator.setPadding(new Insets(20, 0 , 0 , 0));
        Label movieListLabel = new Label("Current Movies:");
        movieListLabel.setUnderline(true);
        movieListLabel.setPadding(new Insets(5, 0, 15, 0));
        movieListLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        reloadMovieListPane();
        this.add(lineSeparator, 0, 9, 3, 1);
        this.add(movieListLabel, 1, 10, 3, 1);
        this.add(movieListPane, 0, 11, 3, 1);
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
                    db.createNewMovie(movieTitle, movieRating, movieReleaseType, urlImageOfPoster, sypnosis);
                    for (int i = 0; i < numOfCinemas; ++i) {
                        if(cinemaArray[i].getCinemaCheckBox().isSelected()) {
                            db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox1().getValue().toString());
                            db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox2().getValue().toString());
                            db.addMovieToCinema(movieTitle, listOfCinemaNames[i], cinemaArray[i].getShowtimesBox3().getValue().toString());
                            System.out.println("SUCCESS");
                            warningLabel.setText("Movie added successfully!");
                            warningLabel.setTextFill(Color.LIGHTGREEN);
                            warningLabel.setVisible(true);
                            reloadMovieListPane();
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                warningLabel.setText("Movie Title already exists!");
                warningLabel.setVisible(true);
            }
        } else {
            warningLabel.setText("Error check fields!");
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
                        } catch (Exception e) {
                            System.err.println(e);
                        }
                    }
                } else setFailureWarningMessage("Cinema name taken!");
            } else setFailureWarningMessage("Choose Coordinates b/w 0-100!");
        } else setFailureWarningMessage("Enter correct value!");
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
                    reloadMovieListPane();
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
}
