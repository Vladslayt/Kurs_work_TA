module com.example.kurs {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.example.kurs to javafx.fxml;
    exports com.example.kurs;
}