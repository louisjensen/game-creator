package runner.external;

import engine.external.Component;

import java.util.List;

public class GameRunner {
    /**
     * This will be the primary class that creates a new game engine
     * and displays sprites on a stage
     */
    private List<Component> myComponents;
    private int mySceneWidth;
    private int mySceneHeight;

    public GameRunner(List<Component> components, int width, int height){
        myComponents = components;
        mySceneWidth = width;
        mySceneHeight = height;
    }
}
