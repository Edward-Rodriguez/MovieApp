package view;

import controller.MovieController;
import database.DatabaseManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Movie;
import model.MovieTableModel;

import static model.StartupConstants.*;
/**
 * This class provides the User Interface for this application,
 * providing controls and the entry points for creating, loading,
 * viewing movie posters.
 *
 * @author Edward Rodriguez, Raymond Calapatia, Sukharam Gole, Fasih Uddin
 */
public class MovieAppView {
    // LIST OF MOVIES FROM DB
    MovieTableModel movieList;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    VBox rootPane;

    // WORKSPACE
    VBox maPane;

    // THIS WILL GO AT THE TOP OF SCREEN
    VBox headerPane;

    // FILTER PANE AND COMPONENTS
    HBox filterBox;
    Label filterLabel;
    CheckBox allCheckBox;
    CheckBox pgRatingCheckBox;
    CheckBox pg13RatingCheckBox;
    CheckBox rRatingCheckBox;
    CheckBox nc17RatingCheckBox;
    CheckBox gRatingCheckBox;

    // POSTER LISTING SPACE (CENTER)
    FlowPane movieListPane;

    // THIS WILL ENCAPSULATE WORKSPACE TO ALLOW
    // SCROLLABILITY
    ScrollPane scrollPane;

    // MAIN APP UI WINDOW AND SCENE GRAPH
    Stage window;
    Scene primaryScene;

    // COMPONENTS FOR BACKGROUND IMAGE
    Image image;
    double imageWidth;
    double imageHeight;
    BackgroundSize backgroundSize;
    BackgroundImage backgroundImage;
    Background background;

    // WINDOW BUTTONS AND COMPONENTS
    Button minimizeButton;
    Button closeButton;
    HBox windowPane;
    double xOffset;
    double yOffset;

    // PANE FOR FILTER AND NOW PLAYING LABEL
    // WILL GO UNDER BANNER AND ABOVE POSTERS
    VBox middlePane;
    Label nowPlayingLabel;

    DatabaseManager db;
    MovieController controller;

    public MovieAppView(DatabaseManager db) {
        this.db = db;
        movieList = new MovieTableModel();
        movieList = db.getMovieTableModel();
        controller = new MovieController(this);

        xOffset = 0.0;
        yOffset = 0.0;
    }

    private void initMovieListPane(){
        movieListPane = new FlowPane();
        movieListPane.setPrefWrapLength(945); // preferred width = 300
        movieListPane.setPrefHeight(600);
        scrollPane = new ScrollPane();

        // SETUP BACKGROUND IMAGE
        image = new Image("img/blur-blurred-dark-1526.jpg");
        imageWidth = movieListPane.getWidth();
        imageHeight = movieListPane.getHeight();
        backgroundSize = new BackgroundSize(imageWidth, imageHeight, true, true, true, true);
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.DEFAULT, backgroundSize);
        background = new Background(backgroundImage);

        // STYLE CLASSES
        movieListPane.getStyleClass().add(CSS_CLASS_FLOW_PANE);
        scrollPane.getStyleClass().add("edge-to-edge");
    }

    private void initTopBarPane() {
        headerPane = new VBox();
        Image logo = new Image("img/logo2.png");
        ImageView logoView = new ImageView(logo);

        // SETUP CUSTOM MIN/CLOSE WINDOW BUTTONS
        Image image = new Image("img/icons8-delete-50.png", 25, 25, false, false);
        closeButton = new Button();
        closeButton.setGraphic(new ImageView(image));
        closeButton.setStyle("-fx-border-color: transparent ");
        Image image2 = new Image("img/icons8-subtract-50.png", 25, 25, false, false);
        minimizeButton = new Button();
        minimizeButton.setGraphic(new ImageView(image2));
        windowPane = new HBox();
        windowPane.getChildren().addAll(minimizeButton, closeButton);

        // SETUP SPACING AND STYLE CLASSES
        closeButton.getStyleClass().add(CSS_CLASS_CLOSE_BUTTON);
        headerPane.getStyleClass().add(CSS_CLASS_HEADER_PANE);
        minimizeButton.getStyleClass().add(CSS_CLASS_MINIMIZE_BUTTON);
        windowPane.getStyleClass().add(CSS_CLASS_WINDOW_PANE);

        headerPane.getChildren().addAll(logoView);
    }

    private void initMiddlePane() {
        middlePane = new VBox();
        nowPlayingLabel = new Label("NOW PLAYING");

        // FILTER LABEL AND CHECKBOXES
        filterBox = new HBox();
        filterLabel = new Label("Filter by Rating: ");
        allCheckBox = new CheckBox("All");
        pgRatingCheckBox = new CheckBox("PG");
        pg13RatingCheckBox = new CheckBox("PG-13");
        rRatingCheckBox = new CheckBox("R");;
        nc17RatingCheckBox = new CheckBox("NC-17");
        gRatingCheckBox = new CheckBox("G");

        // SET ALL CHECKBOXES TO DEFAULT VALUE OF TRUE
        allCheckBox.setSelected(true);
        gRatingCheckBox.setSelected(true);
        pg13RatingCheckBox.setSelected(true);
        pgRatingCheckBox.setSelected(true);
        rRatingCheckBox.setSelected(true);
        nc17RatingCheckBox.setSelected(true);

        // SETUP SPACING AND STYLE CLASSES
        filterBox.getStyleClass().add(CSS_CLASS_FILTER_BOX);
        middlePane.getStyleClass().add(CSS_CLASS_MIDDLE_PANE);

        middlePane.setAlignment(Pos.CENTER);
        filterBox.getChildren().addAll(filterLabel, allCheckBox, gRatingCheckBox, pgRatingCheckBox, pg13RatingCheckBox,
                rRatingCheckBox, nc17RatingCheckBox);
        middlePane.getChildren().addAll(filterBox);
    }

    public void startUI(Stage primaryStage, String windowTitle){

        window = primaryStage;
        initTopBarPane();
        initMiddlePane();
        initMovieListPane();
        initWindow(windowTitle);
        initEventHandlers();
        initCheckboxListeners();
        reloadMovieListPane(movieList);

    }

    public void reloadMovieListPane(MovieTableModel movieList) {
        movieListPane.getChildren().clear();
        for (Movie movie : movieList.getMovies()) {
            MovieView movieEditor = new MovieView(movie, movieList);
            //MovieDescription movieEditor1 = new MovieDescription(movie, movieList);
            movieListPane.getChildren().add(movieEditor);

            movieEditor.getImageView().setOnMouseEntered(e ->  {
                primaryScene.setCursor(Cursor.HAND);
            });
            movieEditor.getImageView().setOnMouseExited(e -> {
                primaryScene.setCursor(Cursor.DEFAULT);
            });
//            movieEditor.getImageView().setOnMouseClicked(e->{
//            	maPane.getChildren().clear();
//            	movieListPane = new FlowPane();
//            	movieListPane.setPrefWrapLength(945);
//            	movieListPane.getChildren().add(movieEditor1);
////            	maPane.setCenter(movieListPane);
//            	maPane.getChildren().addAll(movieListPane);
//                primaryScene = new Scene(maPane, 960, 600);
//                window.setScene(primaryScene);
//                window.show();
//               });
        }   
    }

    private void initEventHandlers() {
        // WINDOW BUTTONS
        minimizeButton.setOnMouseClicked(e -> {
            window.setIconified(true);
        });
        closeButton.setOnAction(e -> {
            Platform.exit();
        });
        windowPane.setOnMousePressed(e -> {
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
        });
        windowPane.setOnMouseDragged(e -> {
                window.setX(e.getScreenX() - xOffset);
                window.setY(e.getScreenY() - yOffset);
        });
    }

    // ADD LISTENERS TO EACH CHECKBOX AND HANDLE EACH EVENT
    private void initCheckboxListeners() {
        allCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    gRatingCheckBox.setSelected(true);
                    pg13RatingCheckBox.setSelected(true);
                    pgRatingCheckBox.setSelected(true);
                    rRatingCheckBox.setSelected(true);
                    nc17RatingCheckBox.setSelected(true);
                }else{
                    // your checkbox has been unticked. do stuff...
                    // clear the config file
                }
            }
        });
        gRatingCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    controller.processGCheckBox();
                }else{
                    // your checkbox has been unticked. do stuff...
                    // clear the config file
                }
            }
        });
        pgRatingCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    controller.processPGCheckBox();
                }else{
                    reloadMovieListPane(movieList);
                    //movieListPane.getChildren().clear();
                }
            }
        });

    }
    public boolean allCheckBoxIsSelected() {
        return allCheckBox.isSelected() ? true : false;
    }

    public MovieTableModel getMovieList() {
        return movieList;
    }
    private void initWindow(String windowTitle) {
        window.setTitle(windowTitle);

        maPane = new VBox();
        middlePane.setBackground(background);
        maPane.getChildren().addAll(headerPane, middlePane, movieListPane);
//        maPane.setTop(headerPane);
//        maPane.setCenter(movieListPane);

        scrollPane.setContent(maPane);
        movieListPane.setBackground(background);

        rootPane = new VBox();
        rootPane.getChildren().addAll(windowPane, scrollPane);

        primaryScene = new Scene(rootPane, 972, 600);
        primaryScene.getStylesheets().add("css/movieStyle.css");

        window.setScene(primaryScene);
        window.setResizable(false);
        //primaryScene.setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.show();
    }
}
