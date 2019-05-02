package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ExplorationActivity;
import com.mygdx.game.logic.activity.MovementActivity;

import java.util.Map;

public class FullBodyActorAnimation implements ActorAnimation {

    Texture warriorTexture = new Texture(Gdx.files.internal("warrior_ss.png"));
    Texture rogueTexture = new Texture(Gdx.files.internal("rogue_ss.png"));

    float phase = 0;

    @Override
    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, Direction direction, Activity activity, Class<? extends Actor> actor) {
        phase = (phase + 0.1f) % 10f;

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
        if(MovementActivity.class.isAssignableFrom(activity.getCurrentClass()) ||
                ExplorationActivity.class.isAssignableFrom(activity.getCurrentClass())
        ) {
            return 2;
        }
        return 3;
    }

    Map<Class<? extends Actor>, Texture> textureMap = ImmutableMap.<Class<? extends Actor>, Texture>builder()
            .put(Warrior.class, warriorTexture)
            .put(Goblin.class, rogueTexture)
            .build();
}
