package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.single.ExplorationActivity;

public class ExplorationDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(ExplorationActivity.class)) {
            // already doing activity, the decision chain should end here
            return true;
        }

        //System.out.println(actor.getCurrentMap().areAllLevelsExplored());
        if(Alignment.FRIENDLY.equals(actor.getAlignment()) && !Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType()) && !actor.getCurrentMap().isExplored() && !actor.getActivityStack().getCurrent().getMainClass().equals(ExplorationActivity.class)) {
            ExplorationActivity explorationActivity = new ExplorationActivity(actor.getCurrentMap() ,actor);
            actor.getActivityStack().add(explorationActivity);
            return true;
        }
        return false;
    }
}
