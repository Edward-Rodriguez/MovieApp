package view;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Cinema;
import model.CinemaTableModel;

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

    public CinemaListView (CinemaTableModel model){
        this.cinemaTableModel = model;
        this.setSpacing(10);

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
        }
    }

    public Button getBackButton() {
        return backButton;
    }
}
