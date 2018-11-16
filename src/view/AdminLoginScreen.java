package view;

import database.DatabaseManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static model.StartupConstants.*;

public class AdminLoginScreen extends VBox {

    private static String adminPassword = "admin";

    // COMPONENTS FOR ADMIN LOGIN SCREEN
    Label adminLabel;
    Label adminPasswordLabel;
    Label warningLabel;
    TextField passwordField;
    Button submitButton;
    Button cancelButton;

    // WINDOW MIN/CLOSE BUTTONS AND PANE
    VBox headerPane;
    HBox windowPane;
    Button minimizeButton;
    Button closeButton;
    double xOffset;
    double yOffset;
    Stage window;

    DatabaseManager db;

    public AdminLoginScreen(DatabaseManager db) {
        this.db = db;

        adminLabel = new Label("Admin Login");
        adminPasswordLabel = new Label("Password");
        warningLabel = new Label("Wrong Password!");
        warningLabel.setVisible(false);
        passwordField = new TextField();
        submitButton = new Button("Submit");
        cancelButton = new Button("Cancel");

        // ADD CSS TO EACH COMPONENT
        this.getStyleClass().addAll(CSS_CLASS_ADMIN_LOGIN_PANE);
        adminLabel.getStyleClass().addAll(CSS_CLASS_ADMIN_LOGIN_LABEL);
        warningLabel.getStyleClass().addAll(CSS_CLASS_WARNING_LABEL);
        passwordField.getStyleClass().addAll(CSS_CLASS_ADMIN_PASSWORD_FIELD);
        adminPasswordLabel.getStyleClass().addAll(CSS_CLASS_ADMIN_PASSWORD_LABEL);
        submitButton.getStyleClass().addAll(CSS_CLASS_ADMIN_SUBMIT_BUTTON);
        cancelButton.getStyleClass().addAll(CSS_CLASS_ADMIN_CANCEL_BUTTON);

        this.getChildren().addAll(adminLabel, passwordField, adminPasswordLabel, submitButton, cancelButton, warningLabel);
        initWindowPane();
        initEventHandlers();
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void initEventHandlers() {
        submitButton.setOnAction(e -> {
            auhenticate();
        });
        passwordField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
            {
                auhenticate();
            }
        });
        // WINDOW BUTTONS
        minimizeButton.setOnMouseClicked(e -> {
            window.setIconified(true);
        });
        closeButton.setOnAction(e -> {
            Platform.exit();
        });
        windowPane.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        windowPane.setOnMouseDragged(e -> {
            window.setX(e.getScreenX() - xOffset);
            window.setY(e.getScreenY() - yOffset);
        });
    }

    private void auhenticate() {
        String password = passwordField.getText();
        if (password.equals(adminPassword)) {
            VBox layout = new VBox();
            VBox rootPane = new VBox();
            ScrollPane scroll = new ScrollPane();

            window = new Stage();
            window = (Stage) this.getScene().getWindow();

            AdminPage adminPage = new AdminPage(db);
            windowPane.getStyleClass().addAll(CSS_CLASS_WINDOW_PANE);
            layout.getChildren().addAll(headerPane, adminPage);
            scroll.setContent(layout);
            rootPane.getChildren().addAll(windowPane, scroll);
            scroll.getStyleClass().add("edge-to-edge");

            Scene scene = new Scene(rootPane, 972, 600);
            scene.getStylesheets().add("css/movieStyle.css");
            window.setScene(scene);
            window.show();
        } else
            warningLabel.setVisible(true);
    }

    private void initWindowPane() {
        headerPane = new VBox();
        Image logo = new Image("img/logo3.png");
        ImageView logoView = new ImageView(logo);

        // SETUP CUSTOM MIN/CLOSE WINDOW BUTTONS
        Image image = new Image("img/icons8-delete-50.png", 25, 25, false, false);
        closeButton = new Button();
        closeButton.setGraphic(new ImageView(image));
        closeButton.setStyle("-fx-border-color: transparent ");
        Image image2 = new Image("img/icons8-subtract-50.png", 25, 25, false, false);
        minimizeButton = new Button();
        minimizeButton.setGraphic(new ImageView(image2));
        windowPane = new HBox();
        windowPane.getChildren().addAll(minimizeButton, closeButton);

        // SETUP SPACING AND STYLE CLASSES
        closeButton.getStyleClass().add(CSS_CLASS_CLOSE_BUTTON);
        headerPane.getStyleClass().add(CSS_CLASS_HEADER_PANE);
        minimizeButton.getStyleClass().add(CSS_CLASS_MINIMIZE_BUTTON);
        windowPane.getStyleClass().add(CSS_CLASS_WINDOW_PANE);

        headerPane.getChildren().addAll(logoView);
    }

    private void initAdminConfigPage() {

    }
}
