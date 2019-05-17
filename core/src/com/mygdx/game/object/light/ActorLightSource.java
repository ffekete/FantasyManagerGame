package com.mygdx.game.object.light;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.actor.Actor;

public class ActorLightSource implements LightSource {

    private final Actor actor;
    private final Color color;
    private final float area;
    private final LightSourceType lightSourceType;

    public ActorLightSource(Actor actor, Color color, float area, LightSourceType lightSourceType) {
        this.actor = actor;
        this.color = color;
        this.area = area;
        this.lightSourceType = lightSourceType;
    }

    @Override
    public float getX() {
        return actor.getX() + actor.getxOffset();
    }

    @Override
    public float getY() {
        return actor.getY() + actor.getyOffset();
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
