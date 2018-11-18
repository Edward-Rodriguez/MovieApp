package view;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Cinema;
import model.CinemaTableModel;

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
    }

    private void generateList() {

    }

    public Button getBackButton() {
        return backButton;
    }
}
