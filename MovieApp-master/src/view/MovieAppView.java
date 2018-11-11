package view;

import database.DatabaseManager;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Movie;
import model.MovieTableModel;

import static model.StartupConstants.CSS_CLASS_FLOW_PANE;
import static model.StartupConstants.CSS_CLASS_TOP_BAR_PANE;
import static model.StartupConstants.CSS_CLASS_WELCOME_LABEL;

public class MovieAppView {
    MovieTableModel movieList;

    FlowPane movieListPane;

    BorderPane borderPane;

    ScrollPane scrollPane;

    HBox topBarPane;
    
    HBox hboxFilter;
    
    HBox cbLocation;
    
    VBox vbox;

    Label welcomeLabel;

    Label nowPlayingLabel;
    
    Label filterLabel;

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
        movieListPane.setPrefWrapLength(945); // preferred width = 300
        movieListPane.getStyleClass().add(CSS_CLASS_FLOW_PANE);
    }

    private void initTopBarPane() {
        topBarPane = new HBox();
        welcomeLabel = new Label("Welcome to MovieApp");
        welcomeLabel.getStyleClass().add(CSS_CLASS_WELCOME_LABEL);
        topBarPane.getStyleClass().add(CSS_CLASS_TOP_BAR_PANE);
        topBarPane.setAlignment(Pos.CENTER);
        topBarPane.setMinHeight(70);
        topBarPane.getChildren().addAll(welcomeLabel);
    }
    
    private void VeeBox() {
    	vbox= new VBox();
    	 topBarPane = new HBox();
         welcomeLabel = new Label("Welcome to MovieApp");
         welcomeLabel.getStyleClass().add(CSS_CLASS_WELCOME_LABEL);
         topBarPane.getStyleClass().add(CSS_CLASS_TOP_BAR_PANE);
         topBarPane.setAlignment(Pos.CENTER);
         topBarPane.getChildren().addAll(welcomeLabel);
         cbLocation = new HBox();
         filterLabel= new Label("Filter by Rating: ");
     	CheckBox checkBox1 = new CheckBox("All");
     	CheckBox checkBox2 = new CheckBox("G");
     	 CheckBox checkBox3 = new CheckBox("PG");
         CheckBox checkBox4 = new CheckBox("PG-13");
         CheckBox checkBox5 = new CheckBox("R");
         CheckBox checkBox6 = new CheckBox("NC-17");
         cbLocation.setAlignment(Pos.TOP_RIGHT);
     	
     	cbLocation.getChildren().addAll(filterLabel,checkBox1,checkBox2,checkBox3,checkBox4,checkBox5,checkBox6);
         
    	vbox.getChildren().addAll(topBarPane,cbLocation);
    	
    }

    public void startUI(Stage primaryStage, String windowTitle){
        window = primaryStage;
        VeeBox();
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
        borderPane.setTop(vbox);
        borderPane.setCenter(scrollPane);

        primaryScene = new Scene(borderPane, 955, 600);
        primaryScene.getStylesheets().add("css/movieStyle.css");

        window.setScene(primaryScene);
        window.show();

    }

}
