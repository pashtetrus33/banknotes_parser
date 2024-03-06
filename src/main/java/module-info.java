module com.example.banknotesparser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.banknotesparser to javafx.fxml;
    exports com.example.banknotesparser;
}