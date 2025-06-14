package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepositPage extends FocusController {

    // Elementy interfejsu GUI zdefiniowane w pliku FXML
    @FXML private TextField depositField;
    @FXML private Label depositError;
    @FXML private Button cancelButton;
    public static String email;

    // Metoda odpowiedzialna za doładowywanie salda zalogowanego użytkownika
    @FXML
    public void deposit() {
        // Pobranie danych z pola tekstowego
        String deposit = depositField.getText().trim();
        double balance;

        // Walidacja danych wejściowych kwoty
        if (deposit.isEmpty()){
            depositError.setText("Podaj kwotę");
            depositError.setVisible(true);
            return;
        }

        try {
            balance = Double.parseDouble(deposit);
        } catch (NumberFormatException e) {
            depositError.setText("Nieprawidłowy format liczby (np. 12.5)");
            depositError.setVisible(true);
            return;
        }

        if (balance <= 0) {
            depositError.setText("Wprowadź kwotę większą od zera");
            depositError.setVisible(true);
            return;
        }

        if (balance >= 100000000) {
            depositError.setText("Wprowadź mniejszą kwotę");
            depositError.setVisible(true);
            return;
        }

        // Aktualizacja salda użytkownika
        try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "UPDATE users SET balance = balance + ? WHERE email = ?";
            PreparedStatement balanceStmt = conn.prepareStatement(sql);
            balanceStmt.setDouble(1, balance);
            balanceStmt.setString(2, email);
            balanceStmt.executeUpdate();
            goToMainPage();
        } catch (SQLException e) {
            depositError.setText("Błąd przy aktualizacji salda: "+ e.getMessage());
            depositError.setVisible(true);
        }
    }

    // Przejście do ekranu głównego użytkownik
    @FXML
    public void goToMainPage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        ViewPageController.goToMainPage(stage);
    }
}
