package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PhysicalTrainingActivity;
import com.mygdx.game.logic.activity.single.SmithingActivity;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.interactive.Anvil;
import com.mygdx.game.object.interactive.PracticeFigure;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.List;

public class SmithingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (SmithingActivity.class.equals(actor.getActivityStack().getCurrent().getMainClass())) {
            return true;
        }

        House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);
        if (house == null) {
            return false;
        }

        List<Anvil> anvils = house.getFurnitureOfType(Anvil.class);

        if (anvils.isEmpty()) {
            return false;
        }

        MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.SmithActivity.SMITHING_PRIORITY, SmithingActivity.class);
        MovementActivity movementActivity = new MovementActivity(actor, (int) anvils.get(0).getX(), (int) anvils.get(0).getY(), 1, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap()));
        SmithingActivity smithingActivity = new SmithingActivity(actor, anvils.get(0));
        moveAndInteractActivity.add(movementActivity)
                .add(smithingActivity);

        actor.getActivityStack().clear();
        actor.getActivityStack().add(moveAndInteractActivity);
        return true;

    }

}
