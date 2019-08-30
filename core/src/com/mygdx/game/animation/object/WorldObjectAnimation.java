package com.mygdx.game.animation.object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.animation.Animation;
import com.mygdx.game.object.AnimatedObject;
import com.mygdx.game.registry.TextureRegistry;

public class WorldObjectAnimation implements Animation {

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;

    private final AnimatedObject animatedObject;

    private float phase;
    private float speed;

    private Texture texture;

    public WorldObjectAnimation(AnimatedObject animatedObject) {
        this.animatedObject = animatedObject;
        phase = animatedObject.getPhase();
        speed = animatedObject.getSpeed();

    }

    @Override
    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, Direction direction) {
        texture = textureRegistry.getForObjcetAnimation(animatedObject.getClass());

        int maxPhase = texture.getWidth() / 32;

        spriteBatch.draw(texture, x, y, 0, 0, 1, 1, scale, scale, 0, (int) phase * 32, 0, 32, 32, false, false);

        phase = (phase + speed) % maxPhase;
    }

    @Override
    public void dispose() {

    }
}
