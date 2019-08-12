package com.mygdx.game.logic.command;

import com.mygdx.game.actor.Actor;

public interface Command<T> {

    Actor getExecutor();

    void setExecutor(Actor executor);

    T getTarget();

}
