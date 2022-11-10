module ch.makery.address.completetutov2 {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.graphics;
        requires javafx.base;


        opens com.github.alexeses to javafx.fxml;
        exports com.github.alexeses;

        opens com.github.alexeses.controllers to javafx.fxml;
        exports com.github.alexeses.controllers;
}