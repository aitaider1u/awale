module com.example.awaleig {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.awaleig to javafx.fxml;
    exports com.example.awaleig;
}