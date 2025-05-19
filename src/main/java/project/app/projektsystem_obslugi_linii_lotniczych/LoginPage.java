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

public class LoginPage extends FocusController{

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private Label emailError;
    @FXML private Label passwordError;
    @FXML private Button loginButton;
    @FXML private Button signupButton;

    @FXML
    public void loginUser() {
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
        } else if (!email.contains("@")) {
            emailError.setText("Email musi zawierać '@'");
            emailError.setVisible(true);
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
            String checkEmailQuery = "SELECT password, role FROM users WHERE email = ?";
            PreparedStatement emailStmt = conn.prepareStatement(checkEmailQuery);
            emailStmt.setString(1, email);
            ResultSet rs = emailStmt.executeQuery();

            if (rs.next()) {
                String correctPassword = rs.getString("password");
                String role = rs.getString("role");
                if (correctPassword.equals(password)) {
                    InfoDisplay.email = email;
                    InfoDisplay.role = role;
                    if ("admin".equalsIgnoreCase(role)) {
                        goToAdminPage();
                    } else {
                        goToMainPage();
                    }
                } else {
                    passwordError.setText("Niepoprawne hasło");
                    passwordError.setVisible(true);
                }
            } else {
                emailError.setText("Niepoprawny email");
                emailError.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSignupPage() {
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

    public void goToMainPage() {
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

    public void goToAdminPage() {
        //Przejście do widoku admina
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/app/projektsystem_obslugi_linii_lotniczych/admin_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

