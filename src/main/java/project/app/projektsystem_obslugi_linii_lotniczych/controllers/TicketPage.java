package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class TicketPage extends InfoDisplay {

    // Elementy interfejsu GUI zdefiniowane w pliku FXML
    @FXML private ComboBox<String> documentComboBox;
    @FXML private ComboBox<String> baggageComboBox;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField birthDateField;
    @FXML private TextField countryField;
    @FXML private TextField cityField;
    @FXML private TextField streetField;
    @FXML private TextField postalCodeField;
    @FXML private TextField phoneField;
    @FXML private TextField weightField;
    @FXML private TextField dimensionsField;
    @FXML private Button buyButton;
    @FXML private Button cancelButton;
    @FXML private Label infoLabel;
    @FXML private Label addInfo;
    private int flightId;
    private double flightPrice;
    private double userBalance;

    // Mapy, które przekształcają widoczne etykiety na wartości przechowywane w bazie danych
    Map<String, String> documentMap = new HashMap<>();
    Map<String, String> bagaggeMap = new HashMap<>();

    // Metoda odpowiedzialna za inicjalizacje strony kupowania biletu
    @FXML
    public void initialize() {
        // Wyświetlanie informacji o zalogowanym użytkowniku
        infoLabel.setText(InfoDisplay.display());

        // Inicjowanie mapy dokumentów i bagażu
        documentMap.put("Dowód osobisty", "ID_CARD");
        documentMap.put("Paszport", "PASSPORT");
        bagaggeMap.put("Rejestrowany","registered");
        bagaggeMap.put("Podręczny","handy");

        // Ustawienie wybierania dokumentów z ComboBoxa
        ObservableList<String> documentOptions = FXCollections.observableArrayList(documentMap.keySet());
        documentComboBox.setItems(documentOptions);
        documentComboBox.getSelectionModel().select(0);

        // Ustawienie wybierania bagażu z ComboBoxa
        ObservableList<String> baggageOptions = FXCollections.observableArrayList(bagaggeMap.keySet());
        baggageComboBox.setItems(baggageOptions);
        baggageComboBox.getSelectionModel().select(0);
    }

    // Settery przekazujące dane z poprzedniego widoku
    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setFlightPrice(double flightPrice) {
        this.flightPrice = flightPrice;
    }

    public void setUserBalance(double userBalance) {
        this.userBalance = userBalance;
    }

    // Metoda odpowiedzialna za kupowanie biletu
    @FXML
    public void buyTicket() {
        // Pobranie danych z formularza
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String birthDate = birthDateField.getText().trim();
        String country = countryField.getText().trim();
        String city = cityField.getText().trim();
        String street = streetField.getText().trim();
        String postalCode = postalCodeField.getText().trim();
        String phone = phoneField.getText().trim();
        String documentTypeLabel = documentComboBox.getSelectionModel().getSelectedItem();
        String documentType = documentMap.get(documentTypeLabel);
        String baggageTypeLabel = baggageComboBox.getSelectionModel().getSelectedItem();
        String baggageType = bagaggeMap.get(baggageTypeLabel);
        String weight = weightField.getText().trim();
        String dimensions = dimensionsField.getText().trim();
        double baggageWeight;

        // Walidacja danych wejściowych
        if (firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || country.isEmpty() || city.isEmpty() || street.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || weight.isEmpty() || dimensions.isEmpty()) {
            addInfo.setText("Uzupełnij wszystkie pola");
            return;
        }

        try {
            baggageWeight = Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            addInfo.setText("Nieprawidłowa waga bagażu (np. 12.5)");
            return;
        }

        if (baggageWeight <= 0) {
            addInfo.setText("Waga bagażu jest zbyt mała");
            return;
        }

        if (baggageWeight >= 32) {
            addInfo.setText("Waga bagażu jest zbyt duża");
            return;
        }

        if (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            addInfo.setText("Nieprawidłowy format daty (YYYY-MM-DD)");
            return;
        }

        if (!postalCode.matches("\\d{2}-\\d{3}")) {
            addInfo.setText("Nieprawidłowy kod pocztowy (np. 38-300)");
            return;
        }

        if (!phone.matches("\\d{9}")) {
            addInfo.setText("Numer telefonu musi zawierać 9 cyfr");
            return;
        }

        if (!dimensions.matches("\\d{1,3}x\\d{1,3}x\\d{1,3}")) {
            addInfo.setText("Nieprawidłowe wymiary bagażu (np. 40x20x25)");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Dodanie rezerwacji
            String reservationsInsertSql = "INSERT INTO reservations (flight_id) VALUES (?)";
            PreparedStatement reservationsInsertStmt = conn.prepareStatement(reservationsInsertSql, Statement.RETURN_GENERATED_KEYS);
            reservationsInsertStmt.setInt(1, flightId);
            reservationsInsertStmt.executeUpdate();
            ResultSet reservation_rs = reservationsInsertStmt.getGeneratedKeys();
            if (!reservation_rs.next()) {
                addInfo.setText("Błąd przy dodawaniu rezerwacji");
                return;
            }

            int reservationId = reservation_rs.getInt(1);

            // Dodanie pasażera do rezerwacji
            String passengersInsertSql = "INSERT INTO passengers (first_name, last_name, date_of_birth, street, city, postal_code, country, phone, document_type, user_id, reservation_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement passengersInsertStmt = conn.prepareStatement(passengersInsertSql);
            passengersInsertStmt.setString(1, firstName);
            passengersInsertStmt.setString(2, lastName);
            passengersInsertStmt.setString(3, birthDate);
            passengersInsertStmt.setString(4, street);
            passengersInsertStmt.setString(5, city);
            passengersInsertStmt.setString(6, postalCode);
            passengersInsertStmt.setString(7, country);
            passengersInsertStmt.setString(8, phone);
            passengersInsertStmt.setString(9, documentType);
            passengersInsertStmt.setInt(10, userId);
            passengersInsertStmt.setInt(11, reservationId);
            passengersInsertStmt.executeUpdate();

            // Dodanie bagażu do rezerwacji
            String baggageInsertSql = "INSERT INTO baggage (weight, dimensions, type, reservation_id) VALUES (?, ?, ?, ?)";
            PreparedStatement baggageInsertStmt = conn.prepareStatement(baggageInsertSql);
            baggageInsertStmt.setDouble(1, baggageWeight);
            baggageInsertStmt.setString(2, dimensions);
            baggageInsertStmt.setString(3, baggageType);
            baggageInsertStmt.setInt(4, reservationId);
            baggageInsertStmt.executeUpdate();

            double newBalance = userBalance - flightPrice;

            // Aktualizacja salda użytkownika
            String balanceUpdateSql = "UPDATE users SET balance = ? WHERE user_id = ?";
            PreparedStatement balanceUpdateStmt = conn.prepareStatement(balanceUpdateSql);
            balanceUpdateStmt.setDouble(1, newBalance);
            balanceUpdateStmt.setInt(2, userId);
            balanceUpdateStmt.executeUpdate();

            addInfo.setText("Pomyślnie zarezerwowano lot!");
            clearFields();
        } catch (SQLException e) {
            addInfo.setText("Błąd podczas rezerwacji lotu: " + e.getMessage());
        }
    }

    // Przejście do ekranu głównego użytkownika
    @FXML
    public void goToMainPage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        ViewPageController.goToMainPage(stage);
    }

    // Metoda odpowiedzialna za czyszczenie wszystkich pól formularza
    @FXML
    public void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        birthDateField.clear();
        countryField.clear();
        cityField.clear();
        streetField.clear();
        postalCodeField.clear();
        phoneField.clear();
        weightField.clear();
        dimensionsField.clear();
        documentComboBox.getSelectionModel().select(0);
        baggageComboBox.getSelectionModel().select(0);
        buyButton.setVisible(false);
    }

    // Metoda odpowiedzialna za aktywację fokusu pól formularza po kliknięciu
    @FXML
    public void FocusOn3(MouseEvent event) {
        firstNameField.setFocusTraversable(true);
        lastNameField.setFocusTraversable(true);
        birthDateField.setFocusTraversable(true);
        countryField.setFocusTraversable(true);
        cityField.setFocusTraversable(true);
        streetField.setFocusTraversable(true);
        postalCodeField.setFocusTraversable(true);
        phoneField.setFocusTraversable(true);
        weightField.setFocusTraversable(true);
        dimensionsField.setFocusTraversable(true);
    }
}
