module ch.makery.address.completetutov2 {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.graphics;
        requires javafx.base;
        requires java.prefs;
        requires java.xml.bind;


        opens com.github.alexeses to javafx.fxml;
        exports com.github.alexeses;
        exports com.github.alexeses.util;

        opens com.github.alexeses.controllers to javafx.fxml;
        exports com.github.alexeses.controllers;
        opens com.github.alexeses.model to javafx.fxml;

}