package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Movie;

import static model.StartupConstants.CSS_CLASS_ADMIN_DELETE_BUTTON;

public class SingleMovieContainerView extends HBox {

    private Movie movie;
    private Button deleteButton;
    private Image imageDelete;
    private Label movieTitle;

    public SingleMovieContainerView(Movie movie) {
        this.movie = movie;

        // MOVIE TITLE
        movieTitle = new Label(this.movie.getMovieTitle());
        movieTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        movieTitle.setPrefWidth(200);

        imageDelete = new Image(getClass().getResourceAsStream("../img/trash.png"));
        deleteButton = new Button();
        deleteButton.setPadding(Insets.EMPTY);
        deleteButton.setGraphic(new ImageView(imageDelete));
        deleteButton.setVisible(false);

        deleteButton.getStyleClass().add(CSS_CLASS_ADMIN_DELETE_BUTTON);

        getChildren().addAll(movieTitle, deleteButton);
        this.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: #D1EEEE;");
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
