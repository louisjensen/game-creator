package api.runner;

public interface ExternalRunner {

    /**
     * Available to authoring environment in order for user to evaluate the current state of the game they
     * are working on and see if they wish to make further edits, also avialable to game
     * center to run whatever game the user selects
     * @param filePath path to files of the game that has been created to play
     * @throws Exception thrown if the files given to run do not create a valid game
     */
    void run(String filePath) throws Exception;
}
