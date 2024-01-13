module com.example.share_ride {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jdk.httpserver;
    requires org.json;

    opens com.share_ride to javafx.fxml;
    exports com.share_ride;
    exports server;
    opens server to javafx.fxml;
    exports handlers;
    opens handlers to javafx.fxml;
    exports database;
    opens database to javafx.fxml;
    exports controllers;
    opens controllers to javafx.fxml;
}