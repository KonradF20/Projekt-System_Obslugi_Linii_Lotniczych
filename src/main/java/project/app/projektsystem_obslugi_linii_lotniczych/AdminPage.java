package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminPage extends InfoDisplay{

    @FXML private TextField flightNumberField;
    @FXML private TextField departureTimeField;
    @FXML private TextField arrivalTimeField;
    @FXML private TextField availableSeatsField;
    @FXML private TextField priceField;
    @FXML private TextField travelClassField;
    @FXML private TextField terminalField;
    @FXML private TextField gateField;
    @FXML private Button addFlightButton;
    @FXML private Button logoutButton;
    @FXML private Label infoLabel;

    @FXML
    public void initialize() {
        infoLabel.setText(InfoDisplay.display());
    }

    @FXML
    public void goToLoginPage() {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        ViewPageController.goToLoginPage(stage);
    }

    @FXML
    public void goToAdminPage() {
        Stage stage = (Stage) addFlightButton.getScene().getWindow();
        ViewPageController.goToAdminPage(stage);
    }

    @FXML
    public void FocusOn2(MouseEvent event) {
        flightNumberField.setFocusTraversable(true);
        availableSeatsField.setFocusTraversable(true);
        priceField.setFocusTraversable(true);
        departureTimeField.setFocusTraversable(true);
        arrivalTimeField.setFocusTraversable(true);
        travelClassField.setFocusTraversable(true);
        terminalField.setFocusTraversable(true);
        gateField.setFocusTraversable(true);
    }
}
