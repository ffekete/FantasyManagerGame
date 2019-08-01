package com.mygdx.game.input.mouse;

public class SandboxInputController implements Controller {

    public static final SandboxInputController INSTANCE = new SandboxInputController();

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
}
