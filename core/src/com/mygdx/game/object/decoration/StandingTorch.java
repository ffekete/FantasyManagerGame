package com.mygdx.game.object.decoration;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.light.LightSourceType;

import java.util.Random;

public class StandingTorch implements WorldObject, LightSource, AnimatedObject, Obstacle {

    private Point coordinates;
    private float flickering = 0.0f;
    private int counter = 0;
    private Color color = Color.valueOf("FF5555");
    private int phase = new Random().nextInt(3);

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
        return color;
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

    @Override
    public int getPhase() {
        return phase;
    }

    @Override
    public float getSpeed() {
        return 0.1f;
    }
}
