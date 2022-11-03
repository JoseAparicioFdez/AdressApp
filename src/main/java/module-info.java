module ch.makery.address.completetutov2 {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.graphics;
        requires javafx.base;


        opens ch.makery.address.completetutov2 to javafx.fxml;
        exports ch.makery.address.completetutov2;

        opens ch.makery.address.completetutov2.view to javafx.fxml;
        exports ch.makery.address.completetutov2.view;
}