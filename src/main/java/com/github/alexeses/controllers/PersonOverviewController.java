package com.github.alexeses.controllers;

import com.github.alexeses.MainApp;
import com.github.alexeses.model.Person;
import com.github.alexeses.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {

    private MainApp mainApp;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel, lastNameLabel, streetLabel, postalCodeLabel, cityLabel, birthdayLabel;

    public PersonOverviewController() {
    }

    /**
     * Este método es llamado por la aplicación principal para tener una referencia
     * de vuelta a sí misma.
     */
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Este método es llamado por la aplicación principal para dar una referencia
     * de vuelta a sí misma.
     *
     *  @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        personTable.setItems(mainApp.getPersonData());
    }

    /**
     * Llena todos los campos de texto para mostrar detalles sobre la persona.
     * Si la referencia de la persona es nula, todos los campos de texto se
     * limpian.
     *
     * @param person la persona o null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Rellena las label con el texto que coja de la instancia de Person:
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Si la persona es null, vacía todos los campos:
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Llamado cuando el usuario hace clic en el botón nuevo. Abre una ventana para
     * editar detalles para una nueva persona.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Contactos");
            alert.setHeaderText("No se ha seleccionado ningún contacto");
            alert.setContentText("Por favor, selecciona un contacto de la lista.");

            alert.showAndWait();
        }
    }

    /**
     * LLamado cuando el usuario hace clic en el botón nuevo. Abre una ventana para
     * editar detalles para una nueva persona.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }
    /**
     * Llamado cuando el usuario hace clic en el botón editar. Abre una ventana para
     * editar detalles para la persona seleccionada.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Contactos");
            alert.setHeaderText("No se ha seleccionado ningún contacto");
            alert.setContentText("Por favor, selecciona un contacto de la lista.");

            alert.showAndWait();
        }
    }


}