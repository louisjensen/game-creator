package center.external;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui_components.games.GameCard;
import ui_components.header.HeaderBar;

public class CenterMain extends Application {
    private static final double STAGE_WIDTH = 1250;
    private static final double STAGE_HEIGHT = 750;
    private static final Color BACKGROUND_COLOR = Color.rgb(46, 43, 51);

    private static final double CARD_H_OFFSET = 50;
    private static final double CARD_V_OFFSET = 50;
    private static final int NUM_CARDS_DISPLAYED = 3;

    public static void main(String[] args){
        launch(args);
    }

    public void start (Stage myStage) throws Exception {
        BorderPane root = new BorderPane();
        HeaderBar myHeader = new HeaderBar();
        Pane layout = myHeader.getHeaderLayout();
        BorderPane.setAlignment(layout, Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: transparent;");
        layout.setStyle("-fx-background-color: transparent;");
        root.setTop(layout);
        FlowPane gameList = new FlowPane();
        GameCard card = new GameCard();
        GameCard card2 = new GameCard();
        gameList.setVgap(CARD_V_OFFSET);
        gameList.setHgap(CARD_H_OFFSET);
        gameList.setPrefWrapLength((GameCard.DISPLAY_WIDTH + CARD_H_OFFSET) * NUM_CARDS_DISPLAYED);
        gameList.getChildren().addAll(card.getDisplay(), card2.getDisplay());
        root.setCenter(gameList);
        Scene myScene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT, BACKGROUND_COLOR);
        myStage.setScene(myScene);
        myStage.show();
    }
}
