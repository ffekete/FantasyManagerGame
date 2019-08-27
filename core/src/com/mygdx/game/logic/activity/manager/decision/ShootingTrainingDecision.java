package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.ShootingTrainingActivity;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.interactive.ShootingTarget;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.List;

public class ShootingTrainingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (ShootingTrainingActivity.class.equals(actor.getActivityStack().getCurrent().getMainClass())) {
            return true;
        }

        if(actor.wantsTraining()) {

            House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);
            if (house == null) {
                return false;
            }

            List<ShootingTarget> shootingTargets = house.getFurnitureOfType(ShootingTarget.class);

            if (shootingTargets.isEmpty()) {
                return false;
            }

            MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.TRAINING_PRIORITY, ShootingTrainingActivity.class);
            MovementActivity movementActivity = new MovementActivity(actor, (int) shootingTargets.get(0).getX(), (int) shootingTargets.get(0).getY(), 1, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap()));
            ShootingTrainingActivity shootingTrainingActivity = new ShootingTrainingActivity(actor, shootingTargets.get(0));
            moveAndInteractActivity.add(movementActivity)
                    .add(shootingTrainingActivity);

            actor.getActivityStack().reset();
            actor.getActivityStack().add(moveAndInteractActivity);
            return true;
        }
        return false;
    }

}
