package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_page.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("FlyNow");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Ikonka_FlyNow.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}