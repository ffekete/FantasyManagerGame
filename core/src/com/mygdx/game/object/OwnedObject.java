package com.mygdx.game.object;

import com.mygdx.game.actor.Actor;

public interface OwnedObject {

    Actor getOwner();
    void setOwner(Actor actor);

}
