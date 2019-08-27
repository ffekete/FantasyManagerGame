package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.compound.WaitMoveActivity;
import com.mygdx.game.logic.activity.single.IdleActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.TimedIdleActivity;
import com.mygdx.game.logic.activity.single.WaitActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.Random;

public class WanderingDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if (Alignment.FRIENDLY.equals(actor.getAlignment())) {
            if (actor.getActivityStack().isEmpty() ||
                    (actor.getActivityStack().contains(IdleActivity.class) && actor.getActivityStack().getSize() == 1)) {
                // Nothing else to do, wandering around the map for now
                int x;
                int y;
                do {
                    x = actor.getX() -10 + new Random().nextInt(actor.getX() + 20);
                    if(x >= actor.getCurrentMap().getWidth()) {
                        x = actor.getCurrentMap().getWidth() -1;
                    }
                    if(x < 0) {
                        x = 0;
                    }

                    y = actor.getY() -10 + new Random().nextInt(actor.getY() + 20);
                    if(y >= actor.getCurrentMap().getHeight()) {
                        y = actor.getCurrentMap().getHeight();
                    }
                    if(y < 0) {
                        y = 0;
                    }

                } while (actor.getCurrentMap().getTile(x, y).isObstacle());


                Activity activity = new MovementActivity(actor, x, y, 0, new PathFinder());
                actor.getActivityStack().add(activity);
                return true;
            }
        } else { // enemies are waiting and moving
            if (actor.getActivityStack().isEmpty() ||
                    (actor.getActivityStack().contains(IdleActivity.class) && actor.getActivityStack().getSize() == 1)) {
                // Nothing else to do, wandering around the map for now
                int x;
                int y;
                do {
                    x = actor.getX() -10 + new Random().nextInt(actor.getX() + 20);
                    if(x >= actor.getCurrentMap().getWidth()) {
                        x = actor.getCurrentMap().getWidth() -1;
                    }
                    if(x < 0) {
                        x = 0;
                    }

                    y = actor.getY() -10 + new Random().nextInt(actor.getY() + 20);
                    if(y >= actor.getCurrentMap().getHeight()) {
                        y = actor.getCurrentMap().getHeight() -1;
                    }
                    if(y < 0) {
                        y = 0;
                    }
                } while (actor.getCurrentMap().getTile(x, y).isObstacle());

                WaitMoveActivity activity = new WaitMoveActivity(Config.Activity.MOVEMENT_PRIORITY, WaitActivity.class);
                activity.add(new TimedIdleActivity(5 + new Random().nextInt(15)));
                activity.add(new MovementActivity(actor, x, y, 1, new PathFinder()));
                actor.getActivityStack().add(activity);
                return true;
            }
        }
        return false;
    }
}
