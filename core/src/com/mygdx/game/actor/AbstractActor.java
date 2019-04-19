package com.mygdx.game.actor;

import com.mygdx.game.actor.component.Attributes;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.activity.ActivityStack;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AbstractActor implements MovableActor {

    private Map<Attributes, Integer> baseAttributes;

    private int x;
    private int y;

    private ActivityStack activityStack = new ActivityStack(new PriorityQueue<>());

    private Map2D currentMap;

    List<PathFinder.Node> path;


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

    @Override
    public void moveToNextPathPoint() {
        if(path != null && !path.isEmpty()) {
            PathFinder.Node next = path.remove(path.size()-1);
            System.out.println(path.size());
            setCoordinates(next.getX(), next.getY());
        }
    }

    @Override
    public boolean hasPathPointsLeft() {
        return path.isEmpty();
    }

    @Override
    public void setPath(List<PathFinder.Node> nodes) {
        this.path = nodes;
    }

    public Integer getattribute(Attributes a) {
        return baseAttributes.get(a);
    }

    public int getMovementSpeed() {
        return 30;
    }
}
