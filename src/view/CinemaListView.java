package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Cinema;
import model.CinemaTableModel;

import java.util.*;

import static model.StartupConstants.CSS_CLASS_ADMIN_DELETE_BUTTON;

public class CinemaListView extends VBox {

    private Label cinemaTitleLabel;
    private Label filterLabel;
    private Cinema cinema;
    private String address;
    private int xCoord;
    private int yCoord;
    private int radius;
    private int distance;
    private String cinemaName;
    private Label addressLabel;
    private Label XaddressLabel;
    private Label YaddressLabel;
    private TextField addressXField;
    private TextField addressYField;
    private Label radiusLabel;
    private TextField radiusField;
    private CinemaTableModel cinemaTableModel;
    private CinemaTableModel tempTableModel;
    private HBox filterBox;
    private Button backButton;
    private Button filterButton;
    private Scene scene;
    private Stage stage;
    private Label warningLabel;
    CinemaTableModel tempCinemaModel;

    public CinemaListView (CinemaTableModel model, Stage stage){
        this.cinemaTableModel = model;
        this.setSpacing(10);
        this.stage = stage;
        tempCinemaModel = new CinemaTableModel();

        filterBox = new HBox(10);
        backButton = new Button("Go Back");
        filterLabel = new Label("Filter:");
        filterLabel.setTextFill(Color.WHITE);
        addressLabel = new Label("Enter Your Address:");
        addressLabel.setTextFill(Color.WHITE);
        XaddressLabel = new Label("X:");
        XaddressLabel.setTextFill(Color.WHITE);
        addressXField = new TextField();
        YaddressLabel = new Label("Y:");
        YaddressLabel.setTextFill(Color.WHITE);
        addressYField = new TextField();
        addressXField.setPromptText("1-100");
        addressYField.setPromptText("1-100");
        addressXField.setMaxWidth(50);
        addressYField.setMaxWidth(50);
        radiusLabel = new Label("Radius:");
        radiusLabel.setTextFill(Color.WHITE);
        radiusField = new TextField();
        radiusField.setPromptText("0-100");
        filterButton = new Button("Apply Filter");
        warningLabel = new Label("Error check fields!");
        warningLabel.setTextFill(Color.RED);
        warningLabel.setVisible(false);
        filterBox.getChildren().addAll(filterLabel, addressLabel, XaddressLabel, addressXField, YaddressLabel, addressYField, radiusLabel, radiusField, filterButton);
        this.getChildren().addAll(backButton, filterBox);

        cinemaTitleLabel = new Label("Cinemas:");
        cinemaTitleLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.getChildren().add(cinemaTitleLabel);
        initEventHandlers();
        generateList(this.cinemaTableModel);
    }

    private void initEventHandlers() {
        // force the coordinates field to be numeric only
        addressXField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("(100)|(0*\\d{1,2})")) {
                    addressXField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        addressYField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("(100)|(0*\\d{1,2})")) {
                    addressYField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        radiusField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("(100)|(0*\\d{1,2})")) {
                    radiusField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        filterButton.setOnAction(e -> {
        filterCinemas();
        });
    }

    private void filterCinemas() {
        tempCinemaModel = new CinemaTableModel();
        if (!addressXField.getText().isEmpty() && !addressYField.getText().isEmpty() && !radiusField.getText().isEmpty()) {
            xCoord = Integer.parseInt(addressXField.getText());
            yCoord = Integer.parseInt(addressYField.getText());
            radius = Integer.parseInt(radiusField.getText());

            if ((xCoord >= 0 && xCoord <= 100) && (yCoord >= 0 && yCoord <= 100) && (radius >= 0 && radius <= 100)) {
                for (Cinema cinema : cinemaTableModel.getCinemas()) {
                    String tempAddress = cinema.getAddress();
                    tempAddress = tempAddress.replace("(", "");
                    tempAddress = tempAddress.replace(")", "");
                    String[] xy = tempAddress.split(",");
                    int x = Integer.parseInt(xy[0]);
                    int y = Integer.parseInt(xy[1]);
                    if ((Math.abs(xCoord - x) <= radius) && (Math.abs(yCoord - y) <= radius)) {
                        tempCinemaModel.addCinema(cinema);
                    }
                    warningLabel.setVisible(false);
                    this.getChildren().clear();
                    this.getChildren().addAll(backButton, filterBox, cinemaTitleLabel);
                    generateList(tempCinemaModel);
                }
            } else warningLabel.setVisible(true);
        } else warningLabel.setVisible(true);
    }

    private void generateList(CinemaTableModel model) {
        for (Cinema cinema: model.getCinemas()) {
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
                    Scene oldScene = this.stage.getScene();
                    CinemaViewer cinemaViewer = new CinemaViewer(cinema);
                    Scene scene = new Scene(cinemaViewer, 972, 600);

                    cinemaViewer.getBackButton().setOnAction(event -> {
                        this.stage.setScene(oldScene);
                    });
                    this.stage.setScene(scene);
                }
            });
        }
    }

    public Button getBackButton() {
        return backButton;
    }

    class CinemaViewer extends VBox {
        private Button backButton;
         private Cinema cinema;
         private Label cinemaNameLabel;
         private HashMap<String, ArrayList<String>> movieShowtimeMap;
         private HashMap<String, String> movieShowtimeRatingMap;

        public CinemaViewer(Cinema cinema) {
            this.cinema = cinema;
            this.movieShowtimeMap = cinema.getMovieShowtimesMap();
            this.movieShowtimeRatingMap = cinema.getMovieShowtimesRatingMap();
            this.setSpacing(10);
            backButton = new Button("Go Back");
//            container = new HBox(10);

            cinemaNameLabel = new Label(this.cinema.getCinemaName());
            cinemaNameLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
            cinemaNameLabel.setPrefWidth(300);
            this.getChildren().addAll(backButton, cinemaNameLabel);
            generateShowtimes();
        }

        private void generateShowtimes() {
                for (Map.Entry<String, ArrayList<String>> entry : movieShowtimeMap.entrySet()) {
                    String key = entry.getKey();
                    ArrayList<String> value = entry.getValue();

                    Region spacer = new Region();
                    spacer.setMinWidth(50);
                    HBox container = new HBox(10);
                    Label movieName = new Label(entry.getKey());
                    movieName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                    movieName.setMinWidth(150);

                    Label ratingLabel = new Label(movieShowtimeRatingMap.get(key));
                    ratingLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                    ratingLabel.setMinWidth(50);

                    container.getChildren().addAll(movieName, spacer, ratingLabel);

                    for(String aString : value){
                        Button tempShowtimeButton = new Button(aString);
                        tempShowtimeButton.setMinWidth(60);
                        tempShowtimeButton.setMinHeight(28);
                        container.getChildren().add(tempShowtimeButton);
                        tempShowtimeButton.setPadding(new Insets(0,5,0,5));
                    }
                    this.getChildren().add(container);
                }

        }
        public Button getBackButton() {
            return backButton;
        }

    }
}
