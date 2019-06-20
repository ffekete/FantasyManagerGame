package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.SelectionUtils;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.food.Food;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.compound.MovePickupEatActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PickUpItemActivity;
import com.mygdx.game.logic.activity.single.SimpleEatingActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;

public class MovePickupEatDecision implements Decision {

    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {
        if (actor.isHungry() && !actor.getActivityStack().contains(MovePickupEatActivity.class)) {

            List<Item> items = itemRegistry.getAllItems(actor.getCurrentMap());
            if(!items.isEmpty()) {
                // find food
                Food food = (Food) SelectionUtils.findClosestItem(actor, items, Config.Item.PICK_UP_ITEM_DISTANCE, Food.class);
                if(food != null) {
                    // go for it
                    Activity activity = new MovePickupEatActivity(Config.Activity.MOVE_PICKUP_EAT_PRIORITY)
                            .add(new MovementActivity(actor, food.getX(), food.getY(), 1, new PathFinder()))
                            .add(new PickUpItemActivity(actor, food))
                            .add(new SimpleEatingActivity(actor));
                    actor.getActivityStack().clear();
                    actor.getActivityStack().add(activity);
                    return true;
                }
            }
        } else if(actor.getActivityStack().contains(MovePickupEatActivity.class)) {
            // already doing activity, the decision chain should end here
            return true;
        }
        return false;
    }
}
