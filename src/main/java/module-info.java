module com.example.banknotesparser {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.pdfbox;
    requires java.desktop;

    opens com.example.banknotesparser to javafx.fxml;
    exports com.example.banknotesparser;
}