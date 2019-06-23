package com.mygdx.game.actor.component.skill;

import com.google.common.collect.ImmutableMap;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.item.weapon.twohandedsword.TwoHandedSword;

import java.util.Map;

public enum WeaponSkill implements Skill {

    Warhammer(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(com.mygdx.game.item.weapon.warhammer.Warhammer.class, 1f)
            .build()),
    Sword(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(com.mygdx.game.item.weapon.sword.Sword.class, 1f)
            .put(TwoHandedSword.class, 0.5f)
            .build()),
    Mace(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(com.mygdx.game.item.weapon.mace.Mace.class, 1f)
            .put(com.mygdx.game.item.weapon.flail.Flail.class, 0.5f)
            .build()),
    Staff(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(com.mygdx.game.item.weapon.staff.Staff.class, 1f)
            .put(com.mygdx.game.item.weapon.mace.Mace.class, 0.5f)
            .build()),
    Dagger(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(com.mygdx.game.item.weapon.dagger.Dagger.class, 1f)
            .put(com.mygdx.game.item.weapon.sword.Sword.class, 0.5f)
            .build()),
    Bow(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(com.mygdx.game.item.weapon.bow.Bow.class, 1f)
            .build()),
    TwoHandedSwords(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(TwoHandedSword.class, 1f)
            .put(com.mygdx.game.item.weapon.sword.Sword.class, 0.5f)
            .build()),
    Flail(ImmutableMap.<Class<? extends Weapon>, Float>builder()
            .put(com.mygdx.game.item.weapon.flail.Flail.class, 1f)
            .put(com.mygdx.game.item.weapon.mace.Mace.class, 0.5f)
            .build());

    Map<Class<? extends Weapon>, Float> associatedItems;

    WeaponSkill(Map<Class<? extends Weapon>, Float> associatedItems) {
        this.associatedItems = associatedItems;
    }

    public boolean canAssociate(Weapon weapon) {
        return this.associatedItems.keySet().stream().anyMatch(associatedWeapon -> associatedWeapon.isAssignableFrom(weapon.getClass()));
    }

    public float getValue(Class<? extends Weapon> weapon) {
        for(Class clazz : weapon.getInterfaces()) {
            if(associatedItems.containsKey(clazz))
                return associatedItems.get(clazz);
        }
        return 0.0f;
    }
}
