module project.app.projektsystem_obslugi_linii_lotniczych {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens project.app.projektsystem_obslugi_linii_lotniczych to javafx.fxml;
    exports project.app.projektsystem_obslugi_linii_lotniczych;
}