package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PhysicalTrainingActivity;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.interactive.PracticeFigure;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.List;

public class PhysicalTrainingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(PhysicalTrainingActivity.class)) {
            return true;
        }

        if(actor.wantsTraining()) {

            House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);
            if (house == null) {
                return false;
            }

            List<PracticeFigure> practiceFigures = house.getFurnitureOfType(PracticeFigure.class);

            if (practiceFigures.isEmpty()) {
                return false;
            }

            MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.TRAINING_PRIORITY, PhysicalTrainingActivity.class);
            MovementActivity movementActivity = new MovementActivity(actor, (int) practiceFigures.get(0).getX(), (int) practiceFigures.get(0).getY(), 1, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap()));
            PhysicalTrainingActivity physicalTrainingActivity = new PhysicalTrainingActivity(actor, practiceFigures.get(0));
            moveAndInteractActivity.add(movementActivity)
                    .add(physicalTrainingActivity);

            actor.getActivityStack().add(moveAndInteractActivity);
            return true;
        }
        return false;
    }

}
