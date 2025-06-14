package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.app.projektsystem_obslugi_linii_lotniczych.models.Airport;
import project.app.projektsystem_obslugi_linii_lotniczych.models.Plane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminPage extends InfoDisplay {

    // Elementy interfejsu GUI zdefiniowane w pliku FXML
    @FXML public ComboBox<String> travelClassComboBox;
    @FXML public ComboBox<Airport> departureAirportComboBox;
    @FXML public ComboBox<Airport> arrivalAirportComboBox;
    @FXML public ComboBox<Plane> planeComboBox;
    @FXML private TextField flightNumberField;
    @FXML private TextField departureTimeField;
    @FXML private TextField arrivalTimeField;
    @FXML private TextField priceField;
    @FXML private TextField terminalField;
    @FXML private TextField gateField;
    @FXML private Button logoutButton;
    @FXML private Label infoLabel;
    @FXML private Label addInfo;

    // Metoda odpowiedzialna za inicjalizacje strony administratora
    @FXML
    public void initialize() {
        // Wyświetlanie informacji o zalogowanym administratorze
        infoLabel.setText(InfoDisplay.display());

        // Listy do przechowywania informacji o lotniskach, samolotach i klasach podróży
        List<Airport> airports = new ArrayList<>();
        List<Plane> planes = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        classes.add("Economy");
        classes.add("Business");
        classes.add("First");

        // Pobieranie dostępnych lotnisk z bazy danych
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
            addInfo.setText("Błąd połączenia z bazą danych: "+ e.getMessage());
        }

        // Pobieranie dostępnych samolotów z bazy danych
        try(Connection conn = DatabaseConnection.getConnection()) {
            String selectSql = "SELECT * FROM planes";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("plane_id");
                String model = rs.getString("model");
                int capacity = rs.getInt("capacity");
                planes.add(new Plane(id, model, capacity));
            }
        }catch (SQLException e){
            addInfo.setText("Błąd połączenia z bazą danych: "+ e.getMessage());
        }

        // Ustawienie wybierania lotnisk z ComboBoxa
        ObservableList<Airport> airportOptions = FXCollections.observableArrayList(airports);
        departureAirportComboBox.setItems(airportOptions);
        departureAirportComboBox.getSelectionModel().select(0);
        arrivalAirportComboBox.setItems(airportOptions);
        arrivalAirportComboBox.getSelectionModel().select(0);

        // Ustawienie wybierania samolotów z ComboBoxa
        ObservableList<Plane> planeOptions = FXCollections.observableArrayList(planes);
        planeComboBox.setItems(planeOptions);
        planeComboBox.getSelectionModel().select(0);

        // Ustawienie wybierania klasy podróży z ComboBoxa
        ObservableList<String> classOptions = FXCollections.observableArrayList(classes);
        travelClassComboBox.setItems(classOptions);
        travelClassComboBox.getSelectionModel().select(0);
    }

    // Metoda odpowiedzialna za dodawanie nowego lotu
    @FXML
    public void addFlight() {
        // Pobranie danych z formularza
        String flightNumber = flightNumberField.getText().trim();
        int departureId = departureAirportComboBox.getSelectionModel().getSelectedItem().getAirport_id();
        int arrivalId = arrivalAirportComboBox.getSelectionModel().getSelectedItem().getAirport_id();
        String departureTime = departureTimeField.getText().trim();
        String arrivalTime = arrivalTimeField.getText().trim();
        int planeId = planeComboBox.getSelectionModel().getSelectedItem().getPlane_id();
        String price = priceField.getText().trim();
        String travelClass = travelClassComboBox.getSelectionModel().getSelectedItem();
        String terminal = terminalField.getText().trim();
        String gate = gateField.getText().trim();
        double ticketPrice;

        // Walidacja danych wejściowych
        if (flightNumber.isEmpty() || departureTime.isEmpty() || arrivalTime.isEmpty() || price.isEmpty() || terminal.isEmpty() || gate.isEmpty()) {
            addInfo.setText("Uzupełnij wszystkie pola");
            return;
        }

        try {
            ticketPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            addInfo.setText("Nieprawidłowa cena (np. 12.5)");
            return;
        }

        if (ticketPrice <= 0) {
            addInfo.setText("Cena biletu musi być większą od zera");
            return;
        }

        if (ticketPrice >= 100000000) {
            addInfo.setText("Cena biletu jest zbyt wysoka");
            return;
        }

        if (!departureTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}") || !arrivalTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
            addInfo.setText("Nieprawidłowy format daty");
            return;
        }

        if (!terminal.matches("[A-Z]")) {
            addInfo.setText("Nieprawidłowy terminal (np. A, B)");
            return;
        }

        if (!gate.matches("\\d{1,2}")) {
            addInfo.setText("Nieprawidłowy gate (np. 7, 13)");
            return;
        }

        if (departureId == arrivalId) {
            addInfo.setText("Lotnisko wylotu i przylotu nie może być takie samo");
            return;
        }

        // Dodanie lotu
        try (Connection conn = DatabaseConnection.getConnection()){
            String insertSql = "INSERT INTO flights (flight_number, departure_airport_id, arrival_airport_id, departure_time, arrival_time, plane_id, price, travel_class, terminal, gate) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, flightNumber);
            insertStmt.setInt(2, departureId);
            insertStmt.setInt(3, arrivalId);
            insertStmt.setString(4, departureTime);
            insertStmt.setString(5, arrivalTime);
            insertStmt.setInt(6, planeId);
            insertStmt.setString(7, price);
            insertStmt.setString(8, travelClass);
            insertStmt.setString(9, terminal);
            insertStmt.setString(10, gate);
            insertStmt.executeUpdate();
            addInfo.setText("Lot dodany pomyślnie!");
            clearFields();
        } catch (SQLException e) {
            addInfo.setText("Błąd podczas dodawania lotu: " + e.getMessage());
        }
    }

    // Przejście do ekranu logowania
    @FXML
    public void goToLoginPage() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        ViewPageController.goToLoginPage(stage);
    }

    // Metoda odpowiedzialna za czyszczenie wszystkich pól formularza
    @FXML
    public void clearFields() {
        flightNumberField.clear();
        departureTimeField.clear();
        arrivalTimeField.clear();
        priceField.clear();
        terminalField.clear();
        gateField.clear();
        departureAirportComboBox.getSelectionModel().select(0);
        arrivalAirportComboBox.getSelectionModel().select(0);
        planeComboBox.getSelectionModel().select(0);
        travelClassComboBox.getSelectionModel().select(0);
    }

    // Metoda odpowiedzialna za aktywację fokusu pól formularza po kliknięciu
    @FXML
    public void FocusOn2(MouseEvent event) {
        flightNumberField.setFocusTraversable(true);
        priceField.setFocusTraversable(true);
        departureTimeField.setFocusTraversable(true);
        arrivalTimeField.setFocusTraversable(true);
        terminalField.setFocusTraversable(true);
        gateField.setFocusTraversable(true);
    }
}
