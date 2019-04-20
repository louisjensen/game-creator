package data.external;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager implements ExternalData{

    private static final String CREATED_GAMES_DIRECTORY = "created_games";
    public static final String XML_EXTENSION = ".xml";
    public static final String GAME_DATA = "game_data";
    private static final String GAME_INFO = "game_info";

    private XStream mySerializer;
    private DatabaseEngine myDatabaseEngine;

    public DataManager(){
        mySerializer = new XStream(new DomDriver());
        myDatabaseEngine = new DatabaseEngine();
    }

    @Override
    public Object loadGameDisplay(String gameName) {
        return null;
    }

    @Override
    public void saveGameDisplay(String gameName) {

    }

    @Override
    public Object loadGame(String gameName) {
        return null;
    }

    @Override
    public Object continueGame(String gameName) {
        return null;
    }

    @Override
    public void saveGame(String gameName) {

    }

    public void createGameFolder(String folderName, String gameName){

        createGameFolder(folderName);
        if (!myDatabaseEngine.open()){
            System.out.println("Couldn't connect to database");
            return;
        }
        try {
            myDatabaseEngine.createEntryForNewGame(gameName);
        } catch (SQLException e) {
            System.out.println("Couldn't create entry fro new game: " + e.getMessage());
        }
        myDatabaseEngine.close();
    }

    @Override
    public void createGameFolder(String folderName) {
//        try {
//            Files.createDirectories(Paths.get(CREATED_GAMES_DIRECTORY + File.separator + folderName));
//        } catch (IOException e) {
//            System.out.println("Could not create directory");
//            e.printStackTrace();
//        }
        if (!myDatabaseEngine.open()){
            System.out.println("Couldn't connect to database");
            return;
        }
        try {
            myDatabaseEngine.createEntryForNewGame(folderName);
        } catch (SQLException e) {
            System.out.println("Couldn't create entry for new game: " + e.getMessage());
        }
        myDatabaseEngine.close();
    }

    @Override
    public void saveObjectToXML(String path, Object objectToBeSaved) {
        String myRawXML = mySerializer.toXML(objectToBeSaved);
        writeToXML(path, myRawXML);
    }

    @Override
    public Object loadObjectFromXML(String path) throws FileNotFoundException{
        String rawXML = readFromXML(path);
        return mySerializer.fromXML(rawXML);
    }

    @Override
    public void saveGameData(String gameName, Object gameObject) {
//        String path = transformGameNameToPath(gameName, GAME_DATA);
//        saveObjectToXML(path, gameObject);
        String myRawXML = mySerializer.toXML(gameObject);
        if (! myDatabaseEngine.open()){
            System.out.println("Couldn't load to database because couldn't connect");
        }
        try {
            myDatabaseEngine.updateGameEntryData(gameName, myRawXML);
        } catch (SQLException e) {
            System.out.println("Couldn't update game entry data: " + e.getMessage());
        }
        myDatabaseEngine.close();

    }

    public Object loadGameInfo(String gameName) throws FileNotFoundException{
        return loadObjectFromXML(transformGameNameToPath(gameName, GAME_INFO));
    }

    public List<Object> loadAllGameInfoObjects(){
//        List<String> gameNames = getGameNames();
//        List<Object> gameInfoObjects = new ArrayList<>();
//        for (String game : gameNames){
////            if (loadGameInfo(game))
//            try {
//                gameInfoObjects.add(loadGameInfo(game));
//            } catch (FileNotFoundException exception){
//                // do not try to add object to the list
//            }
//        }
        List<Object> gameInfoObjects = new ArrayList<>();
        List<String> gameInfoObjectXMLs = null;
        try {
            myDatabaseEngine.open();
            gameInfoObjectXMLs = myDatabaseEngine.loadAllGameInformationXMLs();
            myDatabaseEngine.close();
        } catch (SQLException e) {
            System.out.println("Couldn't load game information xmls: " + e.getMessage());
        }
        for (String xml : gameInfoObjectXMLs){
            gameInfoObjects.add(mySerializer.fromXML(xml));
        }
        return gameInfoObjects;
    }

    public void saveGameInfo(String gameName, Object gameInfoObject){
//        String path = transformGameNameToPath(gameName, GAME_INFO);
//        saveObjectToXML(path, gameInfoObject);
        String myRawXML = mySerializer.toXML(gameInfoObject);
        if (! myDatabaseEngine.open()){
            System.out.println("Couldn't load to database because couldn't connect");
        }
        try {
            myDatabaseEngine.updateGameEntryInfo(gameName, myRawXML);
        } catch (SQLException e) {
            System.out.println("Couldn't update game entry information: " + e.getMessage());
        }
        myDatabaseEngine.close();
    }

    @Override
    public void saveAssets(String gameName, List<String> assets) {

    }

    @Override
    public Object loadGameData(String gameName) {

//        return loadObjectFromXML(transformGameNameToPath(gameName, GAME_DATA));
        if(!myDatabaseEngine.open()){
            System.out.println("Couldn't connect");
        }
        Object ret = null;
        try {
            ret = mySerializer.fromXML(myDatabaseEngine.loadGameData(gameName));
        } catch (SQLException e) {
            System.out.println("Couldn't load game date from database: " + e.getMessage());
        }
        myDatabaseEngine.close();
        return ret;
    }

//    public void saveGameDataFromFolder(String gameName){
//        myDatabaseEngine.open();
//        myDatabaseEngine.createEntryForNewGame(gameName);
//        try {
//            myDatabaseEngine.updateGameEntryInfo(gameName, readFromXML("created_games/"+gameName+"/"+GAME_INFO+XML_EXTENSION));
//        } catch (FileNotFoundException e || SQLException sqlException) {
//            e.printStackTrace();
//        }
//    }


    private String readFromXML(String path) throws FileNotFoundException {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        StringBuilder rawXML = new StringBuilder();
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                rawXML.append(currentLine);
            }
        } catch (IOException e) {
            System.out.println("Cannot read XML file");
            throw new FileNotFoundException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ex) {
                System.out.println("Could not close files");
            }
        }
        return rawXML.toString();
    }

    public void saveImage(String imageName, File imageToSave){
        myDatabaseEngine.open();
        myDatabaseEngine.saveImage(imageName, imageToSave);
        myDatabaseEngine.close();
    }

    public void saveSound(String soundName, File soundToSave){
        myDatabaseEngine.open();
        myDatabaseEngine.saveSound(soundName, soundToSave);
        myDatabaseEngine.close();
    }

    public InputStream loadSound(String soundName){
        myDatabaseEngine.open();
        InputStream inputStream = myDatabaseEngine.loadSound(soundName);
        myDatabaseEngine.close();
        return inputStream;
    }

    public InputStream loadImage(String imageName){
        myDatabaseEngine.open();
        InputStream inputStream = myDatabaseEngine.loadImage(imageName);
        myDatabaseEngine.close();
        return inputStream;
    }

    public List<String> getGameNames(){
        File file = new File(CREATED_GAMES_DIRECTORY);
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        System.out.println(Arrays.toString(directories));
        if (directories != null) {
            return Arrays.asList(directories);
        }
        return new ArrayList<>();
    }

    private String transformGameNameToPath(String gameName, String filename) {
        return CREATED_GAMES_DIRECTORY + File.separator + gameName + File.separator + filename + XML_EXTENSION;
    }

    private void writeToXML(String path, String rawXML){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(path));
            fileWriter.write(rawXML);
        } catch (IOException exception){
            System.out.println("In catch block");
            exception.printStackTrace();
        } finally {
            System.out.println("In finally block");
            try {
                if (fileWriter != null){
                    fileWriter.close();
                }
            } catch (IOException e){
                System.out.println("Couldn't close file");
            }
        }
    }
}
