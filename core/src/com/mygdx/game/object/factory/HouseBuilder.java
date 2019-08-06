package com.mygdx.game.object.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.house.HouseBuiltDetector;
import com.mygdx.game.object.wall.Wall;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.HouseRegistry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class HouseBuilder {

    public static final HouseBuilder INSTANCE = new HouseBuilder();

    private static final HouseBuiltDetector houseBuiltDetector = HouseBuiltDetector.INSTANCE;
    private static final HouseRegistry houseRegistry = HouseRegistry.INSTANCE;
    private static final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;

    static <T extends WorldObject> void buildHouse(Class<T> clazz, Map2D map2D, T object) {

        if (Wall.class.isAssignableFrom(clazz)) {
            Set<Wall> houseWalls = houseBuiltDetector.isAHouse(map2D, object).stream().map(o -> (Wall) o).collect(Collectors.toSet());

            if (!houseWalls.isEmpty()) {
                houseWalls.remove(object); // remove this from the already known walls
                if (getHouseByWall(houseWalls).isPresent()) {
                    House house = getHouseByWall(houseWalls).get();
                    house.getWalls().addAll(houseWalls);
                    house.getWalls().add((Wall) object);
                } else { // new house, let's build it
                    Optional<Actor> owner = findActorWithoutHouse();

                    if (owner.isPresent()) {
                        House house = new House(owner.get());
                        house.setWalls(houseWalls);
                        house.getWalls().add((Wall) object);
                        houseRegistry.add(house, owner.get());
                    } else {
                        House house = new House();
                        house.setWalls(houseWalls);
                        house.getWalls().add((Wall) object);
                        houseRegistry.add(house);
                    }
                }
            }
        }
    }

    private static Optional<House> getHouseByWall(Set<Wall> worldObject) {
        return houseRegistry.getHouses()
                .stream()
                .filter(house -> house.getWalls().stream().anyMatch(worldObject::contains))
                .findAny();
    }

    private static Optional<Actor> findActorWithoutHouse() {
        return actorRegistry.getAllActors()
                .stream()
                .filter(actor1 -> {
                    return actor1.getAlignment().equals(Alignment.FRIENDLY) && !houseRegistry.getOwnedHouses().containsKey(actor1);
                }).findFirst();
    }

}
