package com.mygdx.game.object.house;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.object.furniture.Furniture;
import com.mygdx.game.object.wall.Wall;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class House {

    private Actor owner;
    private Set<Wall> walls;
    private Set<Furniture> furnitures;
    private Set<Floor> floors;

    private Point topLeft;
    private Point bottomRight;

    public House() {
        walls = new HashSet<>();
        furnitures = new HashSet<>();
        floors = new HashSet<>();
    }

    public House(Actor owner) {
        this.owner = owner;
        walls = new HashSet<>();
        furnitures = new HashSet<>();
        floors = new HashSet<>();
    }

    public Set<Wall> getWalls() {
        return walls;
    }

    public Set<Furniture> getFurnitures() {
        return furnitures;
    }

    public <T extends Furniture> List<T> getFurnitureOfType(Class<T> clazz) {
        List<T> furnitures1 = furnitures.stream().filter(f -> clazz.isAssignableFrom(f.getClass())).map(o -> (T)o)
        .collect(Collectors.toList());

        if(furnitures1 == null) {
            return Collections.emptyList();
        }
        else return furnitures1;
    }

    public Set<Floor> getFloors() {
        return floors;
    }

    public void setWalls(Set<Wall> walls) {
        this.walls = walls;
    }

    public void setFurnitures(Set<Furniture> furnitures) {
        this.furnitures = furnitures;
    }

    public void setFloors(Set<Floor> floors) {
        this.floors = floors;
    }

    public Actor getOwner() {
        return owner;
    }

    public void setOwner(Actor owner) {
        this.owner = owner;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }
}
