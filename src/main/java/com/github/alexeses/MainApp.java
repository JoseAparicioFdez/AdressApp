package com.github.alexeses;

import com.github.alexeses.controllers.RootLayoutController;
import com.github.alexeses.model.Person;
import com.github.alexeses.controllers.PersonEditDialogController;
import com.github.alexeses.controllers.PersonOverviewController;
import com.github.alexeses.model.PersonWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.prefs.Preferences;

public class MainApp extends Application {

    // Referencia al Stage principal
    private Stage primaryStage;
    private BorderPane rootLayout;
    private final ObservableList<Person> personData = FXCollections.observableArrayList();

    public MainApp() {
        // Datos de ejemplo:
        personData.add(new Person("Hans", "Mustermann", "Musterstrasse 1", 12345,
                "Musterstadt", LocalDate.of(1999, 2, 21)));
        personData.add(new Person("Ruth", "Mueller", "Musterstrasse 2", 12345,
                "Musterstadt", LocalDate.of(2000, 3, 22)));
        personData.add(new Person("Heinz", "Kurz", "Musterstrasse 3", 12345,
                "Musterstadt", LocalDate.of(2001, 4, 23)));
        personData.add(new Person("Cornelia", "Meier", "Musterstrasse 4", 12345,
                "Musterstadt", LocalDate.of(2002, 4, 24)));
        personData.add(new Person("Werner", "Meyer", "Musterstrasse 5", 12345,
                "Musterstadt", LocalDate.of(2003, 5, 25)));
        personData.add(new Person("Lydia", "Kunz", "Musterstrasse 6", 12345,
                "Musterstadt", LocalDate.of(2004, 6, 26)));
        personData.add(new Person("Anna", "Best", "Musterstrasse 7", 12345,
                "Musterstadt", LocalDate.of(2005, 7, 27)));
        personData.add(new Person("Stefan", "Meier", "Musterstrasse 8", 12345,
                "Musterstadt", LocalDate.of(2006, 8, 28)));
        personData.add(new Person("Martin", "Mueller", "Musterstrasse 9", 12345,
                "Musterstadt", LocalDate.of(2007, 9, 29)));
    }
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Contactos");
        this.primaryStage.getIcons().add(new javafx.scene.image.Image("file:resources/images/icon.png"));

        this.primaryStage.setWidth(800);
        this.primaryStage.setHeight(600);
        this.primaryStage.setMinWidth(800);
        this.primaryStage.setMinHeight(600);

        initRootLayout();

        showPersonOverview();

        // Carga los datos de prueba
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    // Inicializa el root layout
    public void initRootLayout() {
        try {
            // Cargo el root layout desde el archivo fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Le da al controlador acceso a la clase ejecutable
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // Mostrar la escena que contiene el root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mostar el person overview dentro del root layout
    public void showPersonOverview() {
        try {
            // Cargo el person overview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonOverview.fxml"));
            AnchorPane personOverview = loader.load();

            rootLayout.setCenter(personOverview);
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Abrimos el diálogo para editar detalles de la persona. Si el usuario hace clic en OK, \
     * los cambios son guardados en el objeto person y true es retornado.
     * @return true si el usuario hizo click en OK, false de lo contrario.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Menú de edición");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Devuelve el archivo de persona que fue abierto la última vez.
     * El valor es devuelto de las preferencias del sistema.
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Actualiza el título de la aplicación.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");
        }
    }

    /**
     * Devuelve el archivo de persona que fue guardado la última vez.
     * El valor retornado es el último archivo abierto por el usuario.
     * El valor por defecto es null.
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Carga los datos de la persona del archivo especificado. La actual
     * información de la persona será reemplazada.
     *
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonWrapper wrapper = new PersonWrapper();
            wrapper.setPersons(personData);

            m.marshal(wrapper, file);

            setPersonFilePath(file);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al guardar");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo guardar el archivo:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    /**
     * Carga los datos de la persona del archivo especificado. La ruta del archivo se guarda en
     * las preferencias de la aplicación.
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(PersonWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            PersonWrapper wrapper = (PersonWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            setPersonFilePath(file);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de lectura");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo cargar los datos del archivo:\n" + file.getPath());
            alert.showAndWait();
        }
    }

}