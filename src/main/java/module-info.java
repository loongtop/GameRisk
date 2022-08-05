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

    opens riskgame to javafx.fxml;
    exports riskgame;
    opens riskgame.controllers.players to javafx.fxml;
    opens riskgame.controllers.game.phase to javafx.fxml;
    exports riskgame.controllers.dice;
    opens riskgame.controllers.dice to javafx.fxml;
    exports riskgame.controllers;
    opens riskgame.controllers to javafx.fxml;
    exports riskgame.utils;
    opens riskgame.utils to javafx.fxml;
    exports riskgame.controllers.game.impl;
    opens riskgame.controllers.game.impl to javafx.fxml;
}