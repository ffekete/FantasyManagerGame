package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.InteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.SmithingActivity;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.interactive.Anvil;
import com.mygdx.game.object.interactive.Smelter;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.List;

public class SmithingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if ((actor.getActivityStack().contains(SmithingActivity.class))) {
            if ((SmithingActivity.class.isAssignableFrom(actor.getActivityStack().getCurrent().getCurrentActivity().getClass()) && ((SmithingActivity) actor.getActivityStack().getCurrent().getCurrentActivity()).getInteractedCounter() % 10 != 0))
                return true;
            else {
                if ((SmithingActivity.class.isAssignableFrom(actor.getActivityStack().getCurrent().getCurrentActivity().getClass()) && ((SmithingActivity) actor.getActivityStack().getCurrent().getCurrentActivity()).getInteractedCounter() % 10 == 0)) {

                    ((SmithingActivity) actor.getActivityStack().getCurrent().getCurrentActivity()).increaseInteractedCounter();

                    House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);
                    if (house == null) {
                        actor.getActivityStack().reset();
                        return false;
                    }

                    List<Anvil> anvils = house.getFurnitureOfType(Anvil.class);
                    List<Smelter> smelters = house.getFurnitureOfType(Smelter.class);

                    if (anvils.isEmpty() || smelters.isEmpty()) {
                        actor.getActivityStack().reset();
                        return false;
                    }

                    MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.SmithActivity.SMELTER_USING_PRIORITY, SmithingActivity.class);
                    MovementActivity movementActivity = new MovementActivity(actor, (int) smelters.get(0).getX(), (int) smelters.get(0).getY(), 1, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap()));
                    InteractActivity smithingActivity = new InteractActivity(actor, smelters.get(0));
                    moveAndInteractActivity.add(movementActivity)
                            .add(smithingActivity);

                    actor.getActivityStack().add(moveAndInteractActivity);
                    return true;
                }

                return true;
            }
        }

        House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);
        if (house == null) {
            return false;
        }

        List<Anvil> anvils = house.getFurnitureOfType(Anvil.class);
        List<Smelter> smelters = house.getFurnitureOfType(Smelter.class);

        if (anvils.isEmpty() || smelters.isEmpty()) {
            return false;
        }

        MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.SmithActivity.SMITHING_PRIORITY, SmithingActivity.class);
        MovementActivity movementActivity = new MovementActivity(actor, (int) anvils.get(0).getX(), (int) anvils.get(0).getY(), 1, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap()));
        SmithingActivity smithingActivity = new SmithingActivity(actor, anvils.get(0));
        moveAndInteractActivity.add(movementActivity)
                .add(smithingActivity);

        actor.getActivityStack().add(moveAndInteractActivity);
        return true;

    }

}
