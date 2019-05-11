package com.mygdx.game.logic.action.manager;

import com.mygdx.game.logic.action.Action;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.MapRegistry;

public class ActionManager {

    public static final ActionManager INSTANCE = new ActionManager();

    private final ActionRegistry actionRegistry = ActionRegistry.INSTANCE;
    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;


    private ActionManager() {
    }

    public void update() {
        if(mapRegistry.getCurrentMapToShow() != null) {
            for (Action action : actionRegistry.getActions((mapRegistry.getCurrentMapToShow()))) {
                if (!action.isFinished()) {
                    action.update();
                } else {
                    action.finish();
                    actionRegistry.remove(mapRegistry.getCurrentMapToShow(), action);
                }
            }
        }
    }

}
