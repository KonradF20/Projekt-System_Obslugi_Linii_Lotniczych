package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReservationPage extends InfoDisplay {

    // Elementy interfejsu GUI zdefiniowane w pliku FXML
    @FXML private Button flightsButton;
    @FXML private Label errorLabel;
    @FXML private VBox boardingPass;
    @FXML private Label passengerName;
    @FXML private Label departureCity;
    @FXML private Label arrivalCity;
    @FXML private Label flightNumber;
    @FXML private Label travelClass;
    @FXML private Label departureDate;
    @FXML private Label departureTime;
    @FXML private Label terminal;
    @FXML private Label gate;
    @FXML private HBox reservationInfo;
    @FXML private Button logoutButton;
    @FXML private Label infoLabel;
    private int reservationId;

    // Metoda odpowiedzialna za inicjalizacje strony rezerwacji
    @FXML
    public void initialize() {
        // Wyświetlanie informacji o zalogowanym użytkowniku
        infoLabel.setText(InfoDisplay.display());
        showBoardingPass();
    }

    // Metoda odpowiedzialna za wyświetlanie danych rezerwacji
    @FXML
    public void showBoardingPass(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Pobranie danych rezerwacji dla zalogowanego użytkownika
            String selectReservationInfoSql = "SELECT r.reservation_id, p.first_name, p.last_name, da.city AS departure_city, aa.city AS arrival_city, f.flight_number, f.travel_class, f.departure_time, f.terminal, f.gate FROM reservations r JOIN passengers p ON p.reservation_id = r.reservation_id JOIN flights f ON f.flight_id = r.flight_id JOIN airports da ON f.departure_airport_id = da.airport_id JOIN airports aa ON f.arrival_airport_id = aa.airport_id WHERE p.user_id = ? AND r.status = 'confirmed'";
            PreparedStatement reservationInfoStmt = conn.prepareStatement(selectReservationInfoSql);
            reservationInfoStmt.setInt(1, userId);
            ResultSet rs = reservationInfoStmt.executeQuery();

            if (rs.next()) {
                // Wyświetlanie danych pasażera i lotu
                passengerName.setText(rs.getString("first_name") + " " + rs.getString("last_name"));
                departureCity.setText(rs.getString("departure_city"));
                arrivalCity.setText(rs.getString("arrival_city"));
                flightNumber.setText(rs.getString("flight_number"));
                travelClass.setText(rs.getString("travel_class"));
                LocalDateTime departureDateTime = rs.getTimestamp("departure_time").toLocalDateTime();

                // Usuwanie rezerwacji, jeśli lot już się odbył
                if (departureDateTime.isBefore(LocalDateTime.now())) {
                    int expiredReservationId = rs.getInt("reservation_id");
                    String deleteReservationSql = "DELETE FROM reservations WHERE reservation_id = ?";
                    PreparedStatement deleteReservationStmt = conn.prepareStatement(deleteReservationSql);
                    deleteReservationStmt.setInt(1, expiredReservationId);
                    deleteReservationStmt.executeUpdate();
                    errorLabel.setText("Rezerwacja anulowana (lot już się odbył)");
                    boardingPass.setVisible(false);
                    reservationInfo.setVisible(false);
                    return;
                }
                departureDate.setText(departureDateTime.toLocalDate().format(dateFormatter));
                departureTime.setText(departureDateTime.toLocalTime().format(timeFormatter));
                terminal.setText(rs.getString("terminal"));
                gate.setText(rs.getString("gate"));
                reservationId = rs.getInt("reservation_id");
                boardingPass.setVisible(true);
                reservationInfo.setVisible(true);
            } else {
                errorLabel.setText("Brak aktywnej rezerwacji");
                boardingPass.setVisible(false);
                reservationInfo.setVisible(false);
            }
        } catch (SQLException e) {
            errorLabel.setText("Błąd podczas pobierania danych rezerwacji: " + e.getMessage());
        }
    }

    // Metoda odpowiedzialna za anulowanie rezerwacji przez użytkownika
    @FXML
    public void cancelReservation(){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String deleteReservationSql = "DELETE FROM reservations WHERE reservation_id = ?";
            PreparedStatement deleteReservationStmt = conn.prepareStatement(deleteReservationSql);
            deleteReservationStmt.setInt(1,reservationId);
            deleteReservationStmt.executeUpdate();
            showBoardingPass();
            errorLabel.setText("Rezerwacja została pomyślnie anulowana");
        } catch (SQLException e) {
            errorLabel.setText("Błąd podczas usuwania rezerwacji: " + e.getMessage());
        }
    }

    // Przejście do ekranu logowania
    @FXML
    public void goToLoginPage() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        ViewPageController.goToLoginPage(stage);
    }

    // Przejście do ekranu głównego użytkownika
    @FXML
    public void goToMainPage() {
        Stage stage = (Stage) flightsButton.getScene().getWindow();
        ViewPageController.goToMainPage(stage);
    }
}
