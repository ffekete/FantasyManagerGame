package com.mygdx.game.map.dungeon.factory;

import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.monster.MonsterTier;
import com.mygdx.game.item.category.*;
import com.mygdx.game.map.dungeon.Decorator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.map.dungeon.DungeonGenerator;
import com.mygdx.game.map.dungeon.MapGenerator;
import com.mygdx.game.map.dungeon.cave.CaveDungeonCreator;
import com.mygdx.game.map.dungeon.cave.CaveDungeonDecorator;
import com.mygdx.game.map.dungeon.decorator.ItemPlacementHandler;
import com.mygdx.game.map.dungeon.room.DungeonWithRoomsCreator;
import com.mygdx.game.registry.MapRegistry;

import java.util.Map;
import java.util.Random;

public class DungeonFactory {

    public static final DungeonFactory INSTANCE = new DungeonFactory();

    private Map<Class<? extends MapGenerator>, Decorator> decoratorMap = ImmutableMap.<Class<? extends MapGenerator>, Decorator>builder()
            .put(CaveDungeonCreator.class, CaveDungeonDecorator.INSTANCE)
            .put(DungeonWithRoomsCreator.class, CaveDungeonDecorator.INSTANCE)
            .build();

    private final ItemPlacementHandler itemPlacementHandler = ItemPlacementHandler.INSTANCE;

    public Dungeon create(Class<? extends DungeonGenerator> clazz) {

        DungeonTheme dungeonTheme = DungeonTheme.values()[new Random().nextInt(DungeonTheme.values().length)];

        DungeonGenerator dungeonGenerator = null;
        try {
            dungeonGenerator = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Dungeon dungeon = dungeonGenerator.create(3);
        MapRegistry.INSTANCE.add(dungeon);

        if (dungeon != null) {
            decoratorMap.get(clazz).decorate(dungeon);
            itemPlacementHandler.place(dungeon);
        }

        if(!dungeonTheme.getMonsters(MonsterTier.Tier1).isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Class<? extends Actor> actorClass = dungeonTheme.getMonsters(MonsterTier.Tier1).get(new Random().nextInt(dungeonTheme.getMonsters(MonsterTier.Tier1).size()));
                Actor actor = ActorFactory.INSTANCE.create(actorClass, dungeon, Placement.RANDOM);
                actor.addMoney(new Random().nextInt(10));
                actor.setHp(actor.getMaxHp());
                actor.setMana(actor.getMaxMana());
                actor.equip(WeaponProvider.INSTANCE.getFor(Tier1.class));
            }
        }

        if(!dungeonTheme.getMonsters(MonsterTier.Tier2).isEmpty()) {
            for (int i = 0; i < 5; i++) {
                Class<? extends Actor> actorClass = dungeonTheme.getMonsters(MonsterTier.Tier2).get(new Random().nextInt(dungeonTheme.getMonsters(MonsterTier.Tier2).size()));
                Actor actor = ActorFactory.INSTANCE.create(actorClass, dungeon, Placement.RANDOM);
                actor.equip(WeaponProvider.INSTANCE.getFor(Tier2.class));
                actor.setHp(actor.getMaxHp());
                actor.setMana(actor.getMaxMana());
                actor.addMoney(new Random().nextInt(30));
            }
        }

        if(!dungeonTheme.getMonsters(MonsterTier.Tier3).isEmpty()) {
            for (int i = 0; i < 3; i++) {
                Class<? extends Actor> actorClass = dungeonTheme.getMonsters(MonsterTier.Tier3).get(new Random().nextInt(dungeonTheme.getMonsters(MonsterTier.Tier3).size()));
                Actor actor = ActorFactory.INSTANCE.create(actorClass, dungeon, Placement.RANDOM);
                actor.equip(WeaponProvider.INSTANCE.getFor(Tier3.class));
                actor.setHp(actor.getMaxHp());
                actor.setMana(actor.getMaxMana());
                actor.addMoney(new Random().nextInt(30) + 30);
            }
        }

        if(!dungeonTheme.getMonsters(MonsterTier.Tier4).isEmpty()) {
            for (int i = 0; i < 2; i++) {
                Class<? extends Actor> actorClass = dungeonTheme.getMonsters(MonsterTier.Tier4).get(new Random().nextInt(dungeonTheme.getMonsters(MonsterTier.Tier4).size()));
                Actor actor = ActorFactory.INSTANCE.create(actorClass, dungeon, Placement.RANDOM);
                actor.equip(WeaponProvider.INSTANCE.getFor(Tier4.class));
                actor.setHp(actor.getMaxHp());
                actor.setMana(actor.getMaxMana());
                actor.addMoney(new Random().nextInt(50) + 50);
            }
        }

        if(!dungeonTheme.getMonsters(MonsterTier.Boss).isEmpty()) {
            for (int i = 0; i < 1; i++) {
                Class<? extends Actor> actorClass = dungeonTheme.getMonsters(MonsterTier.Boss).get(new Random().nextInt(dungeonTheme.getMonsters(MonsterTier.Boss).size()));
                Actor actor = ActorFactory.INSTANCE.create(actorClass, dungeon, Placement.RANDOM);
                actor.equip(WeaponProvider.INSTANCE.getFor(Legendary.class));
                actor.setHp(actor.getMaxHp());
                actor.setMana(actor.getMaxMana());
                actor.addMoney(new Random().nextInt(100) + 50);
            }
        }

        return dungeon;
    }

}
