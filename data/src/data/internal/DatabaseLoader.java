package data.internal;

import data.external.DataManager;

import java.util.List;

public class DatabaseLoader {

    public static void main(String[] args) {
        loadGameCenterDataFromCreatedGames();
    }

    private static void loadGameCenterDataFromCreatedGames() {
        DataManager dm = new DataManager();
        List<String> gamesToLoad = List.of("celeste","downwell","fez","inside","limbo","mario","ori","spelunky",
                "supermeatboy","yooka");
        for (String game: gamesToLoad){
            dm.saveGameDataFromFolder(game);
        }
    }
}
