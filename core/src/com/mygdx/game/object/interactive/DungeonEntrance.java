package com.mygdx.game.object.interactive;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.logic.CharacterMap2dSwitcher;
import com.mygdx.game.logic.Point;

public class DungeonEntrance implements InteractiveObject {

    private final Map2D to;
    private final CharacterMap2dSwitcher characterMap2dSwitcher = CharacterMap2dSwitcher.INSTANCE;

    private Point coordinates;

    public DungeonEntrance(Map2D to) {
        this.to = to;
    }

    @Override
    public void onInteract(Actor actor) {
        characterMap2dSwitcher.switchTo(to, actor.getCurrentMap(), actor);
    }

    @Override
    public boolean canInteract(Actor actor) {
        return !to.isExplored();
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


}
