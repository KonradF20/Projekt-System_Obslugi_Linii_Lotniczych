package project.app.projektsystem_obslugi_linii_lotniczych.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Klasa przedstawiająca dane lotów

public class Flights {
    int flight_id;
    String departure_iata_code;
    String departure_city;
    LocalDateTime departure_time;
    String arrival_iata_code;
    String arrival_city;
    LocalDateTime arrival_time;
    int available_seats;
    double price;
    String travel_class;

    // Gettery i Settery dla klasy Flights
    public int getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id = flight_id;
    }

    public String getDeparture_iata_code() {
        return departure_iata_code;
    }

    public void setDeparture_iata_code(String departure_iata_code) {
        this.departure_iata_code = departure_iata_code;
    }

    public String getDeparture_city() {
        return departure_city;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = departure_city;
    }

    public LocalDateTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(LocalDateTime departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_iata_code() {
        return arrival_iata_code;
    }

    public void setArrival_iata_code(String arrival_iata_code) {
        this.arrival_iata_code = arrival_iata_code;
    }

    public String getArrival_city() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = arrival_city;
    }

    public LocalDateTime getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(LocalDateTime arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(int available_seats) {
        this.available_seats = available_seats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTravel_class() {
        return travel_class;
    }

    public void setTravel_class(String travel_class) {
        this.travel_class = travel_class;
    }

    // Nadpisana metoda odpowiedzialna za czytelne wyświetlenie informacji o locie
    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
        return String.format(
                "%s → %s %s | %s - %s | 💰 %.2f zł | 💺 %d | %s",
                departure_city, arrival_iata_code, arrival_city, departure_time.format(dateFormatter), arrival_time.format(dateFormatter), price, available_seats, travel_class
        );
    }
}
