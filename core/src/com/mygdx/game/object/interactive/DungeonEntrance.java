package com.mygdx.game.object.interactive;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.CharacterMap2dSwitcher;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.LinkedWorldObject;
import com.mygdx.game.object.Obstacle;

public class DungeonEntrance implements InteractiveObject, Obstacle, LinkedWorldObject {

    private final Map2D to;
    private final Map2D from;
    private LinkedWorldObject exit;

    private final CharacterMap2dSwitcher characterMap2dSwitcher = CharacterMap2dSwitcher.INSTANCE;

    private Point coordinates;

    public DungeonEntrance(Map2D to, Map2D from) {
        this.to = to;
        this.from = from;
    }

    @Override
    public void onInteract(Actor actor) {
        if (actor.getCurrentMap().equals(from))
            // descend to the next level of the dungeon
            characterMap2dSwitcher.switchTo(to, actor.getCurrentMap(), actor);
        else
            // climb up
            characterMap2dSwitcher.switchTo(actor.getCurrentMap(), to, actor);
    }

    @Override
    public boolean canInteract(Actor actor) {
        return !to.isExplored();
    }

    @Override
    public void finished(Actor actor) {

    }

    @Override
    public float getX() {
        return coordinates.getX();
    }

    @Override
    public float getY() {
        return coordinates.getY();
    }

    @Override
    public void setCoordinates(Point point) {
        this.coordinates = point;
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return 1f;
    }

    public Map2D getTo() {
        return to;
    }

    @Override
    public LinkedWorldObject getExit() {
        return exit;
    }

    @Override
    public void setExit(LinkedWorldObject object) {
        this.exit = object;
    }

    public Map2D getFrom() {
        return from;
    }
}
