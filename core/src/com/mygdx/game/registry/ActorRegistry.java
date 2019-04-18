package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;

import java.util.ArrayList;
import java.util.List;

public class ActorRegistry {

    private List<Actor> actors = new ArrayList<>();

    public void add(Actor actor) {
        actors.add(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }
}
