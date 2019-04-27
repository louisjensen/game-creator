package frontend.ratings;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class StarBox {
    private static final int NUMBER_OF_STARS = 5;

    private List<Star> myStars;
    private BorderPane myDisplay;

    public StarBox() {
        myDisplay = new BorderPane();
        initializeDisplay();
    }

    public Pane getDisplay() {
        return myDisplay;
    }

    private void initializeDisplay() {
        myStars = new ArrayList<>();
        HBox stars = new HBox();
        for(int i = 0; i < NUMBER_OF_STARS; i++) {
            Star currentStar = new Star(i);
            currentStar.getImageDisplay().setOnMouseClicked(e-> handleStarClick(currentStar));
            myStars.add(currentStar);
            stars.getChildren().add(currentStar.getImageDisplay());
        }
        stars.setAlignment(Pos.CENTER);
        myDisplay.setCenter(stars);
    }

    private void handleStarClick(Star currentStar) {
        System.out.println("FASJDIAFJAS");
        resetAllStars();
        for(int i = 0; i <= currentStar.getIndex(); i++) {
            myStars.get(i).setSelected(true);
        }
        initializeDisplay();
    }

    private void resetAllStars() {
        for(Star star : myStars) {
            star.setSelected(false);
        }
    }

}
