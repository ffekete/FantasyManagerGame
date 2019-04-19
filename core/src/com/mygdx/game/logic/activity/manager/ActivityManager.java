package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;

import java.util.Random;

public class ActivityManager {

    private PathFinder pathFinder;

    public ActivityManager() {
        this.pathFinder = new PathFinder();
    }

    public void manage(Actor actor) {
        if(actor.getActivityStack().isEmpty()) {
            int x;
            int y;
            do {
                x = new Random().nextInt(actor.getCurrentMap().getWidth());
                y = new Random().nextInt(actor.getCurrentMap().getHeight());
            } while(actor.getCurrentMap().getTile(x,y).isObstacle());

            Activity activity = new MovementActivity(actor, x,y, pathFinder);
            actor.getActivityStack().add(activity);
        }
    }

}
