package api.runner;

public interface ExternalRunner {
    /*
     * Available to authoring environment in order for user to evaluate the current state of the game they
     * are working on and see if they wish to make further edits
     */
    void run();
}
