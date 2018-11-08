package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Movie;
import model.MovieTableModel;
import static model.StartupConstants.*;


public class MovieView extends VBox {
    Movie movie;

    //Label for title
    Label titleLabel;

    //Label for rating
    Label ratingLabel;

    //Image for poster
    Image posterImage;

    //Imageview
    ImageView imageView;

    public MovieView(Movie movie, MovieTableModel model) {
        this.movie = movie;

        titleLabel = new Label(movie.getMovieTitle());

        ratingLabel = new Label(movie.getRating());

        posterImage = new Image(movie.getUrlOfImage());

        titleLabel.getStyleClass().add(CSS_CLASS_POSTER_TITLE);
        ratingLabel.getStyleClass().add(CSS_CLASS_POSTER_RATING);

        imageView = new ImageView();
        imageView.setImage(posterImage);
        imageView.setFitHeight(195);
        imageView.setFitWidth(150);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(15, 15, 15, 15));
        //this.getStylesheets().add(CSS_CLASS_MOVIE_BOX);

        getChildren().addAll(titleLabel, imageView, ratingLabel);
    }
}
