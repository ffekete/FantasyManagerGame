package com.mygdx.game.logic.activity;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ActorRegistry;

public class SimpleAttackActivity implements Activity, CooldownActivity {

    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    private boolean firstRun = true;
    private int priority = 97;
    private int counter = 0;

    private final Actor actor;
    private final Actor enemy;
    private boolean suspended = false;

    public SimpleAttackActivity(Actor actor, Actor enemy) {
        this.actor = actor;
        this.enemy = enemy;
        actor.setxOffset(0);
        actor.setyOffset(0);
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % actor.getAttackSpeed();
    }

    @Override
    public boolean isTriggered() {
        return counter == actor.getAttackSpeed() -1;
    }

    @Override
    public boolean isDone() {
        return Math.abs(actor.getX() - enemy.getX()) <= 1 && Math.abs(actor.getY() - enemy.getY()) <= 1;
    }

    @Override
    public void update() {
        // todo attack and roll to hit regularly
    }

    @Override
    public void init() {
        firstRun = false;
    }

    @Override
    public void cancel() {
        System.out.println(actor + " cancelled.");
        actor.setxOffset(0);
        actor.setyOffset(0);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public void suspend() {
        suspended = true;
    }

    @Override
    public void resume() {
        suspended = false;
        if(actorRegistry.getActors(actor.getCurrentMap()).contains(enemy)) {

        }
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(priority, o.getPriority());
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public void clear() {
        actor.setxOffset(0);
        actor.setyOffset(0);
        System.out.println(" I attacked and killed " + enemy);
        actorRegistry.getActors(actor.getCurrentMap()).remove(enemy);
    }

    @Override
    public boolean isCancellable() {
        // enemy is gone
        return !actorRegistry.getActors(actor.getCurrentMap()).contains(enemy) || !(Math.abs(actor.getX() - enemy.getX()) <= 1 && Math.abs(actor.getY() - enemy.getY()) <= 1 ) ;
    }
}
