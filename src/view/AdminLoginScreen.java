package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    public AdminLoginScreen() {

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
        initEventHandlers();
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    private void initEventHandlers() {
        submitButton.setOnAction(e -> {
            String password = passwordField.getText();
            if (password.equals(adminPassword)) {
                System.out.println("success");
            } else
                warningLabel.setVisible(true);
        });
    }

    private void initAdminConfigPage() {

    }
}
