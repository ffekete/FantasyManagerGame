package com.mygdx.game.logic;

import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.worker.Shopkeeper;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.furniture.ShopkeepersDesk;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.interactive.Anvil;
import com.mygdx.game.object.interactive.BookCase;
import com.mygdx.game.object.interactive.PracticeFigure;
import com.mygdx.game.object.interactive.ShootingTarget;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;

public class HousePopulator {

    public static final HousePopulator INSTANCE = new HousePopulator();

    private final HouseRegistry houseRegistry = HouseRegistry.INSTANCE;

    public void update() {
        if (houseRegistry.getEmptyHouses().isEmpty()) {
            return;
        }

        House house = houseRegistry.getEmptyHouses().stream().findFirst().get();

        Map2D map = MapRegistry.INSTANCE.getMaps().stream()
                .filter(map2D -> map2D.getMapType().equals(Map2D.MapType.WORLD_MAP))
                .findFirst()
                .get();

        if(!house.getFurnitureOfType(Anvil.class).isEmpty()) {
            Anvil anvil = house.getFurnitureOfType(Anvil.class).get(0);
            ActorFactory.INSTANCE.create(Smith.class, map, Placement.FIXED.X((int)anvil.getX()).Y((int)anvil.getY()));
        }
        else if(!house.getFurnitureOfType(ShopkeepersDesk.class).isEmpty()) {
            ShopkeepersDesk shopkeepersDesk = house.getFurnitureOfType(ShopkeepersDesk.class).get(0);
            ActorFactory.INSTANCE.create(Shopkeeper.class, map, Placement.FIXED.X((int)shopkeepersDesk.getX()).Y((int)shopkeepersDesk.getY()));
        }
        else if(!house.getFurnitureOfType(PracticeFigure.class).isEmpty()) {
            PracticeFigure practiceFigure = house.getFurnitureOfType(PracticeFigure.class).get(0);
            ActorFactory.INSTANCE.create(Warrior.class, map, Placement.FIXED.X((int)practiceFigure.getX()).Y((int)practiceFigure.getY()));
        }
        else if(!house.getFurnitureOfType(ShootingTarget.class).isEmpty()) {
            ShootingTarget shootingTarget = house.getFurnitureOfType(ShootingTarget.class).get(0);
            ActorFactory.INSTANCE.create(Ranger.class, map, Placement.FIXED.X((int)shootingTarget.getX()).Y((int)shootingTarget.getY()));
        }
        else if(!house.getFurnitureOfType(BookCase.class).isEmpty()) {
            BookCase bookCase = house.getFurnitureOfType(BookCase.class).get(0);
            ActorFactory.INSTANCE.create(Wizard.class, map, Placement.FIXED.X((int)bookCase.getX()).Y((int)bookCase.getY()));
        }
//        else {
//            WoodenBed woodenBed = house.getFurnitureOfType(WoodenBed.class).get(0);
//            ActorFactory.INSTANCE.create(Smith.class, map, Placement.FIXED.X((int)woodenBed.getX()).Y((int)woodenBed.getY()));
//        }


    }

}
