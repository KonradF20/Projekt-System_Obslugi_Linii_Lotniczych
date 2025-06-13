package project.app.projektsystem_obslugi_linii_lotniczych.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

 //Klasa służąca do zarządzania fokusem pól formularza np. używana w formularzach logowania, rejestracji lub doładowywania salda

public class FocusController {

    // Elementy interfejsu GUI
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox showPasswordCheckBox;
    @FXML private TextField depositField;

    // Metoda odpowiedzialna za aktywację fokusu pól formularza po kliknięciu
    @FXML
    public void FocusOn(MouseEvent event) {
        emailField.setFocusTraversable(true);
        passwordField.setFocusTraversable(true);
        showPasswordCheckBox.setFocusTraversable(true);
    }

    // Metoda odpowiedzialna za aktywację fokusu pól formularza po kliknięciu
    @FXML
    public void FocusOn1(MouseEvent event) {
        depositField.setFocusTraversable(true);
    }
}
