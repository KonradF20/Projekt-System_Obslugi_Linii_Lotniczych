package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.app.projektsystem_obslugi_linii_lotniczych.models.Airport;
import project.app.projektsystem_obslugi_linii_lotniczych.models.Flights;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends InfoDisplay {

    @FXML private ComboBox<Airport> chooseAirportComboBox;
    @FXML private Label errorLabel;
    @FXML private Button reservationButton;
    @FXML private Button reserveButton0;
    @FXML private Button reserveButton1;
    @FXML private Button reserveButton2;
    @FXML private Button reserveButton3;
    @FXML private Button reserveButton4;
    @FXML private Label balanceShow;
    @FXML private Button depositButton;
    @FXML private Label iataCode;
    @FXML private Label flightsInfo;
    @FXML private Label iataCode1;
    @FXML private Label flightsInfo1;
    @FXML private Label iataCode2;
    @FXML private Label flightsInfo2;
    @FXML private Label iataCode3;
    @FXML private Label flightsInfo3;
    @FXML private Label iataCode4;
    @FXML private Label flightsInfo4;
    @FXML private Button logoutButton;
    @FXML private Label infoLabel;
    double Balance;
    List<Label> iataCodes = new ArrayList<>();
    List<Label> flightsInfos = new ArrayList<>();
    List<Button> reserveButtons = new ArrayList<>();
    List<Flights> flights = new ArrayList<>();
    List<Flights> flightsToShow = new ArrayList<>();

    @FXML
    public void initialize() {
        infoLabel.setText(InfoDisplay.display());
        showBalance();
        List<Airport> airports = new ArrayList<>();
        iataCodes.add(iataCode);
        iataCodes.add(iataCode1);
        iataCodes.add(iataCode2);
        iataCodes.add(iataCode3);
        iataCodes.add(iataCode4);
        flightsInfos.add(flightsInfo);
        flightsInfos.add(flightsInfo1);
        flightsInfos.add(flightsInfo2);
        flightsInfos.add(flightsInfo3);
        flightsInfos.add(flightsInfo4);
        reserveButtons.add(reserveButton0);
        reserveButtons.add(reserveButton1);
        reserveButtons.add(reserveButton2);
        reserveButtons.add(reserveButton3);
        reserveButtons.add(reserveButton4);

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
        selectFlights();
    }

    @FXML
    public void selectFlights() {
        Airport selectedAirport = chooseAirportComboBox.getSelectionModel().getSelectedItem();
        flights.clear();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String selectFlightsSql = "SELECT f.flight_id, da.iata_code AS departure_iata_code, da.city AS departure_city, f.departure_time, aa.iata_code AS arrival_iata_code, aa.city AS arrival_city, f.arrival_time, f.available_seats, f.price, f.travel_class FROM flights AS f JOIN airports AS da ON f.departure_airport_id = da.airport_id JOIN airports AS aa ON f.arrival_airport_id = aa.airport_id WHERE f.departure_airport_id = ?";
            PreparedStatement selectFlightsStmt = conn.prepareStatement(selectFlightsSql);
            selectFlightsStmt.setInt(1, selectedAirport.getAirport_id());
            ResultSet rs = selectFlightsStmt.executeQuery();

            while (rs.next()) {
                Flights flight = new Flights();
                flight.setFlight_id(rs.getInt("flight_id"));
                flight.setDeparture_iata_code(rs.getString("departure_iata_code"));
                flight.setDeparture_city(rs.getString("departure_city"));
                flight.setDeparture_time(rs.getTimestamp("departure_time").toLocalDateTime());
                flight.setArrival_iata_code(rs.getString("arrival_iata_code"));
                flight.setArrival_city(rs.getString("arrival_city"));
                flight.setArrival_time(rs.getTimestamp("arrival_time").toLocalDateTime());
                flight.setAvailable_seats(rs.getInt("available_seats"));
                flight.setPrice(rs.getDouble("price"));
                flight.setTravel_class(rs.getString("travel_class"));
                flights.add(flight);
            }
            showFlights(flights);
        } catch (SQLException e) {
            errorLabel.setText("Błąd podczas pobierania lotów: " + e.getMessage());
        }
    }

    @FXML
    public void showFlights(List<Flights> flightsToShow) {
        this.flightsToShow = new ArrayList<>();
        for (int i = 0; i < iataCodes.size(); i++){
            iataCodes.get(i).setText("");
            flightsInfos.get(i).setText("");
            reserveButtons.get(i).setVisible(false);
        }

        int a = 0;

        for (int i = 0; i < flightsToShow.size() && i < 5; i++) {
            Flights flight = flightsToShow.get(i);
            String flightInfoText = flight.toString();
            String iataCodeText = flight.getDeparture_iata_code();
            if (flight.getDeparture_time().isAfter(LocalDateTime.now())) {
                this.flightsToShow.add(flight);
                iataCodes.get(a).setText(iataCodeText);
                flightsInfos.get(a).setText(flightInfoText);
                reserveButtons.get(a).setVisible(true);
                a++;
            } else {
                iataCodes.get(a).setText("");
                flightsInfos.get(a).setText("");
                reserveButtons.get(a).setVisible(false);
            }
        }
    }

    @FXML
    public void showBalance() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String selectBalanceSql = "SELECT balance FROM users WHERE email = ?";
            PreparedStatement balanceStmt = conn.prepareStatement(selectBalanceSql);
            balanceStmt.setString(1, email);
            ResultSet rs = balanceStmt.executeQuery();

            if (rs.next()) {
                balanceShow.setText(rs.getString("balance")+ " zł");
                Balance = rs.getDouble("balance");
            }
        } catch (SQLException e) {
            errorLabel.setText("Błąd podczas pobierania salda: " + e.getMessage());
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
    public void goToTicketPage(ActionEvent actionEvent) {
        Button clikedButton = (Button)actionEvent.getSource();
        char c = clikedButton.getId().charAt(clikedButton.getId().length()-1);
        int index = Integer.parseInt(String.valueOf(c));
        int selectedFlightId = flightsToShow.get(index).getFlight_id();
        double flightPrice = flightsToShow.get(index).getPrice();
        double userBalance = Balance;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String selectReservationSql = "SELECT reservation_id FROM passengers WHERE user_id = ?";
            PreparedStatement reservationStmt = conn.prepareStatement(selectReservationSql);
            reservationStmt.setInt(1, userId);
            ResultSet rs = reservationStmt.executeQuery();
            rs.next();
            int reservation = rs.getInt(1);
            if (reservation > 0) {
                errorLabel.setText("Już posiadasz aktywną rezerwację");
                return;
            }
        } catch (SQLException e) {
            errorLabel.setText("Błąd przy sprawdzaniu rezerwacji: " + e.getMessage());
        }

        if (Balance < flightsToShow.get(index).getPrice()) {
            errorLabel.setText("Za mało środków na koncie");
            return;
        }

        Stage stage = (Stage) reserveButton0.getScene().getWindow();
        ViewPageController.goToTicketPage(stage, selectedFlightId, flightPrice, userBalance);
    }
}
