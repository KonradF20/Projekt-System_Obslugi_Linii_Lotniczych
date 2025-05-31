package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AdminPage extends InfoDisplay{

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
}
