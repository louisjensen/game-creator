package engine.external.component;

/**
 * @Author Hsingchih Tang
 * Stores a boolean variable indicating whether the audio file carried by
 * the same Entity via AudioComponent should be played
 * @param <Boolean> flag indicating whether to play the audio effect
 */
public class PlayAudioComponent<Boolean> extends Component<Boolean> {
    public PlayAudioComponent(Boolean play){
        super(play);
    }
}
