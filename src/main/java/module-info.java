module se2203b.hydragame {
    requires javafx.controls;
    requires javafx.fxml;


    opens se2203b.hydragame to javafx.fxml;
    exports se2203b.hydragame;
}