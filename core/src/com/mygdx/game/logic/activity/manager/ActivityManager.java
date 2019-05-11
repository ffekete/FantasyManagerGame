package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.food.Food;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.potion.HealingPotion;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.manager.decision.ConsumeHealingpotionDecision;
import com.mygdx.game.logic.activity.manager.decision.Decision;
import com.mygdx.game.logic.activity.manager.decision.DungeonVisitingDecision;
import com.mygdx.game.logic.activity.manager.decision.EatingDecision;
import com.mygdx.game.logic.activity.manager.decision.EquipDecision;
import com.mygdx.game.logic.activity.manager.decision.ExplorationDecision;
import com.mygdx.game.logic.activity.manager.decision.MoveAndAttackDecision;
import com.mygdx.game.logic.activity.manager.decision.MovePickupDecision;
import com.mygdx.game.logic.activity.manager.decision.MovePickupEatDecision;
import com.mygdx.game.logic.activity.manager.decision.WanderingDecision;
import com.mygdx.game.logic.activity.single.ConsumeHealingPotion;
import com.mygdx.game.logic.activity.single.EquipActivity;
import com.mygdx.game.logic.activity.single.ExplorationActivity;
import com.mygdx.game.logic.activity.single.IdleActivity;
import com.mygdx.game.logic.activity.compound.MovePickupActivity;
import com.mygdx.game.logic.activity.compound.MovePickupEatActivity;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PickUpItemActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.activity.single.SimpleEatingActivity;
import com.mygdx.game.logic.activity.single.TimedIdleActivity;
import com.mygdx.game.logic.activity.single.WaitActivity;
import com.mygdx.game.logic.activity.compound.WaitMoveActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ActivityManager {

    private List<Decision> decisionTable;

    public ActivityManager() {
        decisionTable = new LinkedList<>();
        decisionTable.add(new ConsumeHealingpotionDecision());
        decisionTable.add(new EquipDecision());
        decisionTable.add(new MoveAndAttackDecision());
        decisionTable.add(new MovePickupDecision());
        decisionTable.add(new EatingDecision());
        decisionTable.add(new DungeonVisitingDecision());
        decisionTable.add(new ExplorationDecision());
        decisionTable.add(new MovePickupEatDecision());
        decisionTable.add(new WanderingDecision());
    }

    public void manage(Actor actor) {

        if (!actor.getActivityStack().finishedCurrentPeriod())
            return;

        for (Decision decision : decisionTable) {
            if(decision.decide(actor)) {
                break;
            }
        }

    }

//        if(actor.getInventory().has(HealingPotion.class) && !actor.getActivityStack().contains(ConsumeHealingPotion.class)) {
//            if(actor.getHp() < actor.getMaxHp() / Config.Actor.LOW_HP_THRESHOLD_DIVIDER) {
//                activity = new ConsumeHealingPotion(actor, actor.getInventory().get(HealingPotion.class));
//                actor.getActivityStack().add(activity);
//            }
//            return;
//        }

//        if(actor.getInventory().has(Equipable.class) && !actor.getActivityStack().contains(EquipActivity.class)) {
//            Equipable equipable = actor.getInventory().get(Equipable.class);
//            EquipActivity equipActivity = new EquipActivity(actor, equipable);
//            actor.getActivityStack().add(equipActivity);
//            return;
//        }

//        if(!actor.getActivityStack().contains(MoveThenAttackActivity.class)) {
//            Actor enemy = findClosestEnemy(actor, actorRegistry.getActors(actor.getCurrentMap()), Config.ATTACK_DISTANCE);
//            if(enemy != null) {
//                CompoundActivity compoundActivity = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY);
//                if(actor.getAlignment().equals(Alignment.FRIENDLY)) {
//                    compoundActivity.add(new MovementActivity(actor, enemy.getX(), enemy.getY(), 1, new PathFinder()));
//                    compoundActivity.add(new SimpleAttackActivity(actor, enemy));
//                }
//                else {
//                    compoundActivity.add(new WaitActivity(actor, enemy, 1));
//                    compoundActivity.add(new SimpleAttackActivity(actor, enemy));
//                }
//
//                actor.getActivityStack().add(compoundActivity);
//                return;
//            }
//        }

//        if(!actor.getActivityStack().contains(MovePickupActivity.class)) {
//            if(!items.isEmpty()) {
//                // find items
//                Item item = findClosestItem(actor, items, Config.Item.PICK_UP_ITEM_DISTANCE, Item.class);
//                if(item != null) {
//                    // go for it
//                    System.out.println(String.format("I'mpicking up %s!", item));
//                    activity = new MovePickupActivity(Config.Activity.MOVE_PICKUP_PRIORITY)
//                            .add(new MovementActivity(actor, item.getX(), item.getY(), 1, new PathFinder()))
//                            .add(new PickUpItemActivity(actor, item));
//                    actor.getActivityStack().suspendAll();
//                    actor.getActivityStack().add(activity);
//                    return;
//                }
//            }
//        }

//        if(actor.isHungry() && !actor.getActivityStack().contains(SimpleEatingActivity.class)) {
//            if(actor.getInventory().has(Food.class)) {
//                SimpleEatingActivity simpleEatingActivity = new SimpleEatingActivity(actor);
//                actor.getActivityStack().suspendAll();
//                actor.getActivityStack().add(simpleEatingActivity);
//                return;
//            }
//        }

//        if(Alignment.FRIENDLY.equals(actor.getAlignment()) && !actor.getCurrentMap().isExplored() && !actor.getActivityStack().contains(ExplorationActivity.class)) {
//            ExplorationActivity explorationActivity = new ExplorationActivity(actor.getCurrentMap() ,actor);
//            actor.getActivityStack().add(explorationActivity);
//            return;
//        }

//        if (actor.isHungry() && !actor.getActivityStack().contains(MovePickupEatActivity.class)) {
//
//            if(!items.isEmpty()) {
//                // find food
//                Food food = (Food) findClosestItem(actor, items, Config.Item.PICK_UP_ITEM_DISTANCE, Food.class);
//                if(food != null) {
//                    // go for it
//                    System.out.println(String.format("I'm hungry for %s!", food));
//                    activity = new MovePickupEatActivity(Config.Activity.MOVE_PICKUP_EAT_PRIORITY)
//                            .add(new MovementActivity(actor, food.getX(), food.getY(), 1, new PathFinder()))
//                            .add(new PickUpItemActivity(actor, food))
//                            .add(new SimpleEatingActivity(actor));
//                    actor.getActivityStack().suspendAll();
//                    actor.getActivityStack().add(activity);
//                    return;
//                }
//            }
//        }

//        if(Alignment.FRIENDLY.equals(actor.getAlignment())) {
//            if (actor.getActivityStack().isEmpty() ||
//                    (actor.getActivityStack().contains(IdleActivity.class) && actor.getActivityStack().getSize() == 1)) {
//                // Nothing else to do, wandering around the map for now
//                int x;
//                int y;
//                do {
//                    x = new Random().nextInt(actor.getCurrentMap().getWidth());
//                    y = new Random().nextInt(actor.getCurrentMap().getHeight());
//                } while (actor.getCurrentMap().getTile(x, y).isObstacle());
//
//
//                activity = new MovementActivity(actor, x, y, 0, new PathFinder());
//                actor.getActivityStack().add(activity);
//                return;
//            }
//        } else { // enemies are waiting and moving
//            if (actor.getActivityStack().isEmpty() ||
//                    (actor.getActivityStack().contains(IdleActivity.class) && actor.getActivityStack().getSize() == 1)) {
//                // Nothing else to do, wandering around the map for now
//                int x;
//                int y;
//                do {
//                    x = new Random().nextInt(actor.getCurrentMap().getWidth());
//                    y = new Random().nextInt(actor.getCurrentMap().getHeight());
//                } while (actor.getCurrentMap().getTile(x, y).isObstacle());
//
//
//                activity = new WaitMoveActivity(Config.Activity.MOVEMENT_PRIORITY);
//                ((WaitMoveActivity) activity).add(new TimedIdleActivity(5 + new Random().nextInt(5)));
//                ((WaitMoveActivity) activity).add(new MovementActivity(actor, x, y, 1, new PathFinder()));
//                actor.getActivityStack().add(activity);
//                return;
//            }
//        }
//    }

//    private Actor findClosestEnemy(Actor actor, Set<Actor> actors, Integer maxDistance) {
//        Actor selectedActor = null;
//        int x = actor.getX();
//        int y = actor.getY();
//        float minDistance = Integer.MAX_VALUE;
//
//        for(Actor actor1 : actors) {
//            if(!actor.getAlignment().getEnemies().contains(actor1.getAlignment())) {
//                continue;
//            }
//
//            int a = actor1.getX();
//            int b = actor1.getY();
//
//            if(!VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(a,b).contains(actor)) {
//                continue;
//            }
//
//            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
//            if(distance < minDistance) {
//                selectedActor = actor1;
//                minDistance = distance;
//            }
//        }
//        return minDistance > maxDistance*maxDistance ? null : selectedActor;
//    }

//    private Item findClosestItem(Actor actor, List<Item> items, Integer maxDistance, Class<? extends Item> clazz) {
//        Item selectedItem = null;
//        int x = actor.getX();
//        int y = actor.getY();
//
//        float minDistance = Float.MAX_VALUE;
//        for(Item item : items) {
//            if(!clazz.isAssignableFrom(item.getClass())) {
//                continue;
//            }
//            int a = item.getX();
//            int b = item.getY();
//
//            if(!VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(a,b).contains(actor)) {
//                continue;
//            }
//
//            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
//            if(distance < minDistance) {
//                selectedItem = item;
//                minDistance = distance;
//            }
//        }
//        return minDistance > maxDistance*maxDistance ? null : selectedItem;
//    }
}
