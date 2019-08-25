package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.single.SleepActivity;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.map.Map2D;

public class WildLifeSleepingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return true;
        }

        if ((DayTimeCalculator.INSTANCE.isItNight() || actor.isSleepy()) && Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())) {
            SleepActivity sleepActivity = new SleepActivity(actor);

            actor.getActivityStack().clear();
            actor.getActivityStack().add(sleepActivity);
            return true;
        }
        return false;
    }
}
