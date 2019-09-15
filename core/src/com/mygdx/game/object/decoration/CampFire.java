package com.mygdx.game.object.decoration;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.*;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.object.light.LightSourceType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class CampFire implements WorldObject, LightSource, AnimatedObject, NoViewBlockinObstacle, Sittable {

    private Point coordinates;
    private Map2D map;
    private Color color = Color.valueOf("AABB22FF");
    private int phase = new Random().nextInt(3);
    private long maxFreeSpace = 0;

    private float flickering = 0.0f;
    private int counter = 0;

    private Map<Point, Boolean> spaces;

    public CampFire(Point coordinates, Map2D map) {
        this.coordinates = coordinates;
        this.map = map;
        spaces = new HashMap<>();
    }

    private Boolean getAvailability(int x, int y) {
        return !(x < 0 || y < 0 || x >= map.getWidth() || y >= map.getHeight() || map.isObstacle(x,y));
    }

    @Override
    public float getX() {
        return coordinates.getX();
    }

    @Override
    public float getY() {
        return coordinates.getY();
    }

    @Override
    public float getArea() {
        return 5f + flickering;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public LightSourceType getType() {
        return LightSourceType.Ambient;
    }

    @Override
    public void update() {
        counter += 1;

        if (counter >= 5) {
            flickering = new Random().nextFloat() / 5;
            counter = 0;
        }
    }

    @Override
    public void setCoordinates(Point point) {
        this.coordinates = point;
        spaces.put(Point.of(coordinates.getX() - 1, coordinates.getY()), getAvailability(coordinates.getX() - 1, coordinates.getY()));
        spaces.put(Point.of(coordinates.getX() + 1, coordinates.getY()), getAvailability(coordinates.getX() + 1, coordinates.getY()));
        spaces.put(Point.of(coordinates.getX(), coordinates.getY() - 1), getAvailability(coordinates.getX(), coordinates.getY() - 1));
        spaces.put(Point.of(coordinates.getX(), coordinates.getY() + 1), getAvailability(coordinates.getX(), coordinates.getY() + 1));

        maxFreeSpace = spaces.values().stream().filter(available -> available == true).count();
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return 1F;
    }

    @Override
    public Point getNextFreeSpace() {
        return spaces.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    @Override
    public boolean hasFreeSpace() {
        return spaces.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .findFirst()
                .map(Map.Entry::getKey)
                .isPresent();
    }

    @Override
    public void bookSpace(Point point) {
        spaces.put(point, false);
    }

    @Override
    public void freeUp(Point point) {
        spaces.put(point, true);
    }

    @Override
    public boolean allFree() {
        return spaces.values().stream().filter(a -> a).count() == maxFreeSpace;
    }

    @Override
    public int getPhase() {
        return phase;
    }

    @Override
    public float getSpeed() {
        return 0.1f;
    }
}
