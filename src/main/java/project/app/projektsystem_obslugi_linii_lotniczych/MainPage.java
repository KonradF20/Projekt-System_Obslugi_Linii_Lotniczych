package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainPage extends InfoDisplay{

    @FXML private Label infoLabel;

    @FXML
    public void initialize() {
        infoLabel.setText(InfoDisplay.display());
    }
}
