package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.worker.Shopkeeper;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.activity.compound.MoveAndSelltemActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.SellItemActivity;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.registry.ActorRegistry;

import java.util.Optional;

public class SellItemDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {
        if(actor.isSleeping()) {
            return false;
        }

        if(actor.getActivityStack().contains(SellItemActivity.class)) {
            return true;
        }

        Item item = null;

        // find item not needed
        if(actor.getInventory().has(Weapon.class)) {
            item = actor.getInventory().get(Weapon.class);
        }

        if(item == null && actor.getInventory().has(Shield.class)) {
            item = actor.getInventory().get(Shield.class);
        }

        if(item == null && actor.getInventory().has(Armor.class)) {
            item = actor.getInventory().get(Armor.class);
        }

        if(item == null) {
            return false;
        }

        // find shopkepper
        Optional<Actor> actorOptional = ActorRegistry.INSTANCE.getActors(actor.getCurrentMap(), Cluster.ofSurrounding(actor.getCurrentMap(), actor.getX(), actor.getY(), 5))
                .stream()
                .filter(actor1 -> Shopkeeper.class.isAssignableFrom(actor1.getClass()))
                .findFirst();

        if(!actorOptional.isPresent()) {
            return false;
        }

        // sell item
        MoveAndSelltemActivity moveAndSelltemActivity = new MoveAndSelltemActivity(Config.Activity.SELL_ITEM_PRIORITY, SellItemActivity.class);
        moveAndSelltemActivity.add(new MovementActivity(actor, actorOptional.get().getX(), actorOptional.get().getY(), 1));
        moveAndSelltemActivity.add(new SellItemActivity(actor, actorOptional.get(), item));
        actor.getActivityStack().add(moveAndSelltemActivity);
        return true;
    }
}
