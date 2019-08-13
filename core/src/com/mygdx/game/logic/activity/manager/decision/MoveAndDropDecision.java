package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.resources.Resource;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.compound.MoveAndStoreActivity;
import com.mygdx.game.logic.activity.compound.MovePickupActivity;
import com.mygdx.game.logic.activity.single.DropItemActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PickUpItemActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.logic.selector.ItemSelector;
import com.mygdx.game.object.StorageArea;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.List;
import java.util.Optional;

public class MoveAndDropDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {
        if (!actor.getActivityStack().contains(MoveAndStoreActivity.class)) {

            if (actor.getInventory().has(Resource.class)) {

                // find Items
                Item item = actor.getInventory().get(Resource.class);

                // looking for resource already in store identical to the one we have now
                Optional<StorageArea> area = ObjectRegistry.INSTANCE.getAll(actor.getCurrentMap()).stream()
                        .filter(object -> StorageArea.class.isAssignableFrom(object.getClass()))
                        .filter(object -> ((StorageArea) object).getStoredItem() != null && ((StorageArea) object).getStoredItem().equals(item.getClass()))
                        .findAny()
                        .map(object -> (StorageArea) object);

                // if not stored, looking for a new empty spot
                if(!area.isPresent()) {
                    area = ObjectRegistry.INSTANCE.getAll(actor.getCurrentMap()).stream()
                            .filter(object -> StorageArea.class.isAssignableFrom(object.getClass()) && ((StorageArea) object).getStoredItem() == null)
                            .findAny().map(object -> (StorageArea) object);
                }

                if (!area.isPresent()) {
                    return false;
                }

                // go for it
                actor.setxOffset(0.0f);
                actor.setyOffset(0.0f);

                Activity activity = new MoveAndStoreActivity(Config.BuilderActivity.STORE_PRIORITY)
                        .add(new MovementActivity(actor, (int) area.get().getX(), (int) area.get().getY(), 1, new PathFinder()))
                        .add(new DropItemActivity(actor, item, area.get()));
                actor.getActivityStack().clear();
                actor.getActivityStack().add(activity);
                ActorMovementHandler.INSTANCE.clearPath(actor);
                return true;

            }
        } else {
            // already doing activity, the decision chain should end here
            return true;
        }
        return false;
    }
}
