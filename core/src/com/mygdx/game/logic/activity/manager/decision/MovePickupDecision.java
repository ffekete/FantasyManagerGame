package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.SelectionUtils;
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

    @Override
    public boolean decide(Actor actor) {
        if(!actor.getActivityStack().contains(MovePickupActivity.class)) {
            List<Item> Items = itemRegistry.getAllItems(actor.getCurrentMap());
            if(!Items.isEmpty()) {
                // find Items
                Item item = SelectionUtils.findClosestItem(actor, Items, Config.Item.PICK_UP_ITEM_DISTANCE, Item.class);
                if(item != null) {
                    // go for it
                    actor.setxOffset(0.0f);
                    actor.setyOffset(0.0f);
                    Activity activity = new MovePickupActivity(Config.Activity.MOVE_PICKUP_PRIORITY)
                            .add(new MovementActivity(actor, item.getX(), item.getY(), 1, new PathFinder()))
                            .add(new PickUpItemActivity(actor, item));
                    actor.getActivityStack().clear();
                    actor.getActivityStack().add(activity);
                    ActorMovementHandler.INSTANCE.clearPath(actor);
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
