package com.mygdx.game.quest;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.LocationMarkerAction;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveToLocationQuest implements Quest {

    private Map2D map;

    private Point coordinates;

    private List<Actor> embarkedHeroes = new ArrayList<>();

    private boolean firstRun = true;

    private Action action;

    public MoveToLocationQuest(Point coordinates, Map2D map) {
        this.coordinates = coordinates;
        this.map = map;
        action = new LocationMarkerAction(coordinates);
    }

    @Override
    public void init() {

        action.setCoordinates(coordinates);
        ActionRegistry.INSTANCE.add(map, action);
        firstRun = false;
    }

    @Override
    public List<Actor> getEmbarkedHeroes() {
        return embarkedHeroes;
    }

    @Override
    public void embark(Actor actor) {
        this.embarkedHeroes.add(actor);
    }

    @Override
    public boolean isFinished() {
        return embarkedHeroes.stream().anyMatch(hero -> hero.getCoordinates().equals(coordinates));
    }

    @Override
    public void finish() {
        List<Actor> rewardedActors = embarkedHeroes.stream()
                .filter(hero -> hero.getCoordinates().equals(coordinates))
                .collect(Collectors.toList());

        for(Actor a : rewardedActors) {
            a.addExperiencePoints(10);
        }

        ActionRegistry.INSTANCE.remove(map, action);
    }

    @Override
    public boolean isCancellable() {
        return false;
    }

    @Override
    public void cancel() {

    }

    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public int getRecommendedLevel() {
        return 0;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public Map2D getMap() {
        return map;
    }
}
