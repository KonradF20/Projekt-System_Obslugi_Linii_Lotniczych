package project.app.projektsystem_obslugi_linii_lotniczych;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewPageController {

    public static void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("login_page.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("FlyNow");
        stage.getIcons().add(new Image(Objects.requireNonNull(ViewPageController.class.getResourceAsStream("/images/Ikonka_FlyNow.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void goToLoginPage(Stage stage) {
        //Przejście do ekranu logowania
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/login_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu logowania: " + e.getMessage());
        }
    }

    public static void goToSignupPage(Stage stage) {
        //Przejście do ekranu rejestracji
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/signup_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu rejestracji: " + e.getMessage());
        }
    }

    public static void goToMainPage(Stage stage) {
        //Przejście do ekranu głównego
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/main_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu głównego: " + e.getMessage());
        }
    }

    public static void goToAdminPage(Stage stage) {
        //Przejście do panelu administratora
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/admin_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do panelu administratora: " + e.getMessage());
        }
    }
}
