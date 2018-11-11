package view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Movie;
import model.MovieTableModel;
import static model.StartupConstants.*;


public class MovieDescription extends HBox {
	
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
    
    Text Description;
    
    

    public MovieDescription(Movie movie, MovieTableModel model) {
    	VBox vbox= new VBox();
        this.movie = movie;

        titleLabel = new Label(movie.getMovieTitle());

        ratingLabel = new Label(movie.getRating());

        posterImage = new Image(movie.getUrlOfImage());
        
        
      // cinema = new String(movie.getListOfCinemas());
        
       


        titleLabel.getStyleClass().add(CSS_CLASS_POSTER_TITLE);
        ratingLabel.getStyleClass().add(CSS_CLASS_POSTER_RATING);
        Description = new Text(movie.getDescription());
        releaseTypeLabel = new Label(movie.getReleaseType());
        
      
        
        imageView = new ImageView();
        imageView.setImage(posterImage);
        imageView.setFitHeight(195);
        imageView.setFitWidth(150);
        imageView.getStyleClass().add(CSS_CLASS_POSTER_IMAGE);
       
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15, 15, 15, 22));
        

        vbox.getChildren().addAll(titleLabel, imageView, ratingLabel,Description,releaseTypeLabel);
        dT=new VBox();
       
     
        Separator separator1 = new Separator();
        final Text text = new Text ("Showtimes");
        
       
       
        Button button1 = new Button("7:00");
        Button button2 = new Button("9:00");
        Button button3 = new Button("10:30");
        Button button4 = new Button("12:30");
        Button button5 = new Button("1:30");
        Button button6 = new Button("4:20");
        button1.getPrefWidth();
        button2.getPrefWidth();
        button3.getPrefWidth();
        button4.getPrefWidth();
        button5.getPrefWidth();
        button6.getPrefWidth();
        

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        gridPane.setVgap(10); //vertical gap in pixels
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(button4, 0, 1, 1, 1);
        gridPane.add(button5, 1, 1, 1, 1);
        gridPane.add(button6, 2, 1, 1, 1);
        
        gridPane.setAlignment(Pos.CENTER);
        
        
     
        
        
        dT.setAlignment(Pos.CENTER);
        dT.getChildren().addAll(separator1,text,gridPane);
        
    
        
       
        this.getChildren().addAll(vbox,dT);
        this.setAlignment(Pos.CENTER);
    }
    
    
    

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
