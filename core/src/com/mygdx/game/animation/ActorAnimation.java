package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import bsh.This;

public class ActorAnimation implements Animation {

    private static final int HEIGHT = 30;
    private static final int WIDTH = 30;
    private static final int GRIDX = 5;
    private static final int GRIDY = 5;

    public static Builder builder() {
        return new Builder();
    }

    private Texture head;

    private Texture arms;

    private Texture legs;

    private Texture torso;

    private Texture leftHandItem;

    private Texture rightHandItem;

    double phase = 0;

    public ActorAnimation() {

    }

    @Override
    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, boolean flip) {
        int nextFrameX = (int)phase % GRIDX;
        int nextFrameY = (int)phase / GRIDY;

        phase = (phase + 20 * Gdx.graphics.getDeltaTime()) % (GRIDX*GRIDY);

        spriteBatch.draw(torso, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
        spriteBatch.draw(head, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
        spriteBatch.draw(arms, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
        spriteBatch.draw(legs, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);

        if(leftHandItem != null)
            spriteBatch.draw(leftHandItem, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
        if(rightHandItem != null)
            spriteBatch.draw(rightHandItem, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * WIDTH, nextFrameY * HEIGHT, WIDTH, HEIGHT, flip, false);
    }

    public void setHead(Texture head) {
        this.head = head;
    }

    public void setArms(Texture arms) {
        this.arms = arms;
    }

    public void setLegs(Texture legs) {
        this.legs = legs;
    }

    public void setTorso(Texture torso) {
        this.torso = torso;
    }

    public void setLeftHandItem(Texture leftHandItem) {
        this.leftHandItem = leftHandItem;
    }

    public void setRightHandItem(Texture rightHandItem) {
        this.rightHandItem = rightHandItem;
    }

    @Override
    public void dispose() {
        head.dispose();
        arms.dispose();
        legs.dispose();
        torso.dispose();

        if(leftHandItem != null)
            leftHandItem.dispose();
        if(rightHandItem != null)
            rightHandItem.dispose();
    }

    public static class Builder {

        private ActorAnimation actorAnimation;

        public Builder() {
            actorAnimation = new ActorAnimation();
        }

        public Builder withHead(Texture head) {
            actorAnimation.head = head;
            return this;
        }

        public Builder withLegs(Texture legs) {
            actorAnimation.legs = legs;
            return this;
        }

        public Builder withArms(Texture arms) {
            actorAnimation.arms = arms;
            return this;
        }

        public Builder withTorso(Texture torso) {
            actorAnimation.torso = torso;
            return this;
        }

        public Builder withLeftHandItem(Texture item) {
            actorAnimation.leftHandItem = item;
            return this;
        }

        public Builder withRightHandItem(Texture item) {
            actorAnimation.rightHandItem = item;
            return this;
        }

        public Animation build() {
            return this.actorAnimation;
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
