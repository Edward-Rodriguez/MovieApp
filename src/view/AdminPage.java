package view;

import database.DatabaseManager;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
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
    ObservableList<String> cinemaNameList;

    private int numOfCinemas;
    private String[] listOfCinemaNames;
    TextField xCoordField;
    TextField yCoordField;

    // STATIC VARIABLES
    private static final String[] ratingsList = {"G", "PG", "PG-13", "R", "NC-17", "Not Rated"};
    private static final String[] releaseTypeList = {"General", "Limited"};

    CinemaShowtimesSelectionBox[] cinemaArray;


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
        generalReleaseCheckbox = new CheckBox("General");
        this.add(generalReleaseCheckbox, 5, 3);
        limitedReleaseCheckbox = new CheckBox("Limited");
        this.add(limitedReleaseCheckbox, 6, 3);
        gCheckbox = new CheckBox("G");
        this.add(gCheckbox, 5, 4);
        pgCheckbox = new CheckBox("PG");
        this.add(pgCheckbox, 6, 4);
        pg13Checkbox = new CheckBox("PG-13");
        this.add(pg13Checkbox, 5, 5);
        rCheckbox = new CheckBox("R");
        this.add(rCheckbox, 6, 5);
        nc17Checkbox = new CheckBox("NC-17");
        this.add(nc17Checkbox, 5, 6);
        notRatedCheckbox = new CheckBox("Not Rated");
        this.add(notRatedCheckbox, 6, 6);

        //ADD CINEMA BUTTON
        addCinemaButton = new Button("Add Cinema");
        this.add(addCinemaButton, 5, 7);
        addCinemaButton.setStyle("-fx-background-color: #7BCC70; -fx-padding:10;");

        cinemaWarningLabel = new Label("Cinema Already Exists!");
        this.add(cinemaWarningLabel, 6, 7);
        cinemaWarningLabel.setTextFill(Color.rgb(210, 17, 14));
        cinemaWarningLabel.setVisible(false);

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
        yCoord = Integer.parseInt(yCoordField.getText());

        if (!xCoordField.getText().isEmpty() && !yCoordField.getText().isEmpty() && !cinemaName.isEmpty()) {
            xCoord = Integer.parseInt(xCoordField.getText());
            yCoord = Integer.parseInt(yCoordField.getText());
            String cineAddress = "(" + xCoord + "," + yCoord + ")";
            if ((xCoord >= 0 && xCoord <= 100) && (yCoord >= 0 && yCoord <= 100)) {
                if (!db.checkIfCinemaExists(cinemaName)) {
                    try {
                        db.createNewCinema(cinemaName, cineAddress);
                        cinemaWarningLabel.setText("Added Cinema Successfully!");
                        cinemaWarningLabel.setTextFill(Color.rgb(17, 210, 14));
                        cinemaWarningLabel.setVisible(true);
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                }
            } else {
                cinemaWarningLabel.setText("Choose Coordinates b/w 0-100!");
                cinemaWarningLabel.setTextFill(Color.rgb(210, 17, 14));
                cinemaWarningLabel.setVisible(true);
            }
        } else {
            cinemaWarningLabel.setText("Enter correct value!");
            cinemaWarningLabel.setTextFill(Color.rgb(210, 17, 14));
            cinemaWarningLabel.setVisible(true);
        }

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
