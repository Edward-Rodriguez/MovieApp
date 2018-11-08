package view;

import javafx.geometry.Pos;
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

    /* **********
    Example movies
     */
    Movie movie = new Movie("Venom",
                            "alien stuff",
                            "R",
                                "GENERAL",
                            "https://i.imgur.com/H0u6dJp.jpg"
                                );

    Movie movie2 = new Movie("Haloween",
            "alien stuff",
            "PG",
            "GENERAL",
            "https://i.imgur.com/7RFjjoy.jpg"
    );

    public MovieAppView() {
        movieList = new MovieTableModel();

        movieListPane = new FlowPane();

        movieList.addMovie(movie);
        movieList.addMovie(movie2);
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
        }
    }

    private void initWindow(String windowTitle) {
        window.setTitle(windowTitle);
        scrollPane = new ScrollPane();
        scrollPane.setContent(movieListPane);

        borderPane = new BorderPane();
        borderPane.setTop(topBarPane);
        borderPane.setCenter(scrollPane);

        primaryScene = new Scene(borderPane, 900, 600);
        primaryScene.getStylesheets().add("css/movieStyle.css");

        window.setScene(primaryScene);
        window.show();

    }

}
