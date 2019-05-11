package com.mygdx.game.registry;

import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.action.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActionRegistry {

    public static final ActionRegistry INSTANCE = new ActionRegistry();

    private final Map<Map2D, List<Action>> actions;

    private ActionRegistry() {
        actions = new HashMap<>();
    }

    public List<Action> getActions(Map2D map) {
        actions.computeIfAbsent(map, v -> new CopyOnWriteArrayList<>());
        return actions.get(map);
    }

    public void add(Map2D map, Action action) {
        actions.computeIfAbsent(map, v -> new ArrayList<>());
        actions.get(map).add(action);
    }

    public void remove(Map2D map, Action action) {
        actions.get(map).remove(action);
    }
}
