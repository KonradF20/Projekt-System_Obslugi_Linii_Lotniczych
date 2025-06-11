package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

//Kontroler do zarządzania przejściami między widokami w aplikacji FlyNow

public class ViewPageController {

    //Uruchomienie głównego okna aplikacji i załadowanie ekranu logowania

    public static void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/login_page.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("FlyNow");
        stage.getIcons().add(new Image(Objects.requireNonNull(ViewPageController.class.getResourceAsStream("/images/Ikonka_FlyNow.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    //Przejście do ekranu logowania

    public static void goToLoginPage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/login_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu logowania: " + e.getMessage());
        }
    }

    //Przejście do ekranu rejestracji

    public static void goToSignupPage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/signup_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu rejestracji: " + e.getMessage());
        }
    }

    //Przejście do ekranu głównego użytkownika

    public static void goToMainPage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/main_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu głównego użytkownika: " + e.getMessage());
        }
    }

    //Przejście do ekranu głównego administratora

    public static void goToAdminPage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/admin_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu głównego administratora: " + e.getMessage());
        }
    }

    //Przejście do ekranu rezerwacji

    public static void goToReservationPage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/reservation_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu rezerwacji: " + e.getMessage());
        }
    }

    //Przejście do ekranu wpłacania pieniędzy

    public static void goToDepositPage(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/deposit_page.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu wpłacania pieniędzy: " + e.getMessage());
        }
    }

    //Przejście do ekranu kupowania biletu z przekazaniem zmiennych

    public static void goToTicketPage(Stage stage, int selectedFlightId, double flightPrice, double userBalance) {
        try {
            FXMLLoader loader = new FXMLLoader(ViewPageController.class.getResource("/project/app/projektsystem_obslugi_linii_lotniczych/ticket_page.fxml"));
            Parent root = loader.load();
            TicketPage controller = loader.getController();
            controller.setFlightId(selectedFlightId);
            controller.setFlightPrice(flightPrice);
            controller.setUserBalance(userBalance);
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println("Błąd podczas przejścia do ekranu kupowania biletu: " + e.getMessage());
        }
    }
}
