package com.mygdx.game.logic.visibility;

public enum VisitedArea {
    NOT_VISITED(0),
    VISIBLE(1),
    VISITED_BUT_NOT_VISIBLE(2);

    private final int value;

    VisitedArea(int value) {
        this.value = value;
    }
}
