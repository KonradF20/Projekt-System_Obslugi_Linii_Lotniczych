package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage extends FocusController {

    // Elementy interfejsu GUI zdefiniowane w pliku FXML
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Button loginButton;
    @FXML private Button signupButton;

    // Metoda odpowiedzialna za logowanie użytkownika
    @FXML
    public void loginUser() {
        // Pobranie danych z pól tekstowych
        String email = emailField.getText().trim();
        String password;

        // Pobranie hasła z odpowiedniego pola w zależności od widoczności
        if (showPasswordCheckBox.isSelected()){
            password = visiblePasswordField.getText();
        } else {
           password = passwordField.getText();
        }

        // Walidacja danych wejściowych emailu
        if (email.isEmpty()) {
            emailError.setText("Email nie może być pusty");
            emailError.setVisible(true);
            return;
        } else if (!email.contains("@")) {
            emailError.setText("Email musi zawierać '@'");
            emailError.setVisible(true);
            return;
        } else {
            emailError.setVisible(false);
        }

        // Walidacja danych wejściowych hasła
        if (password.isEmpty()) {
            passwordError.setText("Hasło nie może być puste");
            passwordError.setVisible(true);
            return;
        } else {
            passwordError.setVisible(false);
        }

        // Sprawdzenie poprawności danych logowania
        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkEmailQuery = "SELECT user_id, password, role FROM users WHERE email = ?";
            PreparedStatement emailStmt = conn.prepareStatement(checkEmailQuery);
            emailStmt.setString(1, email);
            ResultSet rs = emailStmt.executeQuery();

            // Sprawdzamy hasło, jeśli email istnieje
            if (rs.next()) {
                String correctPassword = rs.getString("password");
                String role = rs.getString("role");
                int userId = rs.getInt("user_id");
                if (correctPassword.equals(password)) {
                    // Zapisanie informacji o użytkowniku w zmiennych statycznych
                    InfoDisplay.email = email;
                    InfoDisplay.role = role;
                    DepositPage.email = email;
                    InfoDisplay.userId = userId;

                    // Przejście do odpowiedniej strony w zależności od roli
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    if ("admin".equalsIgnoreCase(role)) {
                        ViewPageController.goToAdminPage(stage);
                    } else {
                        ViewPageController.goToMainPage(stage);
                    }
                } else {
                    passwordError.setText("Niepoprawne hasło");
                    passwordError.setVisible(true);
                }
            } else {
                emailError.setText("Niepoprawny email");
                emailError.setVisible(true);
            }
        } catch (SQLException e) {
            passwordError.setText("Błąd połączenia z bazą danych: "+ e.getMessage());
            passwordError.setVisible(true);
        }
    }

    // Przejście do ekranu rejestracji
    @FXML
    public void gotoSignupPage() {
        Stage stage = (Stage) signupButton.getScene().getWindow();
        ViewPageController.goToSignupPage(stage);
    }

    // Metoda odpowiedzialna za zmianę widoczności hasła
    @FXML
    public void ChangePasswordVisibility(){
        if (showPasswordCheckBox.isSelected()) {
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
        } else {
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
        }
    }
}

