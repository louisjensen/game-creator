package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.PlayAudioComponent;
import javafx.application.Platform;
import runner.internal.AudioManager;
import runner.internal.LevelRunner;
import java.util.Collection;


public class SoundSystem extends RunnerSystem {
    private AudioManager myAudioManager;

    public SoundSystem(Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, AudioManager audioManager) {
        super(requiredComponents, levelRunner);
        myAudioManager = audioManager;
    }

    @Override
    public void run() {
        for (Entity entity : this.getEntities()) {
            if (entity.hasComponents(PlayAudioComponent.class) && (Boolean) getComponentValue(PlayAudioComponent.class, entity)) {
                playSound(entity);
            }
        }
    }

    private void playSound(Entity entity) {
        myAudioManager.playSound(entity);
    }
}
