package com.mygdx.game.logic.activity.single;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.projectile.Arrow;
import com.mygdx.game.item.projectile.PracticeArrow;
import com.mygdx.game.item.projectile.Projectile;
import com.mygdx.game.item.weapon.bow.Bow;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.BowAction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ProjectileRegistry;
import com.mygdx.game.sound.DistanceBasedSoundPlayer;

public class ShootingTrainingActivity implements Activity {

    private final ActionRegistry actionRegistry = ActionRegistry.INSTANCE;

    private Actor actor;
    private InteractiveObject object;
    private int counter = 0;
    private Action action;

    private Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit/hit07.mp3"));

    public ShootingTrainingActivity(Actor actor, InteractiveObject object) {
        this.actor = actor;
        this.object= object;
    }

    @Override
    public boolean isDone() {
        return actor.getTrainingNeeds() <= 0 || !object.canInteract(actor);
    }

    @Override
    public void update() {
        if(object.canInteract(actor)) {
            object.onInteract(actor);
            actor.increaseTrainingNeeds(-1000);
            actor.increaseSleepiness(300);
            actor.increaseHunger(200);

            if(MapRegistry.INSTANCE.getCurrentMapToShow().equals(actor.getCurrentMap())) {
                DistanceBasedSoundPlayer.play(hitSound, actor.getCoordinates(), 0.05f ,0.01f);
            }

            action = new BowAction(actor);
            actionRegistry.add(actor.getCurrentMap(), action);
            Projectile arrow = new PracticeArrow(object.getCoordinates());
            arrow.init(actor, null);
            ProjectileRegistry.INSTANCE.add(arrow);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void cancel() {
        actor.getActivityStack().clear();
    }

    @Override
    public int getPriority() {
        return Config.Activity.TRAINING_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return false;
    }

    @Override
    public void suspend() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public void clear() {
        if(object.canInteract(actor)) {
            object.onInteract(actor);
        }
    }

    @Override
    public boolean isCancellable() {
        return !object.canInteract(actor);
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    @Override
    public void countDown() {
        counter = (counter + 1) % actor.getAttackSpeed();
    }

    @Override
    public boolean isTriggered() {
        return counter == 1;
    }

    @Override
    public int compareTo(Activity o) {
        return Integer.compare(getPriority(), o.getPriority());
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }

}
