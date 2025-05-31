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
            stage.centerOnScreen();
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
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu rejestracji: " + e.getMessage());
        }
    }

    public static void goToMainPage(Stage stage) {
        //Przejście do ekranu głównego użytkownika
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/main_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu głównego użytkownika: " + e.getMessage());
        }
    }

    public static void goToAdminPage(Stage stage) {
        //Przejście do ekranu głównego administratora
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/admin_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu głównego administratora: " + e.getMessage());
        }
    }

    public static void goToReservationPage(Stage stage) {
        //Przejście do ekranu rezerwacji
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/reservation_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu rezerwacji: " + e.getMessage());
        }
    }

    public static void goToDepositPage(Stage stage) {
        //Przejście do ekranu wpłacania pieniędzy
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/deposit_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu wpłacania pieniędzy: " + e.getMessage());
        }
    }
}
