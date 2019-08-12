package com.mygdx.game.object;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.resources.Resource;

public interface Cuttable extends Targetable, WorldObject {

    void addProgress(float progress);

    Class<? extends Resource> finish(Actor actor);

    boolean isFinished();
}
