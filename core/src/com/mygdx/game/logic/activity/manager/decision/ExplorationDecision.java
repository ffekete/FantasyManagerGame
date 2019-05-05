package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.single.ExplorationActivity;

public class ExplorationDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {
        if(Alignment.FRIENDLY.equals(actor.getAlignment()) && !actor.getCurrentMap().isExplored() && !actor.getActivityStack().contains(ExplorationActivity.class)) {
            ExplorationActivity explorationActivity = new ExplorationActivity(actor.getCurrentMap() ,actor);
            actor.getActivityStack().add(explorationActivity);
            return true;
        }
        return false;
    }
}
