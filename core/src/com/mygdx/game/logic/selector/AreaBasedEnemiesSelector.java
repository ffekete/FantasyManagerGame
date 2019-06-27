package com.mygdx.game.logic.selector;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;

import java.util.ArrayList;
import java.util.List;

public class AreaBasedEnemiesSelector {

    public List<Actor> findAllEnemiesWithinRange(Point middle, Map2D map, Integer maxDistance) {

        Actor actorForClosestEnemy;

        List<Actor> selectedActors = new ArrayList<>();
        List<Actor> actors = ActorRegistry.INSTANCE.getActors(map);

        int x = middle.getX();
        int y = middle.getY();

        for(int i = 0; i < actors.size(); i++) {
            actorForClosestEnemy = actors.get(i);

            int a = actorForClosestEnemy.getX();
            int b = actorForClosestEnemy.getY();

            double distance = Math.sqrt(Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b));
            if(distance <= maxDistance) {
                selectedActors.add(actorForClosestEnemy);
            }
        }
        return selectedActors;
    }

}
