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

    opens example.gamerisk to javafx.fxml;
    exports example.gamerisk;
    exports example.gamerisk.gampage;
    opens example.gamerisk.gampage to javafx.fxml;
    exports example.gamerisk.frontpage;
    opens example.gamerisk.frontpage to javafx.fxml;
}