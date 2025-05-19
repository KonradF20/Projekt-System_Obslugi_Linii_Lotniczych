package project.app.projektsystem_obslugi_linii_lotniczych;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InfoDisplay {
    public static String email;

    public static void display() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println("Zalogowany użytkownik: " + email);
        System.out.println("Aktualna data i godzina: " + formattedDate);
    }
}