package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketPage extends InfoDisplay {

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
    @FXML private Button cancelButton;
    @FXML private Label infoLabel;
    @FXML private Label addInfo;

    @FXML
    public void initialize() {
        infoLabel.setText(InfoDisplay.display());
        List<String> documents = new ArrayList<>();
        documents.add("Dowód osobisty");
        documents.add("Paszport");
        List<String> luggage = new ArrayList<>();
        luggage.add("Rejestrowany");
        luggage.add("Podręczny");

        ObservableList<String> documentOptions = FXCollections.observableArrayList(documents);
        documentComboBox.setItems(documentOptions);
        documentComboBox.getSelectionModel().select(0);

        ObservableList<String> baggageOptions = FXCollections.observableArrayList(luggage);
        baggageComboBox.setItems(baggageOptions);
        baggageComboBox.getSelectionModel().select(0);
    }

    @FXML
    public void buyTicket(){
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String birthDate = birthDateField.getText().trim();
        String country = countryField.getText().trim();
        String city = cityField.getText().trim();
        String street = streetField.getText().trim();
        String postalCode = postalCodeField.getText().trim();
        String phone = phoneField.getText().trim();
        String documentType = documentComboBox.getSelectionModel().getSelectedItem();
        String baggageType = baggageComboBox.getSelectionModel().getSelectedItem();
        String weight = weightField.getText().trim();
        String dimensions = dimensionsField.getText().trim();
        double baggageWeight;

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
            String passengersInsertSql = "INSERT INTO passengers (first_name, last_name, date_of_birth, street, city, postal_code, country, phone, document_type, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            passengersInsertStmt.executeUpdate();

            ResultSet passenger_rs = passengersInsertStmt.getGeneratedKeys();
            if (!passenger_rs.next()) {
                addInfo.setText("Błąd przy dodawaniu pasażera");
                return;
            }

            int passengerId = passenger_rs.getInt(1);

            String baggageInsertSql = "INSERT INTO baggage (weight, dimensions, type) VALUES (?, ?, ?)";
            PreparedStatement baggageInsertStmt = conn.prepareStatement(baggageInsertSql);
            baggageInsertStmt.setDouble(1, baggageWeight);
            baggageInsertStmt.setString(2, dimensions);
            baggageInsertStmt.setString(3, baggageType);
            baggageInsertStmt.executeUpdate();

            //int flightId = InfoDisplay.getSelectedFlightId();

            String reservationsInsertSql = "INSERT INTO reservations (passenger_id, flight_id) VALUES (?, ?)";
            PreparedStatement reservationsInsertStmt = conn.prepareStatement(reservationsInsertSql);
            reservationsInsertStmt.setInt(1, passengerId);
           // reservationsInsertStmt.setInt(2, flightId);
            reservationsInsertStmt.executeUpdate();
            addInfo.setText("Pomyślnie zarezerwowano lot!");
            clearFields();
        } catch (SQLException e) {
            addInfo.setText("Błąd podczas rezerwacji lotu: " + e.getMessage());
        }
    }

    @FXML
    public void goToMainPage() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        ViewPageController.goToMainPage(stage);
    }

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
    }

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
