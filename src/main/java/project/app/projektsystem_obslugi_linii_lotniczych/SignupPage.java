package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupPage extends FocusController{

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Button cancelButton;

    @FXML
    public void addUser() {
        String email = emailField.getText().trim();
        String password;
        if (showPasswordCheckBox.isSelected()){
            password = visiblePasswordField.getText();
        } else {
            password = passwordField.getText();
        }

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

        if (password.isEmpty()) {
            passwordError.setText("Hasło nie może być puste");
            passwordError.setVisible(true);
            return;
        } else {
            passwordError.setVisible(false);
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Sprawdzenie, czy email już istnieje
            String checkEmailQuery = "SELECT * FROM users WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                emailError.setText("Email już istnieje");
                emailError.setVisible(true);
            } else {
                // Dodanie nowego użytkownika
                String insertQuery = "INSERT INTO users (email, password) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, email);
                insertStmt.setString(2, password);
                insertStmt.executeUpdate();
                gotoLoginPage();
            }
        } catch (SQLException e) {
            passwordError.setText("Błąd połączenia z bazą danych: "+ e.getMessage());
            passwordError.setVisible(true);
        }
    }

    @FXML
    public void gotoLoginPage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        ViewPageController.goToLoginPage(stage);
    }

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
