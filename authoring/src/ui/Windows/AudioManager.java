package ui.Windows;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.manager.AssetManager;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.InputStream;

public class AudioManager extends AssetManager {

    private static final String EXTENSION_KEY = "AcceptableAudioExtensions";
    private static final String TITLE_KEY = "AudioTitle";
    private static final String ASSET_AUDIO_FOLDER_PATH = GENERAL_RESOURCES.getString("audio_filepath");
    private ListView myListView;

    public AudioManager(){
        super(ASSET_AUDIO_FOLDER_PATH, TITLE_KEY, EXTENSION_KEY);
    }

    @Override
    protected void addAsset(File file) {
        Label text = new Label(file.getName());
        VBox vBox = new VBox(text);
        vBox.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    Media sound = new Media(file.toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(sound);
                    mediaPlayer.play();
                }
            }
        });
        myListView.getItems().add(vBox);
    }

    @Override
    protected ListView createAndFormatScrollPaneContent() {
        myListView = new ListView();
        return myListView;
    }

    @Override
    protected void saveAsset(File selectedFile) {
        try {
            InputStream inputStream = AudioSystem.getAudioInputStream(selectedFile);
            AudioFormat audioFormat = ((AudioInputStream) inputStream).getFormat();
            AudioInputStream audioInputStream = new AudioInputStream(inputStream, audioFormat, ((AudioInputStream) inputStream).getFrameLength());
            File saveToFile = new File(ASSET_AUDIO_FOLDER_PATH + File.separator + selectedFile.getName());
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, saveToFile);
        } catch (Exception e) {
            handleSavingException();
        }
    }

    @Override
    protected void handleApply() {

    }
}
