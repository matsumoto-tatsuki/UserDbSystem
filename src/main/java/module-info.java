module com.example.usermanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.usermanagementsystem to javafx.fxml;
    exports com.example.usermanagementsystem;
}