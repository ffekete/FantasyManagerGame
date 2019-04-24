package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActorRegistry {

    public static final ActorRegistry INSTANCE = new ActorRegistry();

    private List<Actor> actors = new CopyOnWriteArrayList<>();

    public void add(Actor actor) {
        actors.add(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }
}
