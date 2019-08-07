package com.mygdx.game.object.factory;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
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
    private static final FurnitureToHouseAssigner furnitureToHouseAssigner = FurnitureToHouseAssigner.INSTANCE;
    private static final FloorToHouseAssigner floorToHouseAssigner = FloorToHouseAssigner.INSTANCE;

    static <T extends WorldObject> void buildHouse(Class<T> clazz, Map2D map2D, T object) {

        if (Wall.class.isAssignableFrom(clazz)) {
            Set<Wall> houseWalls = houseBuiltDetector.isAHouse(map2D, object).stream().map(o -> (Wall) o).collect(Collectors.toSet());

            if (!houseWalls.isEmpty()) {
                houseWalls.remove(object); // remove this from the already known walls
                if (getHouseByWall(houseWalls).isPresent()) {
                    House house = getHouseByWall(houseWalls).get();
                    house.getWalls().addAll(houseWalls);
                    house.getWalls().add((Wall) object);

                    updateCoordinates(house);
                    furnitureToHouseAssigner.assignAllTo(map2D, house);
                    floorToHouseAssigner.assignAllTo(map2D, house);
                    System.out.println("Existing house updated");

                } else { // new house, let's build it
                    Optional<Actor> owner = findActorWithoutHouse();

                    System.out.println("House built!");

                    if (owner.isPresent()) {
                        House house = new House(owner.get());
                        house.setWalls(houseWalls);
                        house.getWalls().add((Wall) object);
                        houseRegistry.add(house, owner.get());
                        updateCoordinates(house);
                        furnitureToHouseAssigner.assignAllTo(map2D, house);
                        floorToHouseAssigner.assignAllTo(map2D, house);
                    } else {
                        House house = new House();
                        house.setWalls(houseWalls);
                        house.getWalls().add((Wall) object);
                        houseRegistry.add(house);
                        updateCoordinates(house);
                        furnitureToHouseAssigner.assignAllTo(map2D, house);
                        floorToHouseAssigner.assignAllTo(map2D, house);
                    }
                }
            }
        }
    }

    private static void updateCoordinates(House house) {
        int leftMost = Integer.MAX_VALUE, rightMost = -1, top = Integer.MAX_VALUE, bottom = -1;

        for (Wall wall : house.getWalls()) {

            if (leftMost > ((WorldObject) wall).getX())
                leftMost = (int) ((WorldObject) wall).getX();

            if (rightMost < ((WorldObject) wall).getX())
                rightMost = (int) ((WorldObject) wall).getX();

            if (bottom < ((WorldObject) wall).getY())
                bottom = (int) ((WorldObject) wall).getY();

            if (top > ((WorldObject) wall).getY())
                top = (int) ((WorldObject) wall).getY();
        }

        house.setTopLeft(Point.of(leftMost, top));
        house.setBottomRight(Point.of(rightMost, bottom));
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
