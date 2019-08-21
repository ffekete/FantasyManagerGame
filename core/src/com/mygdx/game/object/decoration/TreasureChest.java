package com.mygdx.game.object.decoration;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.ContainerObject;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.Obstacle;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.furniture.Furniture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreasureChest implements WorldObject, InteractiveObject, ContainerObject, Obstacle, Furniture {

    private final float worldMapSize = 1f;

    private List<Item> items;

    private Point coordinates;
    private boolean opened = false;
    private int money;

    public TreasureChest(Point point) {
        coordinates = point;
        items = new ArrayList<>();
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
    public void setCoordinates(Point point) {
        this.coordinates = point;
    }

    @Override
    public Point getCoordinates() {
        return this.coordinates;
    }

    @Override
    public float getWorldMapSize() {
        return worldMapSize;
    }

    @Override
    public void onInteract(Actor actor) {
        for(Item item : removeItems()) {
            System.out.println(actor.getName() + " found " + item + " in a chest");
            actor.getInventory().add(item);
        }
        actor.addMoney(money);
        money = 0;
        this.opened = true;
    }

    @Override
    public boolean canInteract(Actor actor) {
        return getSize() > 0 || money > 0;
    }

    @Override
    public void finished(Actor actor) {

    }

    @Override
    public void add(Item item) {
        this.items.add(item);
    }

    @Override
    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }

    @Override
    public List<Item> removeItems() {
        List<Item> removed =  new ArrayList<>(items);
        items.clear();
        return removed;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public boolean isOpened() {
        return opened;
    }

    @Override
    public void setMoney(int amount) {
        this.money += amount;
    }

    @Override
    public int getMoney() {
        return money;
    }
}
