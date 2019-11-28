package com.mygdx.game.item.armor;

import com.mygdx.game.actor.BodyType;
import com.mygdx.game.item.Equipable;

import java.util.Set;

public interface Armor extends Equipable {

    int getDamageProtection();
    Set<BodyType> getCompatibleBodyTypes();

}
