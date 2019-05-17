package com.mygdx.game.object.light;

import com.badlogic.gdx.graphics.Color;

public interface LightSource {

    float getX();
    float getY();
    float getArea();
    Color getColor();
    LightSourceType getType();

}
