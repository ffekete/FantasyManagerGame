package com.mygdx.game.map.dungeon.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.category.Category;
import com.mygdx.game.registry.ItemRegistry;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ArmorRandomiser {

    public static final ArmorRandomiser INSTANCE = new ArmorRandomiser();

    public Equipable getFor(Class<? extends Category> category, Class<? extends Actor> clazz) {

        List<Class<? extends Item>> equippables = ItemRegistry.INSTANCE.getFor(category)
                .stream()
                .filter(item -> Armor.class.isAssignableFrom(item))
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

        if(equipables.isEmpty())
            return null;

        List<Armor> armors = equipables.stream()
                .filter(e -> ((Armor) e).getAllowedClasses().contains(clazz))
                .map(e -> (Armor) e)
                .collect(Collectors.toList());

        if(armors.size() == 0)
            return null;
        return armors.get(new Random().nextInt(armors.size()));
    }
}
