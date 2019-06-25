package com.mygdx.game.object.light;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.registry.LightSourceRegistry;

public class TimedLightSource implements LightSource {

    private final int x;
    private final int y;
    private final Color color;
    private final float area;
    private final LightSourceType lightSourceType;
    private int counter;

    public TimedLightSource(int x, int y, Color color, float area, LightSourceType lightSourceType, int counter) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.area = area;
        this.lightSourceType = lightSourceType;
        this.counter = counter;
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
    public void update() {
        counter--;
        if(counter < 0) {
            LightSourceRegistry.INSTANCE.remove(this);
        }
    }

    @Override
    public float getArea() {
        return area;
    }
}
