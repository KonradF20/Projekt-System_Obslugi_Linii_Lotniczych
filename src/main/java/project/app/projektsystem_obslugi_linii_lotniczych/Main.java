package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.application.Application;
import javafx.stage.Stage;
import project.app.projektsystem_obslugi_linii_lotniczych.controllers.ViewPageController;

public class Main extends Application  {

    @Override
    public void start(Stage stage) throws Exception {
        ViewPageController.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}