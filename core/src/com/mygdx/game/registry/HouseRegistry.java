package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.object.house.House;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HouseRegistry {

    public static final HouseRegistry INSTANCE = new HouseRegistry();

    private Set<House> houses = new HashSet<>();
    private Map<Actor, House> ownedHouses = new HashMap<>();
    private Set<House> emptyHouses = new HashSet<>();

    public Set<House> getHouses() {
        return houses;
    }

    public Set<House> getEmptyHouses() {
        return emptyHouses;
    }

    public Map<Actor, House> getOwnedHouses() {
        return ownedHouses;
    }

    public void add(House house, Actor actor) {
        this.houses.add(house);
        this.ownedHouses.put(actor, house);
    }

    public void add(House house) {
        this.houses.add(house);
        this.emptyHouses.add(house);
    }
}
