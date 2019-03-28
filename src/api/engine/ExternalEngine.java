package api.engine;

import java.util.Collection;

public interface ExternalEngine {
    /*
     * Updates all GameObjects that were involved in some event that requires values to change. This method will be called
     * as a "step" function would
     */
    VisualState updateState(Collection<Integer> myInputKeys);

}
