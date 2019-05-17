package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.compound.MovePickupActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PickUpItemActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;

public class MovePickupDecision implements Decision {

    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {
        if(!actor.getActivityStack().contains(MovePickupActivity.class)) {
            List<Item> items = itemRegistry.getAllItems(actor.getCurrentMap());
            if(!items.isEmpty()) {
                // find items
                Item item = DecisionUtils.findClosestItem(actor, items, Config.Item.PICK_UP_ITEM_DISTANCE, Item.class);
                if(item != null) {
                    // go for it
                    Activity activity = new MovePickupActivity(Config.Activity.MOVE_PICKUP_PRIORITY)
                            .add(new MovementActivity(actor, item.getX(), item.getY(), 1, new PathFinder()))
                            .add(new PickUpItemActivity(actor, item));
                    actor.getActivityStack().suspendAll();
                    actor.getActivityStack().add(activity);
                    return true;
                }
            }
        } else {
            // already doing activity, the decision chain should end here
            return true;
        }
        return false;
    }
}
