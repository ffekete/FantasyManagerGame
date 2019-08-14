package com.mygdx.game.object;

import com.mygdx.game.logic.Point;

public interface Sittable {

    Point getNextFreeSpace();
    boolean hasFreeSpace();
    void bookSpace(Point point);
    void freeUp(Point point);

    boolean allFree();
}
