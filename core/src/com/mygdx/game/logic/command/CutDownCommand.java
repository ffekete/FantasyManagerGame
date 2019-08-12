package com.mygdx.game.logic.command;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.object.Cuttable;

public class CutDownCommand implements Command<Cuttable> {

    private Cuttable target;
    private Actor executor;

    public CutDownCommand(Cuttable target) {
        this.target = target;
    }

    @Override
    public Cuttable getTarget() {
        return target;
    }

    @Override
    public Actor getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(Actor executor) {
        this.executor = executor;
    }
}
