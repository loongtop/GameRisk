module example.gamerisk {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires java.desktop;
    requires com.google.common;

    opens risk to javafx.fxml;
    exports risk;
    opens risk.controllers.players to javafx.fxml;
    exports risk.controllers;
    opens risk.controllers to javafx.fxml;
    exports risk.utils;
    opens risk.utils to javafx.fxml;
    exports risk.controllers.game;
    opens risk.controllers.game to javafx.fxml;
    exports risk.models;
    opens risk.models to javafx.fxml;
    exports risk.models.game;
    opens risk.models.game to javafx.fxml;
}