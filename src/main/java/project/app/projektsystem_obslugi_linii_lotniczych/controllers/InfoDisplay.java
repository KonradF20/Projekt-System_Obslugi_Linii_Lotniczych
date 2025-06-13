package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Klasa służąca do przechowywania i wyświetlania podstawowych informacji o aktualnie zalogowanym użytkowniku

public class InfoDisplay {

    // informacje o aktualnie zalogowanym użytkowniku
    public static String email;
    public static String role;
    public static int userId;
    static String rola;
    static String formattedDate;

    // Metoda odpowiedzialna za wyświetlanie informacji o zalogowanym użytkowniku
    public static String display() {
        // Pobranie aktualnej daty i czasu
        LocalDateTime currentDateTime = LocalDateTime.now();
        formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        // Tłumaczenie roli na język polski dla lepszego wyglądu
        if ("admin".equalsIgnoreCase(role)) {
            rola = "Administrator";
        } else {
            rola = "Użytkownik";
        }

        return "Zalogowany: " + email + " | Rola: " + rola + " | " + formattedDate;
    }
}