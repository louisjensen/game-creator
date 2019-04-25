package controls;

import javafx.scene.control.ToggleButton;

class SceneSwitchButton extends ToggleButton {
    private static final String STYLE = "default_launcher.css";
    SceneSwitchButton(String label){
        this.setText(label);
        this.getStyleClass().add(STYLE);

    }

}
