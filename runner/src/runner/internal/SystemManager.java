package runner.internal;

import engine.external.component.*;
import runner.internal.runnerSystems.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SystemManager {
    private List<RunnerSystem> mySystems;
    private LevelRunner myLevelRunner;

    public SystemManager(LevelRunner levelRunner){
        myLevelRunner = levelRunner;
        mySystems = new ArrayList<>();
        Collection<Class<? extends Component>> components = new ArrayList<>();
        components.add(ProgressionComponent.class);
        components.add(NextLevelComponent.class);
        mySystems.add(new ProgressionSystem(components, myLevelRunner,
                myLevelRunner.myGroup, myLevelRunner.myStage, myLevelRunner.myAnimation,
                myLevelRunner.mySceneWidth, myLevelRunner.mySceneHeight, myLevelRunner.myLevelChanger));

        Collection<Class<? extends Component>> components2 = new ArrayList<>();
        components2.add(CameraComponent.class);
        mySystems.add(new ScrollingSystem(components2, myLevelRunner, myLevelRunner.myGroup, myLevelRunner.myScene));

        Collection<Class<? extends Component>> components3 = new ArrayList<>();
        components3.add(ImageViewComponent.class);
        mySystems.add(new ImageDisplaySystem(components3, myLevelRunner, myLevelRunner.myGroup));

        Collection<Class<? extends Component>> components4 = new ArrayList<>();
        components4.add(ScoreComponent.class);
        mySystems.add(new ScoringSystem(components4, myLevelRunner, myLevelRunner.myHUD));

        Collection<Class<? extends Component>> components5 = new ArrayList<>();
        components5.add(ScoreComponent.class);
        mySystems.add(new LivesSystem(components5, myLevelRunner, myLevelRunner.myHUD));

        Collection<Class<? extends Component>> components6 = new ArrayList<>();
        components6.add(NextLevelComponent.class);
        mySystems.add(new LevelSystem(components6, myLevelRunner, myLevelRunner.myHUD));
    }

    public List<RunnerSystem> getSystems(){
        return mySystems;
    }
}
