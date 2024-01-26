module com.chandni.cftes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    
    opens com.chandni.cftes to javafx.fxml;
    exports com.chandni.cftes;
}
