package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.InteractActivity;
import com.mygdx.game.logic.activity.single.InteractWithPracticeFigureActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.TrainingObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.PracticeFigure;
import com.mygdx.game.object.decoration.TreasureChest;
import com.mygdx.game.object.house.House;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PhysicalTrainingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (InteractWithPracticeFigureActivity.class.equals(actor.getActivityStack().getCurrent().getCurrentClass())) {
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

            MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.TRAINING_PRIORITY);
            MovementActivity movementActivity = new MovementActivity(actor, (int) practiceFigures.get(0).getX(), (int) practiceFigures.get(0).getY(), 1, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap()));
            InteractWithPracticeFigureActivity interactWithPracticeFigureActivity = new InteractWithPracticeFigureActivity(actor, practiceFigures.get(0));
            moveAndInteractActivity.add(movementActivity)
                    .add(interactWithPracticeFigureActivity);

            actor.getActivityStack().clear();
            actor.getActivityStack().add(moveAndInteractActivity);
            return true;
        }
        return false;
    }

}
