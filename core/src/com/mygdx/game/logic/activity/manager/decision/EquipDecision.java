package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.activity.single.EquipActivity;

public class EquipDecision implements Decision {

    @Override
    public boolean decide(Actor actor) {
        if(!actor.getActivityStack().contains(EquipActivity.class)) {
            if(actor.getInventory().has(Equipable.class)) {
                Equipable equipable = actor.getInventory().get(Equipable.class);

                if(Shield.class.isAssignableFrom(equipable.getClass())) {
                    if(actor.getLeftHandItem() == null || actor.getLeftHandItem().getPower() < equipable.getPower()) {
                        EquipActivity equipActivity = new EquipActivity(actor, equipable);
                        actor.getActivityStack().add(equipActivity);
                        return true;
                    }
                } else if(Weapon.class.isAssignableFrom(equipable.getClass())) {
                    if(actor.getRightHandItem() == null || actor.getRightHandItem().getPower() < equipable.getPower()) {
                        EquipActivity equipActivity = new EquipActivity(actor, equipable);
                        actor.getActivityStack().add(equipActivity);
                        return true;
                    }
                } else if(Armor.class.isAssignableFrom(equipable.getClass())) {
                    if(actor.getWornArmor() == null || actor.getWornArmor().getPower() < equipable.getPower()) {
                        EquipActivity equipActivity = new EquipActivity(actor, equipable);
                        actor.getActivityStack().add(equipActivity);
                        return true;
                    }
                }
                return false;
            }
        }

        return actor.getActivityStack().contains(EquipActivity.class);
    }

    public EquipDecision() {
    }
}
