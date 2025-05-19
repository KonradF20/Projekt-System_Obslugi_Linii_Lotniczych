package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Button loginButton;
    @FXML private Button signupButton;

    @FXML
    public void initialize() {
        // Obsługa widoczności hasła
        showPasswordCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                showPlainPassword();
            } else {
                hidePlainPassword();
            }
        });
    }

    @FXML
    public void loginUser() {
        String email = emailField.getText().trim();
        String password = showPasswordCheckBox.isSelected()
                ? visiblePasswordField.getText()
                : passwordField.getText();

        emailError.setVisible(false);
        passwordError.setVisible(false);

        if (email.isEmpty() || !email.contains("@")) {
            emailError.setText("Niepoprawny email");
            emailError.setVisible(true);
            return;
        }

        if (password.isEmpty()) {
            passwordError.setText("Hasło nie może być puste");
            passwordError.setVisible(true);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Logowanie udane
                System.out.println("Zalogowano jako: " + rs.getString("email") + " (rola: " + rs.getString("role") + ")");
                InfoDisplay.email = rs.getString("email");
                // Tutaj można przejść do głównego widoku aplikacji
                goToMainView();
            } else {
                passwordError.setText("Niepoprawny email lub hasło");
                passwordError.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSignup() {
        //Przejście do rejestracji
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/app/projektsystem_obslugi_linii_lotniczych/signup_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToMainView() {
        //Przejście do głównego widoku
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/app/projektsystem_obslugi_linii_lotniczych/main_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     //Obsługa widoczności hasła
     private void showPlainPassword() {
         visiblePasswordField.setText(passwordField.getText());
         visiblePasswordField.setVisible(true);
         visiblePasswordField.setManaged(true);

         passwordField.setVisible(false);
         passwordField.setManaged(false);
     }

    private void hidePlainPassword() {
        passwordField.setText(visiblePasswordField.getText());
        passwordField.setVisible(true);
        passwordField.setManaged(true);

        visiblePasswordField.setVisible(false);
        visiblePasswordField.setManaged(false);
    }
}

