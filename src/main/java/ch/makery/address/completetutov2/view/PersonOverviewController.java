package ch.makery.address.completetutov2.view;

import ch.makery.address.completetutov2.MainApp;
import ch.makery.address.completetutov2.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {

    // Declarate the nodes that we are going to manage in the controller:
    private MainApp mainApp;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    public PersonOverviewController() {
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    /**
     * This method is called when the user clicks on the delete button.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // 'Pinta' los datos en la tabla
        personTable.setItems(mainApp.getPersonData());
    }
}