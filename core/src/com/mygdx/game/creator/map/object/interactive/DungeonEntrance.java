package com.mygdx.game.creator.map.object.interactive;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.object.InteractiveObject;
import com.mygdx.game.logic.Point;

public class DungeonEntrance implements InteractiveObject {

    private final Map2D to;

    private int x;
    private int y;

    public DungeonEntrance(int x, int y, Map2D to) {
        this.x = x;
        this.y = y;
        this.to = to;
    }

    @Override
    public void onInteract(Actor actor) {
        actor.setCurrentMap(to);
        actor.setCoordinates(to.getDefaultSpawnPoint());
    }

    @Override
    public boolean canInteract(Actor actor) {
        return !to.isExplored();
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
    public void setCoordinates(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }


}
