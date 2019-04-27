package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Animation implements Disposable  {

    private static final int HEIGHT = 30;
    private static final int WIDTH = 30;
    private static final int GRIDX = 5;
    private static final int GRIDY = 5;

    public static Builder builder() {
        return new Builder();
    }

    private Texture head;
    private Size headSize;

    private Texture arms;
    private Size armsSize;

    private Texture legs;
    private Size legsSize;

    private Texture torso;
    private Size torsoSize;

    private Texture leftHandItem;
    private Size leftHadItemSize;

    private Texture rightHandItem;
    private Size rightHadItemSize;

    double phase = 0;

    public Animation() {

    }

    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, boolean flip) {
        int nextFrameX = (int)phase % GRIDX;
        int nextFrameY = (int)phase / GRIDY;

        phase = (phase + 20 * Gdx.graphics.getDeltaTime()) % (GRIDX*GRIDY);

        spriteBatch.draw(torso, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
        spriteBatch.draw(head, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
        spriteBatch.draw(arms, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
        spriteBatch.draw(legs, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);

    }

    @Override
    public void dispose() {
        head.dispose();
        arms.dispose();
        legs.dispose();
        torso.dispose();
    }

    public static class Builder {

        private Animation animation;

        public Builder() {
            animation = new Animation();
        }

        public Builder withHead(Texture head) {
            animation.head = head;
            return this;
        }

        public Builder withLegs(Texture legs) {
            animation.legs = legs;
            return this;
        }

        public Builder withArms(Texture arms) {
            animation.arms = arms;
            return this;
        }

        public Builder withTorso(Texture torso) {
            animation.torso = torso;
            return this;
        }

        public Animation build() {
            return this.animation;
        }

    }

    private class Size {
        double x;
        double y;
        double height;
        double width;

        private Size(double x, double y, double width, double height) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
        }
    }

}
