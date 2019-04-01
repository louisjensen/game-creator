module authoring {
    requires runner;
    requires data;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;

    exports authoring.external;
    exports ui.panes;
}