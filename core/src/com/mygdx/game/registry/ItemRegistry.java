package com.mygdx.game.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.armor.*;
import com.mygdx.game.item.category.*;
import com.mygdx.game.item.food.Bread;
import com.mygdx.game.item.potion.SmallAntiVenomPotion;
import com.mygdx.game.item.potion.SmallHealingPotion;
import com.mygdx.game.item.potion.SmallManaPotion;
import com.mygdx.game.item.shield.LargeShield;
import com.mygdx.game.item.shield.LionShield;
import com.mygdx.game.item.shield.MediumShield;
import com.mygdx.game.item.shield.SmallShiled;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.mace.GiantClub;
import com.mygdx.game.item.weapon.staff.JadeStaff;
import com.mygdx.game.item.weapon.sword.*;
import com.mygdx.game.item.weapon.twohandedsword.Flamberge;
import com.mygdx.game.item.weapon.twohandedsword.FlambergePlusOne;
import com.mygdx.game.map.Map2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ItemRegistry {

    public static final ItemRegistry INSTANCE = new ItemRegistry();

    private final Map<Class<? extends Category>, List<Class< ? extends Item>>> itemTierMap = ImmutableMap.<Class<? extends Category>, List<Class<? extends Item>>>builder()
            .put(Tier1.class, ImmutableList.<Class<? extends Item>>builder()
                    // Food
                    .add(Bread.class)
                    // Potion
                    .add(SmallHealingPotion.class)
                    .add(SmallManaPotion.class)
                    .add(SmallAntiVenomPotion.class)
                    // Shield
                    .add(SmallShiled.class)
                    .add(MediumShield.class)
                    // Sword
                    .add(ShortSword.class)
                    .add(Flamberge.class)
                    // staff
                    .add(JadeStaff.class)
                    // bow
                    .add(LongBow.class)
                    // Armor
                    .add(LeatherArmor.class)
                    .add(ChainMailArmor.class)
                    .add(Robe.class)
                    .build())

            .put(Tier2.class, ImmutableList.<Class<? extends Item>>builder()
                    // Food
                    // Potion
                    // Shield
                    .add(LargeShield.class)
                    // Sword
                    .add(ShortSwordPlusOne.class)
                    .add(FlambergePlusOne.class)
                    // Armor
                    .add(PlateMailArmor.class)
                    .build())

            .put(Tier3.class, ImmutableList.<Class<? extends Item>>builder()
                    // Food
                    // Potion
                    // Shield
                    .add(LionShield.class)
                    // Sword
                    .add(GiantClub.class)
                    // Armor
                    .add(BlackPlateMail.class)
                    .add(TrollArmor.class)
                    .build())

            .put(Tier4.class, ImmutableList.<Class<? extends Item>>builder()
                    // Food
                    // Potion
                    // Shield
                    // Sword
                    .add(ShortSwordPlusFour.class)
                    // Armor
                    .build())

            .put(Legendary.class, ImmutableList.<Class<? extends Item>>builder()
                    // Food
                    // Potion
                    // Shield
                    // Sword
                    .add(FlameTongue.class)
                    .add(PoisonFang.class)
                    // Armor
                    .build())

            .build();

    private Map<Map2D, List<Item>> items;

    public ItemRegistry() {
        this.items = new ConcurrentHashMap<>();
    }

    public List<Item> getAllItems(Map2D map) {
        if(items.containsKey(map))
            return items.get(map);
        return Collections.emptyList();
    }

    public List<Item> getAllItems(Map2D map, Class clazz) {
        return items.get(map).parallelStream().filter(item -> clazz.isAssignableFrom(item.getClass())).collect(Collectors.toList());

    }

    public void add(Map2D map, Item item) {
        items.computeIfAbsent(map, value -> new ArrayList<>());
        items.get(map).add(item);
    }
    public void remove(Map2D map, Item item) {
        items.get(map).remove(item);
    }

    public List<Class<? extends Item>> getFor(Class<? extends Category> category) {
        return itemTierMap.get(category);
    }

}
