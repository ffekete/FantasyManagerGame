package com.mygdx.game.object.factory;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.object.house.House;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.HashSet;

public class FloorToHouseAssigner {

    public static final FloorToHouseAssigner INSTANCE = new FloorToHouseAssigner();

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    private WorldObject object;

    public void assign(Floor floor) {

        int x = (int) ((WorldObject) floor).getX();
        int y = (int) ((WorldObject) floor).getY();

        HouseRegistry.INSTANCE.getHouses().stream()
                .filter(house -> house.getTopLeft().getX() <= x && house.getTopLeft().getY() <= y && house.getBottomRight().getX() >= x && house.getBottomRight().getY() >= y)
                .findAny().ifPresent(house -> {
            if (house.getFloors() == null)
                house.setFloors(new HashSet<>());
            house.getFloors().add(floor);
        });
    }

    public void assignAllTo(Map2D map, House house) {
        for (int i = house.getTopLeft().getX(); i < house.getBottomRight().getX(); i++) {
            for (int j = house.getTopLeft().getY(); j < house.getBottomRight().getY(); j++) {

                object = objectRegistry.getObjectGrid().get(map)[i][j][1];

                if (object != null) {
                    if (Floor.class.isAssignableFrom(object.getClass())) {

                        if (house.getFloors() == null) {
                            house.setFloors(new HashSet<>());
                        }

                        house.getFloors().add((Floor) object);

                    }
                }

            }
        }
    }

}
