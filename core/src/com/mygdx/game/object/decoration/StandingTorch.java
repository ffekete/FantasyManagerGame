package com.mygdx.game.object.decoration;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.light.LightSourceType;

import java.util.Random;

public class StandingTorch implements WorldObject, LightSource, AnimatedObject {

    private Point coordinates;
    private float flickering = 0.0f;
    private int counter = 0;

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
        return 4 + flickering;
    }

    @Override
    public Color getColor() {
        return Color.valueOf("FF5555");
    }

    @Override
    public LightSourceType getType() {
        return LightSourceType.Ambient;
    }

    @Override
    public void update() {
        counter += 1;

        if(counter >= 5) {
            flickering = new Random().nextFloat() / 5;
            counter = 0;
        }
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
}
