package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ExplorationActivity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.activity.SimpleAttackActivity;
import com.mygdx.game.logic.activity.WaitActivity;

import java.util.Map;

public class BodyPartsBasedActorAnimation implements ActorAnimation {

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

    public BodyPartsBasedActorAnimation() {

    }

    @Override
    public void drawKeyFrame(SpriteBatch spriteBatch, float x, float y, int scale, Direction direction, Activity activity, Class<? extends Actor> actorClass) {

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
            .put(WaitActivity.class, ImmutableMap.<Direction, Phase>builder()
                    .put(Direction.UP, Phase.MoveUp)
                    .put(Direction.DOWN, Phase.MoveDown)
                    .put(Direction.LEFT, Phase.MoveLeft)
                    .put(Direction.RIGHT, Phase.MoveRight)
                    .build())
            .put(SimpleAttackActivity.class, ImmutableMap.<Direction, Phase>builder()
                    .put(Direction.UP, Phase.AttackUp)
                    .put(Direction.DOWN, Phase.AttackDown)
                    .put(Direction.LEFT, Phase.AttackLeft)
                    .put(Direction.RIGHT, Phase.AttackRight)
                    .build())
            .build();

    public enum Phase {
        MoveUp(1),
        MoveDown(0),
        MoveLeft(2),
        MoveRight(3),
        AttackUp(5),
        AttackDown(4),
        AttackLeft(6),
        AttackRight(7);

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

        private BodyPartsBasedActorAnimation bodyPartsBasedActorAnimation;

        public Builder() {
            bodyPartsBasedActorAnimation = new BodyPartsBasedActorAnimation();
        }

        public Builder withHead(Texture head) {
            bodyPartsBasedActorAnimation.head = head;
            return this;
        }

        public Builder withLegs(Texture legs) {
            bodyPartsBasedActorAnimation.legs = legs;
            return this;
        }

        public Builder withArms(Texture arms) {
            bodyPartsBasedActorAnimation.arms = arms;
            return this;
        }

        public Builder withTorso(Texture torso) {
            bodyPartsBasedActorAnimation.torso = torso;
            return this;
        }

        public Builder withLeftHandItem(Texture item) {
            bodyPartsBasedActorAnimation.leftHandItem = item;
            return this;
        }

        public Builder withRightHandItem(Texture item) {
            bodyPartsBasedActorAnimation.rightHandItem = item;
            return this;
        }

        public ActorAnimation build() {
            return this.bodyPartsBasedActorAnimation;
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
