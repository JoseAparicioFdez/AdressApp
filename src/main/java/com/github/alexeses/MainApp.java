package com.github.alexeses;

import com.github.alexeses.model.Person;
import com.github.alexeses.controllers.PersonEditDialogController;
import com.github.alexeses.controllers.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class MainApp extends Application {

    // Referencia al Stage principal
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

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
    }

    // Inicializa el root layout
    public void initRootLayout() {
        try {
            // Cargo el root layout desde el archivo fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

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
     * Abrimos el dialogo para editar detalles de la persona. Si el usuario hace click en OK,\
     * los cambios son guardados en el objeto person y true es retornado.
     * @param person
     * @return true si el usuario hizo click en OK, false de lo contrario.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

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
}