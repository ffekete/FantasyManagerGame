package com.mygdx.game.creator.map.object;

import com.mygdx.game.actor.Actor;

public interface InteractiveObject extends WorldObject {

    void onInteract(Actor actor);
    boolean canInteract(Actor actor);

}
