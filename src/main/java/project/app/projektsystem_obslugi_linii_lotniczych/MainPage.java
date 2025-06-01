package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPage extends InfoDisplay{

    @FXML private Button reservationButton;
    @FXML private Button reserveButton;
    @FXML private Label balanceShow;
    @FXML private Button depositButton;
    @FXML private Button logoutButton;
    @FXML private Label infoLabel;
    public static String email;

    @FXML
    public void initialize() {
        infoLabel.setText(InfoDisplay.display());
        showBalance();
    }

    public void showBalance() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT balance FROM users WHERE email = ?";
            PreparedStatement balanceStmt = conn.prepareStatement(sql);
            balanceStmt.setString(1, email);
            ResultSet rs = balanceStmt.executeQuery();

            if (rs.next()) {
                balanceShow.setText(rs.getString("balance")+ " zł");
            }
        } catch (SQLException e) {
            balanceShow.setText("Błąd podczas pobierania salda: " + e.getMessage());
        }
    }

    @FXML
    public void goToLoginPage() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        ViewPageController.goToLoginPage(stage);
    }

    @FXML
    public void goToReservationPage() {
        Stage stage = (Stage) reservationButton.getScene().getWindow();
        ViewPageController.goToReservationPage(stage);
    }

    @FXML
    public void goToDepositPage() {
        Stage stage = (Stage) depositButton.getScene().getWindow();
        ViewPageController.goToDepositPage(stage);
    }

    @FXML
    public void goToTicketPage() {
        Stage stage = (Stage) reserveButton.getScene().getWindow();
        ViewPageController.goToTicketPage(stage);
    }
}
