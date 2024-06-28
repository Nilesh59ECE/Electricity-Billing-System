module org.example.electricitybillingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql; // Add this line to declare the dependency on java.sql module

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires itextpdf;

    opens org.example.electricitybillingsystem to javafx.fxml;
    exports org.example.electricitybillingsystem;
}
