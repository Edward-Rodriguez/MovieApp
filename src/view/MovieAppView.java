package view;

import database.DatabaseManager;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Movie;
import model.MovieTableModel;

import static model.StartupConstants.CSS_CLASS_WELCOME_LABEL;

public class MovieAppView {
    MovieTableModel movieList;

    FlowPane movieListPane;

    BorderPane borderPane;

    ScrollPane scrollPane;

    HBox topBarPane;

    Label welcomeLabel;

    Label nowPlayingLabel;

    Stage window;

    Scene primaryScene;

    DatabaseManager db;

    /* **********
    Example movies
     */
    Movie movie = new Movie(1,"Venom",
                            "alien stuff",
                            "R",
                                "GENERAL",
                            "Mom and Pops",
                            "https://i.imgur.com/H0u6dJp.jpg"
                                );

    public MovieAppView(DatabaseManager db) {
        this.db = db;
        movieList = new MovieTableModel();
        movieList = db.getMovieTableModel();

        movieListPane = new FlowPane();
//        movieListPane.setVgap(8);
//        movieListPane.setHgap(4);
        movieListPane.setPrefWrapLength(945); // preferred width = 300

//        movieList.addMovie(movie);
//        movieList.addMovie(movie);
//        movieList.addMovie(movie);
//        movieList.addMovie(movie);
//        movieList.addMovie(movie);
//        movieList.addMovie(movie);
//        movieList.addMovie(movie);
    }

    private void initTopBarPane() {
        topBarPane = new HBox();
        welcomeLabel = new Label("Welcome to MovieApp");
        welcomeLabel.getStyleClass().add(CSS_CLASS_WELCOME_LABEL);
        topBarPane.setAlignment(Pos.CENTER);

        topBarPane.getChildren().addAll(welcomeLabel);
    }

    public void startUI(Stage primaryStage, String windowTitle){
        window = primaryStage;
        initTopBarPane();
        initWindow(windowTitle);
        reloadMovieListPane();

    }

    private void reloadMovieListPane() {
        //movieListPane.getChildren().clear();

        for (Movie movie : movieList.getMovies()) {
            MovieView movieEditor = new MovieView(movie, movieList);
            movieListPane.getChildren().add(movieEditor);

            movieEditor.getImageView().setOnMouseEntered(e ->  {
                primaryScene.setCursor(Cursor.HAND);
            });
            movieEditor.getImageView().setOnMouseExited(e -> {
                primaryScene.setCursor(Cursor.DEFAULT);
            });
        }
    }

    private void initWindow(String windowTitle) {
        window.setTitle(windowTitle);
        scrollPane = new ScrollPane();
        scrollPane.setContent(movieListPane);

        borderPane = new BorderPane();
        borderPane.setTop(topBarPane);
        borderPane.setCenter(scrollPane);

        primaryScene = new Scene(borderPane, 955, 600);
        primaryScene.getStylesheets().add("css/movieStyle.css");

        window.setScene(primaryScene);
        window.show();

    }

}
