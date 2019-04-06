package ui_components.games;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameList {
    private Pane myDisplay;
    private ArrayList<String> gameNames = new ArrayList<String>(); // todo: use this to populate cards instead of a number of games
    private static final double CARD_H_OFFSET = 50;
    private static final double CARD_V_OFFSET = 50;
    private static final int NUM_CARDS_DISPLAYED = 3;

    public GameList(int numGames) {
        initializeDisplay(numGames);
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay(int numGames) {
        FlowPane gameList = new FlowPane();
        gameList.setVgap(CARD_V_OFFSET);
        gameList.setHgap(CARD_H_OFFSET);
        gameList.setPrefWrapLength((GameCard.DISPLAY_WIDTH + CARD_H_OFFSET) * NUM_CARDS_DISPLAYED);
        for(int i = 0; i < numGames; i++) {
            GameCard c = new GameCard();
            gameList.getChildren().add(c.getDisplay());
        }
        myDisplay = gameList;
    }

}
