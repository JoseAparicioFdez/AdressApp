package com.github.alexeses.controllers;

import com.github.alexeses.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class RootLayoutController {
    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Abre un FileChooser para que el usuario seleccione una agenda para cargar
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Guarda el archivo actual en el archivo que se especificó en la aplicación. Si no hay
     * archivo especificado, se abre el diálogo "Guardar como".
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Abre un FileChooser para que el usuario seleccione un archivo para guardar.
     * Si el usuario hace click en OK, el archivo es guardado y el stage es cerrado.
     * Si el usuario hace click en cancelar, el stage es cerrado.
     * Si el usuario selecciona un archivo que ya existe, se le pregunta si desea
     * sobreescribirlo.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Establece el filtro para la extensión XML
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Muestra el dialog de guardado
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Nos aseguramos de que el fichero es XML
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Abre un diálogo de información sobre la aplicación.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("About the app");
        alert.setHeaderText(null);
        alert.setContentText("Author: Marco Jakob" +
                "\nWebsite: http://code.makery.ch" +
                "\nAutor of this version: @baljaudev");
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}