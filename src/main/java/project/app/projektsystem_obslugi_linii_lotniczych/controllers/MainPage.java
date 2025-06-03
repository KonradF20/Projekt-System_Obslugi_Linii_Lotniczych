package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.app.projektsystem_obslugi_linii_lotniczych.models.Airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends InfoDisplay {

    @FXML private ComboBox<Airport> chooseAirportComboBox;
    @FXML private Label errorLabel;
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
        List<Airport> airports = new ArrayList<>();

        try(Connection conn = DatabaseConnection.getConnection()) {
            String selectSql = "SELECT airport_id, name FROM airports";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("airport_id");
                String airportName = rs.getString("name");
                airports.add(new Airport(id, airportName));
            }
        }catch (SQLException e){
            errorLabel.setText("Błąd połączenia z bazą danych: "+ e.getMessage());
        }

        ObservableList<Airport> airportOptions = FXCollections.observableArrayList(airports);
        chooseAirportComboBox.setItems(airportOptions);
        chooseAirportComboBox.getSelectionModel().select(0);
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
