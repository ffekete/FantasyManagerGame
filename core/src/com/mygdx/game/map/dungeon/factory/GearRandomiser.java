package com.mygdx.game.map.dungeon.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.category.Category;
import com.mygdx.game.item.weapon.OnehandedWeapon;
import com.mygdx.game.item.weapon.TwohandedWeapon;

import java.util.Random;

public class GearRandomiser {

    public static final GearRandomiser INSTANCE = new GearRandomiser();

    public void selectGear(Class<? extends Actor> actorClass, Actor actor, Class<? extends Category> category) {

        Equipable twoHandedAvailable = WeaponRandomiser.INSTANCE.getFor(category, TwohandedWeapon.class);
        Equipable oneHandedAvailable = WeaponRandomiser.INSTANCE.getFor(category, OnehandedWeapon.class);
        Equipable shieldAvailable = ShieldRandomiser.INSTANCE.getFor(category);


        if (twoHandedAvailable == null && oneHandedAvailable == null && shieldAvailable == null) {
            return;
        }

        // if all available
        if (twoHandedAvailable != null && oneHandedAvailable != null && shieldAvailable != null) {
            if (new Random().nextBoolean()) {
                actor.equip(WeaponRandomiser.INSTANCE.getFor(category, TwohandedWeapon.class));
            } else {
                actor.equip(ShieldRandomiser.INSTANCE.getFor(category));
                actor.equip(WeaponRandomiser.INSTANCE.getFor(category, OnehandedWeapon.class));
            }

            // if sword and shield available
        } else if (oneHandedAvailable != null && shieldAvailable != null) {
            actor.equip(ShieldRandomiser.INSTANCE.getFor(category));
            actor.equip(WeaponRandomiser.INSTANCE.getFor(category, OnehandedWeapon.class));

        } else if (twoHandedAvailable != null && oneHandedAvailable != null) {
            if (new Random().nextBoolean()) {
                actor.equip(WeaponRandomiser.INSTANCE.getFor(category, TwohandedWeapon.class));
            } else {
                actor.equip(WeaponRandomiser.INSTANCE.getFor(category, OnehandedWeapon.class));
            }
        } else if (twoHandedAvailable != null) {
            actor.equip(WeaponRandomiser.INSTANCE.getFor(category, TwohandedWeapon.class));

        } else if (oneHandedAvailable != null) {
            actor.equip(WeaponRandomiser.INSTANCE.getFor(category, OnehandedWeapon.class));
        }

        if (new Random().nextInt(3) == 0) {
            actor.equip(ArmorRandomiser.INSTANCE.getFor(category, actorClass));
        }
    }
}
