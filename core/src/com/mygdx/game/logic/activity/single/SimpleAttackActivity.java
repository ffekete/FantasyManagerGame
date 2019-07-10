package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.SwingAttackAction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.CooldownActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.attack.AttackController;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.TextureRegistry;

import java.awt.font.ShapeGraphicAttribute;

public class SimpleAttackActivity implements Activity, CooldownActivity {

    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private AnimationRegistry animationRegistry = AnimationRegistry.INSTANCE;
    private ActionRegistry actionRegistry = ActionRegistry.INSTANCE;
    private AttackController attackController = AttackController.INSTANCE;

    private boolean firstRun = true;
    private int priority = Config.Activity.ATTACK_PRIORITY;
    private int counter = 0;

    private final Actor actor;
    private final Actor enemy;
    private Action action;
    private boolean suspended = false;
    private ShapeGraphicAttribute target;

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
        if(MathUtil.distance(actor.getCoordinates(), enemy.getCoordinates()) <= 1) {
            AttackController.INSTANCE.calculateAttack(actor, enemy);
            action = new SwingAttackAction(actor.getX(), actor.getY(), TextureRegistry.INSTANCE.getFor(actor.getRightHandItem().getClass()), actor);
            actionRegistry.add(actor.getCurrentMap(), action);
        }
    }

    @Override
    public void init() {
        firstRun = false;

        attackController.registerAttackHistory(actor, enemy);

        action = new SwingAttackAction(actor.getX(), actor.getY(), TextureRegistry.INSTANCE.getFor(actor.getRightHandItem().getClass()), actor);
        actionRegistry.add(actor.getCurrentMap(), action);
    }

    @Override
    public void cancel() {
        actor.setxOffset(0);
        actor.setyOffset(0);
        actionRegistry.remove(actor.getCurrentMap(), action);
        AttackController.INSTANCE.clearAttackingHistory(actor);
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
        actionRegistry.remove(actor.getCurrentMap(), action);
    }

    @Override
    public void resume() {
        suspended = false;
        actionRegistry.add(actor.getCurrentMap(), action);
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
        ActorMovementHandler.INSTANCE.clearPath(actor);
        animationRegistry.remove(enemy);
        AttackController.INSTANCE.clearAttackingHistory(actor);
        actionRegistry.remove(enemy.getCurrentMap(), action);
        enemy.die(actor);
    }

    @Override
    public boolean isCancellable() {
        if(MathUtil.distance(actor.getCoordinates(), enemy.getCoordinates()) > actor.getAttackRange()) {
            System.out.println("Enemy is too far away");
            return true;
        }
        // enemy is gone
        if (!actorRegistry.getActors(actor.getCurrentMap()).contains(enemy)) {
            System.out.println("Enemy is killed by someone else");
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

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    public Actor getTarget() {
        return enemy;
    }
}
