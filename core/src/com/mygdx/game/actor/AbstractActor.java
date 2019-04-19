package com.mygdx.game.actor;

import com.mygdx.game.actor.component.Attributes;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.activity.ActivityStack;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AbstractActor implements Actor {

    private Map<Attributes, Integer> baseAttributes;

    private int x;
    private int y;

    private ActivityStack activityStack = new ActivityStack(new PriorityQueue<>());

    private Map2D currentMap;

    public AbstractActor() {
        this.baseAttributes = new HashMap<>();
        for(Attributes a : Attributes.values()) {
            baseAttributes.put(a, 10);
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public com.mygdx.game.logic.activity.ActivityStack getActivityStack() {
        return this.activityStack;
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getattribute(Attributes a) {
        return baseAttributes.get(a);
    }

    public int getMovementSpeed() {
        return 50 - getattribute(Attributes.Dexterity);
    }

    @Override
    public Map2D getCurrentMap() {
        return currentMap;
    }

    @Override
    public void setCurrentMap(Map2D map) {
        this.currentMap = map;
    }
}
