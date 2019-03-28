package api.data;

public interface ExternalData {
    /*
     * From the game center, a particular set of games will be loaded in to be displayed - the game center does not
     * necessarily need access to all of the game's information so there will be a more encapsulated version that simply
     * stores information about top plays of the game, a display image, and the name of the game
     */
    Object loadGameDisplay(String gameName);
    /*
     * From the game center, a particular game's display may be edited to the user's preference. For example, a game's
     * display image may change from some default background to a likely scene in that particular game. This method will
     * need to change any other files that accompany the actual game to ensure consistency between the game center, engine,
     * and authoring
     */
    void saveGameDisplay(String gameName);
    /*
     * This method will load a game from the beginning, as a user may wish to restart a game entirely as opposed to continuing
     * wherever they left off. The files loaded will include all of the game's necessary xml and resources file. In particular, these
     * files will be separated into ones that the runner needs access to (music, background image, etc.)  and particular
     * state and behavior information that the engine will need access to in order to evaluate logic.
     */
    Object loadGame(String gameName);
    /*
     * This method will continue a game from its most recent state (i.e. if the user had gotten to a saved checkpoint, exited
     * the game, played another one, and then wished to return to the first game, they may do so by invoking the following
     * method. It may call the same methods as loadGame for the most part and simply include one or two more xml files
     * to specify where the game last left off
     */
    Object continueGame(String gameName);
    /*
     * This method will save the current state of the game (if the user has reached a checkpoint, it will store the current
     * score, time, hero lives, hero position, etc. so that the user may continue wherever they left off if they choose to
     * resume playing the game
     */
    void saveGame(String gameName);
}
