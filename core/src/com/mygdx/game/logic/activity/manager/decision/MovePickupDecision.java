package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.selector.ItemSelector;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.compound.MovePickupActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PickUpItemActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;

public class MovePickupDecision implements Decision {

    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;
    private final ItemSelector itemSelector = new ItemSelector();

    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(PickUpItemActivity.class)) {
            return true;
        }

        List<Item> Items = itemRegistry.getAllItems(actor.getCurrentMap());
        if (!Items.isEmpty()) {
            // find Items
            Item item = itemSelector.findClosestItem(actor, Items, Config.Item.PICK_UP_ITEM_DISTANCE, Item.class);
            if (item != null) {
                // go for it
                actor.setxOffset(0.0f);
                actor.setyOffset(0.0f);
                Activity activity = new MovePickupActivity(Config.Activity.MOVE_PICKUP_PRIORITY, PickUpItemActivity.class)
                        .add(new MovementActivity(actor, item.getX(), item.getY(), 1, new PathFinder()))
                        .add(new PickUpItemActivity(actor, item));
                actor.getActivityStack().reset();
                actor.getActivityStack().add(activity);
                ActorMovementHandler.INSTANCE.clearPath(actor);
                return true;
            }
        }

        return false;
    }
}
