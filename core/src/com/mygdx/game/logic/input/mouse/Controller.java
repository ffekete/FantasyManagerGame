package com.mygdx.game.logic.input.mouse;

public interface Controller {

    boolean touchDown(int screenX, int screenY, int pointer, int button);
    boolean mouseMoved(int screenX, int screenY);
}
