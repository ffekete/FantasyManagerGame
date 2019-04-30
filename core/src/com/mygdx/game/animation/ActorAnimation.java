package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ExplorationActivity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;

import java.util.Map;

import bsh.This;

public class ActorAnimation {

    private int offset;
    private int width = 50;
    private int height = 60;

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

    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, Direction direction, Activity activity ) {

        if(!phaseMapper.containsKey(activity.getCurrentClass())) {
            return;
        }

        int nextFrameX = (int)phase % 4;

        phase = (phase + 5 * Gdx.graphics.getDeltaTime()) % (4);


        offset = phaseMapper.get(activity.getCurrentClass()).get(direction).offset;

        spriteBatch.draw(head, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * width,  height * offset, width, height, false, false);
        spriteBatch.draw(torso, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * width,  height * offset, width, height, false, false);
        spriteBatch.draw(legs, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * width, height * offset, width, height, false, false);
        spriteBatch.draw(arms, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * width, height * offset, width, height, false, false);

        if(leftHandItem != null)
            spriteBatch.draw(leftHandItem, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * width, height * offset, width, height, false, false);
        if(rightHandItem != null)
            spriteBatch.draw(rightHandItem, x,y, 0, 0, 1, 1, scale, scale, 0, nextFrameX * width, height * offset, width, height, false, false);
    }

    private final Map<Class<? extends Activity>, Map<Direction, Phase>> phaseMapper = ImmutableMap.<Class<? extends Activity>, Map<Direction,Phase>>builder()
            .put(MovementActivity.class, ImmutableMap.<Direction, Phase>builder()
                    .put(Direction.UP, Phase.MoveUp)
                    .put(Direction.DOWN, Phase.MoveDown)
                    .put(Direction.LEFT, Phase.MoveLeft)
                    .put(Direction.RIGHT, Phase.MoveRight)
                    .build())
            .put(ExplorationActivity.class, ImmutableMap.<Direction, Phase>builder()
                    .put(Direction.UP, Phase.MoveUp)
                    .put(Direction.DOWN, Phase.MoveDown)
                    .put(Direction.LEFT, Phase.MoveLeft)
                    .put(Direction.RIGHT, Phase.MoveRight)
                    .build())
            .build();

    public enum Phase {
        MoveUp(1),
        MoveDown(0),
        MoveLeft(2),
        MoveRight(3),
        AttackUp(7),
        AttackDown(8),
        AttackLeft(9),
        AttackRight(10);

        private int offset;

        Phase(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return offset;
        }
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

        public ActorAnimation build() {
            return this.actorAnimation;
        }

    }

    private class Size {
        double x;
        double y;
        double offset;
        double width;

        private Size(double x, double y, double width, double offset) {
            this.x = x;
            this.y = y;
            this.offset = offset;
            this.width = width;
        }
    }

}
