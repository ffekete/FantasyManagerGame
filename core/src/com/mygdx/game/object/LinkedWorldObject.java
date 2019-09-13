package com.mygdx.game.object;

import com.mygdx.game.logic.Point;

public interface LinkedWorldObject extends WorldObject {
    LinkedWorldObject getExit();
    void setExit(LinkedWorldObject object);
}
