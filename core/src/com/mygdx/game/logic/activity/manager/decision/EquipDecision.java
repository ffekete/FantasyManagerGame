package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.logic.activity.single.EquipActivity;

public class EquipDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {
        if(actor.getInventory().has(Equipable .class) && !actor.getActivityStack().contains(EquipActivity .class)) {
            Equipable equipable = actor.getInventory().get(Equipable.class);
            EquipActivity equipActivity = new EquipActivity(actor, equipable);
            actor.getActivityStack().add(equipActivity);
            return true;
        }
        return false;
    }

    public EquipDecision() {
    }
}
