package com.mygdx.game.logic.activity;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.ActorDeathHandler;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.attack.AttackController;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;

public class SimpleAttackActivity implements Activity, CooldownActivity {

    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private AnimationRegistry animationRegistry = AnimationRegistry.INSTANCE;

    private boolean firstRun = true;
    private int priority = Config.Activity.ATTACK_PRIORITY;
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
        return enemy.getHp() <= 0;
    }

    @Override
    public void update() {
        // todo attack and roll to hit regularly
        AttackController.INSTANCE.calculateAttack(actor, enemy);
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
        ActorMovementHandler.INSTANCE.clearPath(actor);
        animationRegistry.remove(enemy);
        AttackController.INSTANCE.clearAttackingHistory(actor);
        enemy.die();
    }

    @Override
    public boolean isCancellable() {
        // enemy is gone
        if (!actorRegistry.getActors(actor.getCurrentMap()).contains(enemy)) {
            System.out.println("Enemy is killed by someone else");
            return true;
        }
        if(Math.abs(actor.getX() - enemy.getX()) * (Math.abs(actor.getX() - enemy.getX()) + Math.abs(actor.getY() - enemy.getY()) * Math.abs(actor.getY() - enemy.getY())) > Config.ATTACK_DISTANCE ) {
            System.out.println("Enemy ran away");
            return true;
        }
//        if(actor.getHp() < actor.getMaxHp() * 0.1) {
//            System.out.println("I've had enough! [" + actor.getName() + "]");
//            return true;
//        }
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }
}
