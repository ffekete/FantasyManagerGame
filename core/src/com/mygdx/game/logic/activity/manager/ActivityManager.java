package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Food;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.MovePickupActivity;
import com.mygdx.game.logic.activity.MovePickupEatActivity;
import com.mygdx.game.logic.activity.SimpleEatingActivity;
import com.mygdx.game.logic.activity.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.MovementActivity;
import com.mygdx.game.logic.activity.PickUpItemActivity;
import com.mygdx.game.logic.activity.SimpleAttackActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.List;
import java.util.Random;

public class ActivityManager {

    private ItemRegistry itemRegistry = ItemRegistry.INSTANCE;
    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    public ActivityManager() { }

    public void manage(Actor actor) {
        Activity activity;

        if(actor.getAlignment().equals(Alignment.ENEMY)) {
            return;
        }
        List<Item> items = itemRegistry.getAllItems(actor.getCurrentMap());

        if(!actor.getActivityStack().contains(MoveThenAttackActivity.class)) {
            Actor enemy = findClosestEnemy(actor, actorRegistry.getActors(), Config.ATTACK_DISTANCE);
            if(enemy != null) {
                System.out.println("Enemy sighted");
                CompoundActivity compoundActivity = new MoveThenAttackActivity(97);
                compoundActivity.add(new MovementActivity(actor, enemy.getX(), enemy.getY(), 1, new PathFinder()));
                compoundActivity.add(new SimpleAttackActivity(actor, enemy));
                actor.getActivityStack().add(compoundActivity);
                return;
            }
        }

        if(!actor.getActivityStack().contains(MovePickupActivity.class)) {
            if(!items.isEmpty()) {
                // find items
                Item item = findClosestFood(actor, items, Config.Item.PICK_UP_ITEM_DISTANCE);
                if(item != null) {
                    // go for it
                    System.out.println(String.format("I'mpicking up %s!", item));
                    activity = new MovePickupActivity(98)
                            .add(new MovementActivity(actor, item.getX(), item.getY(), 1, new PathFinder()))
                            .add(new PickUpItemActivity(actor, item));
                    actor.getActivityStack().suspendAll();
                    actor.getActivityStack().add(activity);
                    return;
                }
            }
        }

        if(actor.isHungry() && !actor.getActivityStack().contains(SimpleEatingActivity.class)) {
            if(!actor.getInventory().has(Food.class)) {
                SimpleEatingActivity simpleEatingActivity = new SimpleEatingActivity(actor);
                actor.getActivityStack().suspendAll();
                actor.getActivityStack().add(simpleEatingActivity);
                return;
            }
        }

        if (actor.isHungry() && !actor.getActivityStack().contains(MovePickupEatActivity.class)) {

            if(!items.isEmpty()) {
                // find food
                Food food = (Food)findClosestFood(actor, items, Config.Item.PICK_UP_ITEM_DISTANCE);
                // go for it
                System.out.println(String.format("I'm hungry for %s!", food));
                activity = new MovePickupEatActivity(99)
                    .add(new MovementActivity(actor, food.getX(), food.getY(), 1, new PathFinder()))
                        .add(new PickUpItemActivity(actor, food))
                        .add(new SimpleEatingActivity(actor));
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


            activity = new MovementActivity(actor, x, y, 0, new PathFinder());
            actor.getActivityStack().add(activity);
        }
    }

    private Actor findClosestEnemy(Actor actor, List<Actor> actors, Integer maxDistance) {
        Actor selectedActor = null;
        int x = actor.getX();
        int y = actor.getY();
        float minDistance = Integer.MAX_VALUE; //Math.abs(x-selectedActor.getX())*Math.abs(x-selectedActor.getX()) + Math.abs(y-selectedActor.getY()) * Math.abs(y-selectedActor.getY());

        for(Actor actor1 : actors) {
            if(!actor.getAlignment().getEnemies().contains(actor1.getAlignment())) {
                continue;
            }

            int a = actor1.getX();
            int b = actor1.getY();

            if(!VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(a,b).contains(actor)) {
                continue;
            }

            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
            if(distance < minDistance) {
                selectedActor = actor1;
                minDistance = distance;
            }
        }
        return minDistance > maxDistance*maxDistance ? null : selectedActor;
    }

    private Item findClosestFood(Actor actor, List<Item> items, Integer maxDistance) {
        Item selectedItem = null;
        int x = actor.getX();
        int y = actor.getY();

        float minDistance = Float.MAX_VALUE;
        for(Item item : items) {
            int a = item.getX();
            int b = item.getY();

            if(!VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(a,b).contains(actor)) {
                continue;
            }

            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
            if(distance < minDistance) {
                selectedItem = item;
                minDistance = distance;
            }
        }
        return minDistance > maxDistance*maxDistance ? null : selectedItem;
    }
}
