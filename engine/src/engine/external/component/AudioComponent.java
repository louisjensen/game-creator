package engine.external.component;

/**
 * @Author Hsingchih Tang
 * Stores the audio files to be played in the game
 * @param <Media> Audio file to be stored so that Runner can access the media and play the sound
 */
public class AudioComponent<Media> extends Component<Media> {
    public AudioComponent(Media audioFile) {
        super(audioFile);
    }
}
