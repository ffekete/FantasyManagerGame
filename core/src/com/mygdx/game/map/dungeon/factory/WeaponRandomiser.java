package com.mygdx.game.map.dungeon.factory;

import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.category.Category;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WeaponRandomiser {

    public static final WeaponRandomiser INSTANCE = new WeaponRandomiser();

    public Equipable getFor(Class<? extends Category> category, Class<? extends Weapon> clazz) {
        try {
            List<Class<? extends Item>> equippables = ItemRegistry.INSTANCE.getFor(category)
                    .stream()
                    .filter(item -> clazz.isAssignableFrom(item))
                    .collect(Collectors.toList());

            if(equippables.size() == 0)
                return null;

            return (Equipable) equippables
                    .get(new Random().nextInt(equippables.size()))
                    .newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Equipable getFor(Class<? extends Category> category) {
        return getFor(category, Weapon.class);
    }
}
