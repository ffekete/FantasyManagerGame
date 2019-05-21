package com.mygdx.game.logic.attack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.WeaponSkill;
import com.mygdx.game.item.weapon.Weapon;

public class WeaponSkillSelector {

    public Float findBestSkillFor(Actor actor, Weapon weapon) {
        float value = 0;
        for(WeaponSkill s : actor.getWeaponSkills().keySet()) {
            if(s.canAssociate(weapon)) {
                if(value <= s.getValue(weapon.getClass()) * actor.getWeaponSkills().get(s)) {
                    value = s.getValue(weapon.getClass()) * actor.getWeaponSkills().get(s);
                }
            }
        }

        return value;
    }

}
