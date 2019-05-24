package com.mygdx.game.logic.attack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.shield.Shield;

public class ShieldSelector {

    public int getShieldValue(Actor victim) {
        if(victim.getLeftHandItem() == null) {
            return 0;
        }
        return Shield.class.isAssignableFrom(victim.getLeftHandItem().getClass()) ? ((Shield)victim.getLeftHandItem()).getDefense() : 0;
    }

}
