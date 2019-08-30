package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.weapon.TwohandedWeapon;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.activity.single.EquipActivity;
import com.mygdx.game.logic.attack.WeaponSkillSelector;

public class EquipDecision implements Decision {

    private WeaponSkillSelector weaponSkillSelector = new WeaponSkillSelector();

    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(EquipActivity.class)) {
            return true;
        }

        if (actor.getInventory().has(Equipable.class)) {
            Equipable equipable = actor.getInventory().get(Equipable.class);

            if (Shield.class.isAssignableFrom(equipable.getClass()) && (
                    actor.getRightHandItem() == null || !TwohandedWeapon.class.isAssignableFrom(actor.getRightHandItem().getClass()))
                    ) {
                if (actor.getLeftHandItem() == null || actor.getLeftHandItem().getPower() < equipable.getPower()) {
                    EquipActivity equipActivity = new EquipActivity(actor, equipable);
                    actor.getActivityStack().add(equipActivity);
                    return true;
                }
            } else if (OneHandedItem.class.isAssignableFrom(equipable.getClass())) {

                if (actor.getRightHandItem() == null || (actor.getRightHandItem().getPower() < equipable.getPower() || weaponSkillSelector.findBestSkillFor(actor, (Weapon) equipable) > weaponSkillSelector.findBestSkillFor(actor, (Weapon) actor.getRightHandItem()))) {
                    if (actor.getRightHandItem() != null) {
                        System.out.println("Weapon skill for old: " + weaponSkillSelector.findBestSkillFor(actor, (Weapon) actor.getRightHandItem()));
                        System.out.println("Weapon skill for new: " + weaponSkillSelector.findBestSkillFor(actor, (Weapon) equipable));
                    }
                    EquipActivity equipActivity = new EquipActivity(actor, equipable);
                    actor.getActivityStack().add(equipActivity);
                    return true;
                }
            } else if (TwohandedWeapon.class.isAssignableFrom(equipable.getClass())) {
                if (actor.getRightHandItem() == null || (actor.getRightHandItem().getPower() < equipable.getPower() || weaponSkillSelector.findBestSkillFor(actor, (Weapon) equipable) > weaponSkillSelector.findBestSkillFor(actor, (Weapon) actor.getRightHandItem()))) {
                    if (actor.getRightHandItem() != null) {
                        System.out.println("Weapon skill for old: " + weaponSkillSelector.findBestSkillFor(actor, (Weapon) actor.getRightHandItem()));
                        System.out.println("Weapon skill for new: " + weaponSkillSelector.findBestSkillFor(actor, (Weapon) equipable));
                    }
                    EquipActivity equipActivity = new EquipActivity(actor, equipable);
                    actor.getActivityStack().add(equipActivity);
                    return true;
                }
            } else if (Armor.class.isAssignableFrom(equipable.getClass())) {
                if (actor.getWornArmor() == null || actor.getWornArmor().getPower() < equipable.getPower()) {
                    EquipActivity equipActivity = new EquipActivity(actor, equipable);
                    actor.getActivityStack().add(equipActivity);
                    return true;
                }
            }
            return false;
        }

        return actor.getActivityStack().contains(EquipActivity.class);
    }

    public EquipDecision() {
    }
}
