package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.MentalTrainingActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.interactive.BookCase;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.List;

public class MentalTrainingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if ((actor.getActivityStack().contains(MentalTrainingActivity.class))) {
            return true;
        }

        if(actor.wantsTraining()) {

            House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);
            if (house == null) {
                return false;
            }

            List<BookCase> bookCases = house.getFurnitureOfType(BookCase.class);

            if (bookCases.isEmpty()) {
                return false;
            }

            MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.TRAINING_PRIORITY, MentalTrainingActivity.class);
            MovementActivity movementActivity = new MovementActivity(actor, (int) bookCases.get(0).getX(), (int) bookCases.get(0).getY(), 1);
            MentalTrainingActivity mentalTrainingActivity = new MentalTrainingActivity(actor, bookCases.get(0));
            moveAndInteractActivity.add(movementActivity)
                    .add(mentalTrainingActivity);

            //actor.getActivityStack().reset();
            actor.getActivityStack().add(moveAndInteractActivity);
            return true;
        }
        return false;
    }

}
