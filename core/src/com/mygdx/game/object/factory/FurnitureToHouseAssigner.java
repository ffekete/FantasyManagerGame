package com.mygdx.game.object.factory;

import com.google.common.collect.ImmutableSet;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.furniture.Furniture;
import com.mygdx.game.object.house.House;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.HashSet;

public class FurnitureToHouseAssigner {

    public static final FurnitureToHouseAssigner INSTANCE = new FurnitureToHouseAssigner();

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    private WorldObject object;

    public void assign(Furniture furniture) {

        int x = (int) ((WorldObject) furniture).getX();
        int y = (int) ((WorldObject) furniture).getY();

        HouseRegistry.INSTANCE.getHouses().stream()
                .filter(house -> house.getTopLeft().getX() <= x && house.getTopLeft().getY() <= y && house.getBottomRight().getX() >= x && house.getBottomRight().getY() >= y)
                .findAny().ifPresent(house -> {
            if (house.getFurnitures() == null)
                house.setFurnitures(new HashSet<>());
            house.getFurnitures().add(furniture);
        });
    }

    public void assignAllTo(Map2D map, House house) {
        for (int i = house.getTopLeft().getX(); i < house.getBottomRight().getX(); i++) {
            for (int j = house.getTopLeft().getY(); j < house.getBottomRight().getY(); j++) {

                object = objectRegistry.getObjectGrid().get(map)[i][j][1];

                if (object != null) {
                    if (Furniture.class.isAssignableFrom(object.getClass())) {

                        if (house.getFurnitures() == null) {
                            house.setFurnitures(new HashSet<>());
                        }

                        house.getFurnitures().add((Furniture) object);

                    }
                }

            }
        }
    }

}
