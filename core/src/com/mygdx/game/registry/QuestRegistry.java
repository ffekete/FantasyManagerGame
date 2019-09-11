package com.mygdx.game.registry;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.quest.Quest;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuestRegistry {

    public static final QuestRegistry INSTANCE = new QuestRegistry();

    private List<Quest> finished = new CopyOnWriteArrayList<>();

    private Map<Map2D, List<Quest>> unfinished = new HashMap<>();

    public void add(Map2D map2D, Quest quest) {
        unfinished.computeIfAbsent(map2D, v -> new CopyOnWriteArrayList<>());
        unfinished.get(map2D).add(quest);
    }

    public void finish(Quest quest) {

        //unfinished.get(map).get(unfinished.get(map).indexOf(quest)).finish();

        unfinished.get(quest.getMap()).remove(quest);
    }

    public List<Quest> getFinished() {
        return finished;
    }

    public List<Quest> getUnfinished(Map2D map) {
        unfinished.computeIfAbsent(map, v -> new CopyOnWriteArrayList<>());
        return unfinished.get(map);
    }
}
