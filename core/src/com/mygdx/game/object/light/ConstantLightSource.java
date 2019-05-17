package com.mygdx.game.object.light;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.actor.Actor;

public class ConstantLightSource implements LightSource {

    private final int x;
    private final int y;
    private final Color color;
    private final float area;
    private final LightSourceType lightSourceType;

    public ConstantLightSource(int x, int y, Color color, float area, LightSourceType lightSourceType) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.area = area;
        this.lightSourceType = lightSourceType;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public LightSourceType getType() {
        return lightSourceType;
    }

    @Override
    public float getArea() {
        return area;
    }
}
