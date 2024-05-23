module org.example.restaurantmanagementsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires jasperreports;


    opens org.example.restaurantmanagementsys to javafx.fxml;
    exports org.example.restaurantmanagementsys;
}