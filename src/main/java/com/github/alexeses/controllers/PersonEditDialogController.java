package com.github.alexeses.controllers;

import com.github.alexeses.model.Person;
import com.github.alexeses.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PersonEditDialogController {

    @FXML
    private TextField firstNameField, lastNameField, streetField, postalCodeField, cityField, birthdayField;
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Inicializa la clase controlador. Este método es llamado automáticamente
     * después de que se ha cargado el archivo fxml.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Establece el escenario de este diálogo.
     *
     *  @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Establece la persona a ser editada en el diálogo.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Retorna true si el usuario clicó OK, de lo contrario false.
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Llamado cuando el usuario clickea OK.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Llamado cuando el usuario clickea cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Valida la entrada del usuario en los campos de texto.
     *
     * @return true si la entrada es válida
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "El nombre es incorrecto!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "El apellido es incorrecto\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "La calle no es válida!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "El código portal no es válido!\n";
        } else {
            // intenta parsear el código postal en un int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "El código postal no es válido (Integer)\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "La ciudad no es válida!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "La fecha de nacimiento no es válida\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "La fecha de nacimiento no es válida. dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Muéstralo en un cuadro de diálogo de alerta.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campos inválidos");
            alert.setHeaderText("Por favor, corrija los campos inválidos");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
