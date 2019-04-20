package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Food;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.ActivityStack;
import com.mygdx.game.logic.activity.HungerActivity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;
import java.util.Random;

public class ActivityManager {

    private PathFinder pathFinder;
    private ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    public ActivityManager() {
        this.pathFinder = new PathFinder();
    }

    public void manage(Actor actor) {
        Activity activity;

        if (actor.isHungry() && !actor.getActivityStack().contains(HungerActivity.class)) {
            List<Item> items = itemRegistry.getAllItems(actor.getCurrentMap(), Food.class);

            if(!items.isEmpty()) {
                // find food
                Food food = findClosest(actor, items);
                // go for it
                System.out.println(String.format("I'm hungry for %s!", items.get(0)));
                activity = new HungerActivity(actor, food);
                actor.getActivityStack().suspendAll();
                actor.getActivityStack().add(activity);
                return;
            }
        }
        if (actor.getActivityStack().isEmpty()) {
            // Nothing else to do, wandering around the map for now
            int x;
            int y;
            do {
                x = new Random().nextInt(actor.getCurrentMap().getWidth());
                y = new Random().nextInt(actor.getCurrentMap().getHeight());
            } while (actor.getCurrentMap().getTile(x, y).isObstacle());

            activity = new MovementActivity(actor, x, y, pathFinder);
            actor.getActivityStack().add(activity);
        }
    }

    private Food findClosest(Actor actor, List<Item> items) {
        Item food = items.get(0);
        int x = actor.getX();
        int y = actor.getY();

        for(Item item : items) {
            int a = item.getX();
            int b = item.getY();

            if(Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b) < Math.abs(x-food.getX())*Math.abs(x-food.getX()) + Math.abs(y-food.getY()) * Math.abs(y-food.getY())) {
                food = item;
            }
        }
        return (Food)food;
    }

}
