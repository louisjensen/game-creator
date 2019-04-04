module runner {
    requires engine;
    requires data;
    requires javafx.graphics;
    exports runner.external;
    exports runner.internal to javafx.graphics;
}