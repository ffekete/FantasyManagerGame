package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.Activity;

public interface ActorAnimation  {
    void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, float scale, Direction direction, Activity activity, Class<? extends Actor> actorClass);
}
