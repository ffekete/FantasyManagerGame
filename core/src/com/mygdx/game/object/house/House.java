package com.mygdx.game.object.house;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.floor.Floor;
import com.mygdx.game.object.furniture.Furniture;
import com.mygdx.game.object.wall.Wall;

import java.util.Set;

public class House {

    private Actor owner;
    private Set<Wall> walls;
    private Set<Furniture> furnitures;
    private Set<Floor> floors;

    private Point topLeft;
    private Point bottomRight;

    public House() {
    }

    public House(Actor owner) {
        this.owner = owner;
    }

    public Set<Wall> getWalls() {
        return walls;
    }

    public Set<Furniture> getFurnitures() {
        return furnitures;
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
