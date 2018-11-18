package view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Cinema;
import model.CinemaTableModel;
import model.Movie;

import java.util.*;

import static model.StartupConstants.CSS_CLASS_ADMIN_DELETE_BUTTON;

public class CinemaListView extends VBox {

    private Label cinemaTitleLabel;
    private Label filterLabel;
    private Cinema cinema;
    private String address;
    private String cinemaName;
    private Label addressLabel;
    private TextField addressField;
    private Label radiusLabel;
    private TextField radiusField;
    private CinemaTableModel cinemaTableModel;
    private CinemaTableModel tempTableModel;
    private HBox filterBox;
    private Button backButton;
    private Scene scene;
    private Stage stage;

    public CinemaListView (CinemaTableModel model, Stage stage){
        this.cinemaTableModel = model;
        this.setSpacing(10);
        this.stage = stage;

        filterBox = new HBox(10);
        backButton = new Button("Go Back");
        filterLabel = new Label("Filter:");
        addressLabel = new Label("Address:");
        addressField = new TextField();
        addressField.setPromptText("x,y");
        radiusLabel = new Label("Radius:");
        radiusField = new TextField();
        radiusField.setPromptText("0-100");
        filterBox.getChildren().addAll(filterLabel, addressLabel, addressField, radiusLabel, radiusField);
        this.getChildren().addAll(backButton, filterBox);

        cinemaTitleLabel = new Label("Cinemas:");
        cinemaTitleLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.getChildren().add(cinemaTitleLabel);
        generateList();
    }

    private void generateList() {
        for (Cinema cinema: cinemaTableModel.getCinemas()) {
            HBox singleContainer = new HBox();
            Label cinemaAddress = new Label(cinema.getAddress());
            Label cinemaName = new Label(cinema.getCinemaName());
            cinemaName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            cinemaName.setPrefWidth(300);
            cinemaAddress.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            cinemaAddress.setPrefWidth(200);

            Region spacer = new Region();

            HBox.setHgrow(spacer, Priority.ALWAYS);
            spacer.setMaxWidth(200);

            singleContainer.getChildren().addAll(cinemaName, cinemaAddress);

            this.getChildren().add(singleContainer);
            singleContainer.setStyle("-fx-border-style: solid inside;"
                    + "-fx-border-width: 0;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #D1EEEE;" +
                    "-fx-background-color: white;");
            singleContainer.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
                    CinemaViewer cinemaViewer = new CinemaViewer(cinema);
                    Scene scene = new Scene(cinemaViewer, 972, 600);
                    this.stage.setScene(scene);
                }
            });
        }
    }

    public Button getBackButton() {
        return backButton;
    }

    class CinemaViewer extends VBox {
    private Cinema cinema;
    private Label cinemaNameLabel;
    private HashMap<String, List<String>> movieShowtimeMap;
    private HashMap<String, List<String>> TempmovieShowtimeMap;

        public CinemaViewer(Cinema cinema) {
            this.cinema = cinema;
            this.movieShowtimeMap = cinema.getMovieShowtimesMap();
            this.setSpacing(10);
//            container = new HBox(10);

            /************
             TESTING MAP
             */
            ArrayList<String> showtimeList = new ArrayList<String>(){
                {
                    add("9:30am");
                    add("9:30pm");
                    add("9:00am");
                }
            };
            ArrayList<String> showtimeList2 = new ArrayList<String>(){
                {
                    add("9:00am");
                    add("10:30pm");
                    add("11:00am");
                }
            };
            TempmovieShowtimeMap = new HashMap<String, List<String>>();
            TempmovieShowtimeMap.put("Venom", showtimeList);
            TempmovieShowtimeMap.put("Halloween", showtimeList);

            cinemaNameLabel = new Label(this.cinema.getCinemaName());
            cinemaNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
            cinemaNameLabel.setPrefWidth(300);
            this.getChildren().add(cinemaNameLabel);
            generateShowtimes();
        }

        private void generateShowtimes() {
            Iterator it = TempmovieShowtimeMap.entrySet().iterator();
            while (it.hasNext()) {
                Region spacer = new Region();
                spacer.setMinWidth(100);
                HBox container = new HBox();
                Map.Entry pair = (Map.Entry)it.next();
                Label movieName = new Label(pair.getKey().toString());
                container.getChildren().addAll(movieName, spacer);

                for (int i =0; i < TempmovieShowtimeMap.size(); ++i){
                    Button tempShowtimeButton = new Button(pair.getValue().toString());
                    container.getChildren().add(tempShowtimeButton);
                }
                System.out.println(pair.getKey() + " = " + pair.getValue());
                it.remove(); // avoids a ConcurrentModificationException
                this.getChildren().add(container);
            }
        }
    }
}
