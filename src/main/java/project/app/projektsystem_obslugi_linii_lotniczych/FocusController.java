package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class FocusController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox showPasswordCheckBox;

    @FXML
    public void FocusOn(MouseEvent event) {
        emailField.setFocusTraversable(true);
        passwordField.setFocusTraversable(true);
        showPasswordCheckBox.setFocusTraversable(true);
    }
}
