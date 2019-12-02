package com.mygdx.game.map.dungeon.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.category.Category;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ShieldRandomiser {

    public static final ShieldRandomiser INSTANCE = new ShieldRandomiser();

    public Equipable getFor(Class<? extends Category> category) {

        List<Class<? extends Item>> equippables = ItemRegistry.INSTANCE.getFor(category)
                .stream()
                .filter(item -> Shield.class.isAssignableFrom(item))
                .collect(Collectors.toList());

        if(equippables.size() == 0)
            return null;

        List<Item> equipables = equippables
                .stream().map(e -> {
                    try {
                        return e.newInstance();
                    } catch (InstantiationException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());

        if(equipables.size() == 0)
            return null;

        List<Shield> shields = equipables.stream()
                .map(e -> (Shield) e)
                .collect(Collectors.toList());

        if(shields.size() == 0)
            return null;
        return shields.get(new Random().nextInt(shields.size()));
    }
}
