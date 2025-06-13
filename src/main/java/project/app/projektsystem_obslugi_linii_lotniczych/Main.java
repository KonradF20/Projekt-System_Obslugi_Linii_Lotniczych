package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.application.Application;
import javafx.stage.Stage;
import project.app.projektsystem_obslugi_linii_lotniczych.controllers.ViewPageController;

// Klasa główna aplikacji — punkt startu programu

public class Main extends Application  {

    // Nadpisana metoda odpowiedzialna za załadowanie początkowego widoku aplikacji
    @Override
    public void start(Stage stage) throws Exception {
        ViewPageController.start(stage);
    }

    // Metoda odpowiedzialna za uruchomienie aplikacji
    public static void main(String[] args) {
        launch(args);
    }
}