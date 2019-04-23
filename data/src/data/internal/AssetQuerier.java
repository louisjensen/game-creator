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


    private PreparedStatement myUpdateImagesStatement;
    private PreparedStatement myUpdateSoundsStatement;
    private PreparedStatement myLoadImageStatement;
    private PreparedStatement myLoadSoundStatement;

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

    public void saveImage(String imageName, File imageToSave) {
        saveAsset(imageName, imageToSave, myUpdateImagesStatement);
    }

    public void saveSound(String soundName, File soundToSave){
        saveAsset(soundName, soundToSave, myUpdateSoundsStatement);
    }

    public InputStream loadImage(String imageName){
        return loadAsset(imageName, IMAGE_DATA_COLUMN, myLoadImageStatement);
    }

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
            System.out.println("Could not load asset: " + e.getMessage());
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
            System.out.println("Could not save the asset: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file: " + assetToSave.toString());
        }
    }
}
