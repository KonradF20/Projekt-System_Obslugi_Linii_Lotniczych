package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InfoDisplay {

    public static String email;
    public static String role;
    static String rola;
    static String formattedDate;
    public static int userId;

    public static String display() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

        if ("admin".equalsIgnoreCase(role)) {
            rola = "Administrator";
        } else {
            rola = "Użytkownik";
        }

        return "Zalogowany: " + email + " | Rola: " + rola + " | " + formattedDate;
    }
}