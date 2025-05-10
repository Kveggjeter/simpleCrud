module com.note.visma {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.desktop;

    opens com.note.visma to javafx.fxml;
    exports com.note.visma;
    exports com.note.visma.viewmodel;
    exports com.note.visma.domain.repository;
    exports com.note.visma.domain.model;
    opens com.note.visma.viewmodel to javafx.fxml;
}