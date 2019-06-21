package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.actor.hero.*;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.actor.monster.Orc;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.actor.monster.SkeletonWarrior;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.ConsumeHealingPotion;
import com.mygdx.game.logic.activity.single.ExplorationActivity;
import com.mygdx.game.logic.activity.single.IdleActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PreCalculatedMovementActivity;
import com.mygdx.game.logic.activity.single.TimedIdleActivity;
import com.mygdx.game.logic.activity.single.WaitActivity;

import java.util.Map;

public class FullBodyActorAnimation implements ActorAnimation {

    private Texture warriorTexture = new Texture(Gdx.files.internal("actors/Warrior.png"));
    private Texture skeletonTexture = new Texture(Gdx.files.internal("actors/Skeleton.png"));
    private Texture goblinTexture = new Texture(Gdx.files.internal("actors/goblin.png"));
    private Texture orcTexture = new Texture(Gdx.files.internal("actors/orc.png"));
    private Texture wizardTexture = new Texture(Gdx.files.internal("actors/Wizard.png"));
    private Texture skeletonWarriorTexture = new Texture(Gdx.files.internal("actors/SkeletonWarrior.png"));

    private float phase = 0;

    @Override
    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, Direction direction, Activity activity, Class<? extends Actor> actor) {
        phase = (phase + 0.1f) % 3f;

        int row = getRow(activity);

        spriteBatch.draw(getTexture(actor), x,y, 0, 0, 1, 1, scale, scale, 0, (int)phase * 32,  32 * row, 32,32, getFlip(direction), false);

    }

    private boolean getFlip(Direction direction) {
        return direction.equals(Direction.LEFT) || direction.equals(Direction.UP) ? true : false;
    }

    private Texture getTexture(Class<? extends Actor> actorClass) {
        return textureMap.get(actorClass);
    }

    private int getRow(Activity activity) {
        if(WaitActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                IdleActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                TimedIdleActivity.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.WALK.column;
        }

        if(ConsumeHealingPotion.class.isAssignableFrom(activity.getCurrentClass())) {
            return AnimationPhase.WALK.column;
        }

        if(PreCalculatedMovementActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
        MovementActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                ExplorationActivity.class.isAssignableFrom(activity.getCurrentClass())
        ) {
            return AnimationPhase.WALK.column;
        }
        return AnimationPhase.ATTACK.column; // attack
    }

    private final Map<Class<? extends Actor>, Texture> textureMap = ImmutableMap.<Class<? extends Actor>, Texture>builder()
            .put(Warrior.class, warriorTexture)
            .put(Skeleton.class, skeletonTexture)
            .put(Goblin.class, goblinTexture)
            .put(Orc.class, orcTexture)
            .put(Wizard.class, wizardTexture)
            .put(SkeletonWarrior.class, skeletonWarriorTexture)
            .build();

    @Override
    public void dispose() {
        for(Texture t:textureMap.values()) {
            t.dispose();
        }
    }

    private enum AnimationPhase {
        WALK(0),
        ATTACK(1);

        private int column;

        AnimationPhase(int column) {
            this.column = column;
        }
    }
}
