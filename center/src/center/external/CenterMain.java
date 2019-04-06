package center.external;

import javafx.application.Application;
import javafx.stage.Stage;
import ui_components.CenterView;

public class CenterMain extends Application {

    public static void main(String[] args){
        launch(args);
    }

    public void start (Stage myStage) throws Exception {
        CenterView view = new CenterView();
        myStage.setScene(view.getScene());
        myStage.show();
    }
}
