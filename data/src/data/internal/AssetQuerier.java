package data.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * A Querier that is used to manage saving and loading assets (sounds and images)
 */
public class AssetQuerier extends Querier {

    private static final String IMAGES_TABLE_NAME = "Images";
    private static final String IMAGE_NAME_COLUMN = "ImageName";
    private static final String IMAGE_DATA_COLUMN = "ImageData";

    private static final String SOUNDS_TABLE_NAME = "Sounds";
    private static final String SOUND_NAME_COLUMN = "SoundName";
    private static final String SOUND_DATA_COLUMN = "SoundData";

    private static final String IMAGES_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", IMAGES_TABLE_NAME, IMAGE_NAME_COLUMN, IMAGE_DATA_COLUMN);
    private static final String SOUNDS_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)", SOUNDS_TABLE_NAME, SOUND_NAME_COLUMN, SOUND_DATA_COLUMN);
    private static final String UPDATE_IMAGES =
            String.format("%s %s %s = ?", IMAGES_INSERT, ON_DUPLICATE_UPDATE, IMAGE_DATA_COLUMN);
    private static final String UPDATE_SOUNDS =
            String.format("%s %s %s = ?", SOUNDS_INSERT, ON_DUPLICATE_UPDATE, SOUND_DATA_COLUMN);
    private static final String LOAD_SOUND =
            String.format("SELECT %s FROM %s WHERE %s = ?", SOUND_DATA_COLUMN, SOUNDS_TABLE_NAME, SOUND_NAME_COLUMN);
    private static final String LOAD_IMAGE =
            String.format("SELECT %s FROM %s WHERE %s = ?", IMAGE_DATA_COLUMN, IMAGES_TABLE_NAME, IMAGE_NAME_COLUMN);

    private static final String COULD_NOT_LOAD_ASSET = "Could not load asset: ";
    private static final String COULD_NOT_SAVE_THE_ASSET = "Could not save the asset: ";
    private static final String COULD_NOT_FIND_THE_FILE = "Could not find the file: ";


    private PreparedStatement myUpdateImagesStatement;
    private PreparedStatement myUpdateSoundsStatement;
    private PreparedStatement myLoadImageStatement;
    private PreparedStatement myLoadSoundStatement;

    /**
     * AssetQuerier constructor calls super constructor to initialize prepared statements
     * @param connection connection to the database provided by database engine
     * @throws SQLException if cannot connect or prepare the statements
     */
    public AssetQuerier(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected void prepareStatements() throws SQLException {
        myUpdateImagesStatement = myConnection.prepareStatement(UPDATE_IMAGES);
        myUpdateSoundsStatement = myConnection.prepareStatement(UPDATE_SOUNDS);
        myLoadImageStatement = myConnection.prepareStatement(LOAD_IMAGE);
        myLoadSoundStatement = myConnection.prepareStatement(LOAD_SOUND);
        myPreparedStatements = List.of(myUpdateImagesStatement, myUpdateSoundsStatement, myLoadImageStatement,
                myLoadSoundStatement);
    }

    /**
     * Saves an image to the database
     * @param imageName the name of the image to save
     * @param imageToSave the image file that should be saved
     */
    public void saveImage(String imageName, File imageToSave) {
        saveAsset(imageName, imageToSave, myUpdateImagesStatement);
    }

    /**
     * Saves a sound to the database
     * @param soundName name of the sound to be saved
     * @param soundToSave sound file to be saved
     */
    public void saveSound(String soundName, File soundToSave){
        saveAsset(soundName, soundToSave, myUpdateSoundsStatement);
    }

    /**
     * Loads an image from the database
     * @param imageName name of the image to be loaded
     * @return an input stream of image data to be converted to an image object
     */
    public InputStream loadImage(String imageName){
        return loadAsset(imageName, IMAGE_DATA_COLUMN, myLoadImageStatement);
    }

    /**
     * Loads a sound from the database
     * @param soundName name of the sound to be loaded
     * @return an input stream of sound data to be converted to a media object
     */
    public InputStream loadSound(String soundName){
        return loadAsset(soundName, SOUND_DATA_COLUMN, myLoadSoundStatement);
    }

    private InputStream loadAsset(String assetName, String columnName, PreparedStatement statement) {
        try {
            statement.setString(1, assetName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                InputStream imageInputStream = resultSet.getBinaryStream(columnName);
                resultSet.close();
                return  imageInputStream;
            }
        } catch (SQLException e) {
            System.out.println(COULD_NOT_LOAD_ASSET + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void saveAsset (String assetName, File assetToSave, PreparedStatement statement) {
        try {
            BufferedInputStream assetData = new BufferedInputStream(new FileInputStream(assetToSave));
            System.out.println(assetData);
            statement.setString(1, assetName);
            statement.setBinaryStream(2, new BufferedInputStream(new FileInputStream(assetToSave)));
            statement.setBinaryStream(3, new BufferedInputStream(new FileInputStream(assetToSave)));
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println(COULD_NOT_SAVE_THE_ASSET + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(COULD_NOT_FIND_THE_FILE + assetToSave.toString());
        }
    }
}
