package com.mygdx.game.object.decoration;

import com.mygdx.game.object.WorldObject;

public interface Growable extends WorldObject  {

    void grow();
    float getMaxSize();

}
