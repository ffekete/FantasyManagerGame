package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.single.DayTimeSleepActivity;
import com.mygdx.game.logic.time.DayTimeCalculator;

public class PredatorSleepingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return true;
        }

        if ((!DayTimeCalculator.INSTANCE.isItNight() || actor.isSleepy())) {
            DayTimeSleepActivity sleepActivity = new DayTimeSleepActivity(actor);
            actor.getActivityStack().add(sleepActivity);
            return true;
        }
        return false;
    }
}
