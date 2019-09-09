package com.mygdx.game.logic.activity.manager.decision.shopkeeper;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.manager.decision.Decision;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.ShopKeepingActivity;
import com.mygdx.game.logic.activity.single.SmithingActivity;
import com.mygdx.game.object.furniture.ShopkeepersDesk;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.interactive.Anvil;
import com.mygdx.game.object.interactive.Smelter;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.List;

public class ShopKeepingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if ((actor.getActivityStack().contains(ShopKeepingActivity.class))) {
            return true;
        }


        House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);
        if (house == null) {
            return false;
        }

        List<ShopkeepersDesk> counterParts = house.getFurnitureOfType(ShopkeepersDesk.class);

        if (counterParts.isEmpty()) {
            return false;
        }

        MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.ShopKeeperActivity.SHOPKEEPING_PRIORITY, ShopKeepingActivity.class);
        MovementActivity movementActivity = new MovementActivity(actor, (int) counterParts.get(0).getX(), (int) counterParts.get(0).getY(), 0, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap()));
        ShopKeepingActivity shopKeepingActivity = new ShopKeepingActivity(actor, counterParts.get(0));
        moveAndInteractActivity.add(movementActivity)
                .add(shopKeepingActivity);

        actor.getActivityStack().add(moveAndInteractActivity);
        return true;

    }

}
