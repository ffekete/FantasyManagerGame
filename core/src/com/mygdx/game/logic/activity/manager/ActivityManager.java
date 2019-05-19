package com.mygdx.game.logic.activity.manager;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.activity.manager.decision.ConsumeHealingpotionDecision;
import com.mygdx.game.logic.activity.manager.decision.Decision;
import com.mygdx.game.logic.activity.manager.decision.DungeonVisitingDecision;
import com.mygdx.game.logic.activity.manager.decision.EatingDecision;
import com.mygdx.game.logic.activity.manager.decision.EquipDecision;
import com.mygdx.game.logic.activity.manager.decision.ExplorationDecision;
import com.mygdx.game.logic.activity.manager.decision.MoveAndAttackDecision;
import com.mygdx.game.logic.activity.manager.decision.MovePickupDecision;
import com.mygdx.game.logic.activity.manager.decision.MovePickupEatDecision;
import com.mygdx.game.logic.activity.manager.decision.WanderingDecision;

import java.util.LinkedList;
import java.util.List;

public class ActivityManager {

    private List<Decision> decisionTable;

    public ActivityManager() {
        decisionTable = new LinkedList<>();
        decisionTable.add(new ConsumeHealingpotionDecision());
        decisionTable.add(new EquipDecision());
        decisionTable.add(new MoveAndAttackDecision());
        decisionTable.add(new MovePickupDecision());
        decisionTable.add(new EatingDecision());
        decisionTable.add(new DungeonVisitingDecision());
        decisionTable.add(new ExplorationDecision());
        decisionTable.add(new MovePickupEatDecision());
        decisionTable.add(new WanderingDecision());
    }

    public void manage(Actor actor) {
        for (int i = 0; i < decisionTable.size(); i++) {
            if(decisionTable.get(i).decide(actor)) {
                break;
            }
        }
    }
}
