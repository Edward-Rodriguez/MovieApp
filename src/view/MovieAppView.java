package view;

import controller.MovieController;
import database.DatabaseManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.CinemaTableModel;
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
    CinemaTableModel cinemaTableModel;

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
    Scene oldScene;
    Scene newScene;

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

    // BUTTON FOR ADMIN LOGIN
    // WILL REDIRECT TO ADMIN PAGE
    Button adminLoginButton;

    // PANE FOR FOOTER
    // WILL CONTAIN ADMIN BUTTON AND OTHER INFO
    HBox footerPane;
    HBox buttonPane;

    // ADMIN PANE
    AdminLoginScreen adminLoginScreen;
    private String adminPassword = "admin";

    DatabaseManager db;
    MovieController controller;
    private static final String cssPath = "css/movieStyle.css";

    Button movieListButton;
    Button cinemaListButton;

    //CINEMALIST COMPONENTS


    public MovieAppView(DatabaseManager db) {
        this.db = db;
        movieList = new MovieTableModel();
        movieList = db.getMovieTableModel();
        controller = new MovieController(this);
        cinemaTableModel = db.getCinemaTableModel();

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
        Image logo = new Image("img/logo3.png");
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

    private void initButtonPane() {
        buttonPane = new HBox();
        buttonPane.setStyle("-fx-background-color: black;");
        movieListButton = new Button("Movies");
        cinemaListButton = new Button("Cinemas");
        movieListButton.setStyle("-fx-background-color: black;\n" +
                "/*     -fx-text-fill: #6E2DBD;*/\n" +
                "     -fx-text-fill: #D8D8D8;\n" +
                "     -fx-font: normal bold 20px 'Arial';");
        cinemaListButton.setStyle("-fx-background-color: black;\n" +
                "/*     -fx-text-fill: #6E2DBD;*/\n" +
                "     -fx-text-fill: #D8D8D8;\n" +
                "     -fx-font: normal bold 20px 'Arial';");
        movieListButton.setFocusTraversable(true);
        cinemaListButton.setFocusTraversable(true);
        HBox.setHgrow(movieListButton, Priority.ALWAYS);
        HBox.setHgrow(cinemaListButton, Priority.ALWAYS);
        movieListButton.setMaxWidth(Double.MAX_VALUE);
        cinemaListButton.setMaxWidth(Double.MAX_VALUE);
        buttonPane.getChildren().addAll(movieListButton, cinemaListButton);
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

    private void initFooterPane() {
        footerPane = new HBox();
        footerPane.setMinHeight(40);

        adminLoginButton = new Button("Admin");
        Image image = new Image("img/icons8-admin.png", 25, 25, false, false);
        adminLoginButton.setGraphic(new ImageView(image));
        adminLoginButton.getStyleClass().add(CSS_CLASS_ADMIN_BUTTON);
        footerPane.getStyleClass().add(CSS_CLASS_FOOTER_PANE);
        footerPane.getChildren().add(adminLoginButton);
        footerPane.setAlignment(Pos.BASELINE_RIGHT);
    }

    public void startUI(Stage primaryStage, String windowTitle){

        window = primaryStage;
        initTopBarPane();
        initButtonPane();
        initMiddlePane();
        initMovieListPane();
        initFooterPane();
        initEventHandlers();
        initCheckboxListeners();
        initWindow(windowTitle);
        reloadMovieListPane(movieList);

    }

    public void reloadMovieListPane(MovieTableModel movieList) {
        movieListPane.getChildren().clear();
        for (Movie movie : movieList.getMovies()) {
            MovieView movieEditor = new MovieView(movie, movieList);
            MovieDescription movieEditor1 = new MovieDescription(movie, movieList);
            movieListPane.getChildren().add(movieEditor);

            movieEditor.getImageView().setOnMouseEntered(e ->  {
                primaryScene.setCursor(Cursor.HAND);
            });
            movieEditor.getImageView().setOnMouseExited(e -> {
                primaryScene.setCursor(Cursor.DEFAULT);
            });
            movieEditor.getImageView().setOnMouseClicked(e->{

                ScrollPane scroll = new ScrollPane();
                scroll.getStyleClass().add("edge-to-edge");
                Button backButton = new Button("Back");

                Separator lineSeparator = new Separator();

                VBox rootPane2 = new VBox();
                VBox movieDescriptionPane = new VBox();

                movieListPane = new FlowPane();
                movieListPane.setPrefWrapLength(945);
                movieListPane.getChildren().add(movieEditor1);

//            	maPane.setCenter(movieListPane);
                windowPane.getStyleClass().add(CSS_CLASS_WINDOW_PANE);
                movieDescriptionPane.getChildren().addAll(headerPane, backButton, movieListPane);

                movieListPane.setBackground(background);
                scroll.setContent(movieDescriptionPane);

                rootPane2.getChildren().addAll(windowPane, scroll);

                backButton.setOnAction(event -> {
                    window.setScene(oldScene);
                });

                newScene = new Scene(rootPane2, 960, 600);
                newScene.getStylesheets().add("css/movieStyle.css");
                window.setScene(newScene);
                window.show();
            });
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
        adminLoginButton.setOnMouseEntered(e ->  {
            primaryScene.setCursor(Cursor.HAND);
        });
        adminLoginButton.setOnMouseExited(e -> {
            primaryScene.setCursor(Cursor.DEFAULT);
        });
        adminLoginButton.setOnAction(e -> {
            adminLoginScreen = new AdminLoginScreen(db);
            Scene scene = new Scene(adminLoginScreen, 972, 600);
            scene.getStylesheets().add(cssPath);
            window.setScene(scene);

            adminLoginScreen.getCancelButton().setOnAction(event -> {
                try {
                    db.retrieveMovies();
                } catch (Exception f) {
                    System.err.println(f);
                }
                movieList = db.getMovieTableModel();
                reloadMovieListPane(movieList);
                window.setScene(oldScene);
            });
        });
        cinemaListButton.setOnAction(e -> {
            ScrollPane scroll = new ScrollPane();
            scroll.getStyleClass().add("edge-to-edge");

            Separator lineSeparator = new Separator();
            CinemaListView cinemaListView = new CinemaListView(cinemaTableModel, window);

            VBox rootPane2 = new VBox();
            VBox movieDescriptionPane = new VBox();

            movieListPane = new FlowPane();
            movieListPane.setPrefWrapLength(945);
            movieListPane.getChildren().add(cinemaListView);

            windowPane.getStyleClass().add(CSS_CLASS_WINDOW_PANE);
            movieDescriptionPane.getChildren().addAll(headerPane, movieListPane);

            movieListPane.setBackground(background);
            movieListPane.setMinHeight(600);
            scroll.setContent(movieDescriptionPane);

            rootPane2.getChildren().addAll(windowPane, scroll);

            cinemaListView.getBackButton().setOnAction(event -> {
                window.setScene(oldScene);
            });

            newScene = new Scene(rootPane2, 960, 600);
            newScene.getStylesheets().add("css/movieStyle.css");
            window.setScene(newScene);
            window.show();
        });

    }

    public CheckBox getAllCheckBox() {
        return allCheckBox;
    }

    public CheckBox getPgRatingCheckBox() {
        return pgRatingCheckBox;
    }

    public CheckBox getPg13RatingCheckBox() {
        return pg13RatingCheckBox;
    }

    public CheckBox getrRatingCheckBox() {
        return rRatingCheckBox;
    }

    public CheckBox getNc17RatingCheckBox() {
        return nc17RatingCheckBox;
    }

    public CheckBox getgRatingCheckBox() {
        return gRatingCheckBox;
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

        maPane.getChildren().addAll(headerPane, buttonPane, middlePane, movieListPane, footerPane);
//        maPane.setTop(headerPane);
//        maPane.setCenter(movieListPane);

        scrollPane.setContent(maPane);
        scrollPane.setHmax(1200);
        movieListPane.setBackground(background);

        rootPane = new VBox();
        rootPane.getChildren().addAll(windowPane, scrollPane);

        primaryScene = new Scene(rootPane, 972, 600);
        primaryScene.getStylesheets().add(cssPath);
        oldScene = primaryScene;

        window.setScene(primaryScene);
        window.setResizable(false);
        //primaryScene.setFill(Color.TRANSPARENT);
        window.initStyle(StageStyle.TRANSPARENT);
        window.show();
    }
}
