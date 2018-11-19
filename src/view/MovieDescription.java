package view;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Movie;
import model.MovieTableModel;
import static model.StartupConstants.*;


public class MovieDescription extends VBox {

    // PANE FOR POSTER, MOVIE DESC., SHOWTIMES
    HBox movieInfoPane;

	VBox  dT;
	VBox vbox2;
    Movie movie;

    //Label for title
    Label titleLabel;

    //Label for rating
    Label ratingLabel;

    //Image for poster
    Image posterImage;

    //Imageview
    ImageView imageView;
    
    //String cinema;
    
    Label releaseTypeLabel;

    // Movie summary
    Label movieSummary;

    Text Description;

    // THIS WILL GO AT THE BOTTOM
    // CINEMA LIST PANE
    Label nowPlayingAt;



    public MovieDescription(Movie movie, MovieTableModel model) {
    	VBox vbox= new VBox();
        this.movie = movie;

        titleLabel = new Label(movie.getMovieTitle());

        ratingLabel = new Label(movie.getRating());

        posterImage = new Image(movie.getUrlOfImage());

        //
      // cinema = new String(movie.getListOfCinemas());
        titleLabel.getStyleClass().add(CSS_CLASS_POSTER_TITLE);
        ratingLabel.getStyleClass().add(CSS_CLASS_POSTER_RATING);
        releaseTypeLabel = new Label(movie.getReleaseType());

        imageView = new ImageView();
        imageView.setImage(posterImage);
        imageView.setFitHeight(195);
        imageView.setFitWidth(150);
        imageView.getStyleClass().add(CSS_CLASS_POSTER_IMAGE);

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15, 15, 15, 22));


        vbox.getChildren().addAll(titleLabel, imageView, ratingLabel, releaseTypeLabel);
        dT=new VBox();

        Region space = new Region();
        VBox.setVgrow(space, Priority.ALWAYS);
        space.setMaxHeight(40);

        Separator lineSeparator = new Separator();
        lineSeparator.setHalignment(HPos.CENTER);
        lineSeparator.setPrefWidth(900);
        lineSeparator.setPadding(new Insets(0,0,0, 40));
        nowPlayingAt = new Label("NOW PLAYING AT:");
        nowPlayingAt.setStyle("-fx-alignment: center;\n" +
                "        -fx-font: normal bold 20px 'Arial';\n" +
                "        -fx-text-fill: #000000;\n" +
                "        -fx-padding:4;");

        movieSummary = new Label();
        movieSummary.setMaxWidth(400);
        movieSummary.setWrapText(true);
        movieSummary.setText(movie.getMovieSummary());
        movieSummary.setStyle("-fx-alignment: center;\n" +
                "        -fx-font: normal bold 13px 'Arial';\n" +
                "        -fx-text-fill: #000000;\n" +
                "        -fx-padding:4;");

        dT.setAlignment(Pos.TOP_CENTER);
        dT.getChildren().addAll(space, movieSummary);

        movieInfoPane = new HBox();
        movieInfoPane.getChildren().addAll(vbox, dT);

        this.getChildren().addAll(movieInfoPane, nowPlayingAt, lineSeparator);
        this.setAlignment(Pos.CENTER);


        CinemaView cinemaView = new CinemaView(movie);
        this.getChildren().add(cinemaView);

    }




    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
