package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FocusController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private TextField depositField;

    @FXML
    public void FocusOn(MouseEvent event) {
        emailField.setFocusTraversable(true);
        passwordField.setFocusTraversable(true);
        showPasswordCheckBox.setFocusTraversable(true);
    }

    @FXML
    public void FocusOn1(MouseEvent event) {
        depositField.setFocusTraversable(true);
    }
}
