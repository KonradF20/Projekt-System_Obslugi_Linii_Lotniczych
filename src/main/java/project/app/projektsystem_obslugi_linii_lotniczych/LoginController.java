package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String user = emailField.getText();
        String pass = passwordField.getText();

        if ("admin".equals(user) && "1234".equals(pass)) {
            errorLabel.setVisible(false);
            System.out.println("Zalogowano pomyślnie!");
        } else {
            errorLabel.setText("Nieprawidłowy login lub hasło.");
            errorLabel.setVisible(true);
        }
    }
}
