package com.mygdx.game.logic.action;

import com.mygdx.game.logic.Point;

public interface Action {

    void update();
    void finish();
    boolean isFinished();
    void setCoordinates(Point newCoordinates);
}
