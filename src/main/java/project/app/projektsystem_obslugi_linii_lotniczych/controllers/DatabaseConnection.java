package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Klasa służąca do połączenia z bazą danych MySQL

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/projekt";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}