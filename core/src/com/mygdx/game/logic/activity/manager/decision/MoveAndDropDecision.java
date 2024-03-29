package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.resources.Resource;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.compound.MoveAndStoreActivity;
import com.mygdx.game.logic.activity.single.DropItemActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.object.StorageArea;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.Optional;

public class MoveAndDropDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(DropItemActivity.class)) {
            return true;
        }


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
            if (!area.isPresent()) {
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

            Activity activity = new MoveAndStoreActivity(Config.BuilderActivity.STORE_PRIORITY, DropItemActivity.class)
                    .add(new MovementActivity(actor, (int) area.get().getX(), (int) area.get().getY(), 1))
                    .add(new DropItemActivity(actor, item, area.get()));
            actor.getActivityStack().add(activity);
            //ActorMovementHandler.INSTANCE.clearPath(actor);
            return true;

        }
        return false;
    }
}
