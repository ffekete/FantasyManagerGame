package com.mygdx.game.object.decoration;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.light.LightSourceType;

public class StandingTorch implements WorldObject, LightSource, AnimatedObject {

    private Point coordinates;

    public StandingTorch(Point point) {
        coordinates = point;
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
    public float getArea() {
        return 10;
    }

    @Override
    public Color getColor() {
        return Color.valueOf("FFFF00");
    }

    @Override
    public LightSourceType getType() {
        return LightSourceType.Ambient;
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
