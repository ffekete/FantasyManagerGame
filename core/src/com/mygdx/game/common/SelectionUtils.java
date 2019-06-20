package com.mygdx.game.common;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.ArrayList;
import java.util.List;

public class SelectionUtils {

    private static Actor actorForClosestEnemy;
    private static Item item;

    public static List<Actor> findAllEnemiesWithinRange(Point middle, Map2D map, Integer maxDistance) {

        List<Actor> selectedActors = new ArrayList<>();
        List<Actor> actors = ActorRegistry.INSTANCE.getActors(map);

        int x = middle.getX();
        int y = middle.getY();

        for(int i = 0; i < actors.size(); i++) {
            actorForClosestEnemy = actors.get(i);

            int a = actorForClosestEnemy.getX();
            int b = actorForClosestEnemy.getY();

            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
            if(distance < maxDistance) {
                selectedActors.add(actorForClosestEnemy);
            }
        }
        return selectedActors;
    }

    public static Actor findClosestEnemy(Actor actor, List<Actor> actors, Integer maxDistance) {
        Actor selectedActor = null;
        int x = actor.getX();
        int y = actor.getY();
        float minDistance = Integer.MAX_VALUE;

        for(int i = 0; i < actors.size(); i++) {
            actorForClosestEnemy = actors.get(i);
            if(!actor.getAlignment().getEnemies().contains(actorForClosestEnemy.getAlignment())) {
                continue;
            }

            int a = actorForClosestEnemy.getX();
            int b = actorForClosestEnemy.getY();

            VisibilityMask mask = VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap());
            if(mask != null && !mask.getValue(a,b).contains(actor)) {
                continue;
            }

            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
            if(distance < minDistance) {
                selectedActor = actorForClosestEnemy;
                minDistance = distance;
            }
        }
        return minDistance > maxDistance*maxDistance ? null : selectedActor;
    }

    public static Item findClosestItem(Actor actor, List<Item> Items, Integer maxDistance, Class<? extends Item> clazz) {
        Item selectedItem = null;
        int x = actor.getX();
        int y = actor.getY();

        float minDistance = Float.MAX_VALUE;
        for(int i = 0; i < Items.size(); i++) {
            item = Items.get(i);
            if(!clazz.isAssignableFrom(item.getClass())) {
                continue;
            }
            int a = item.getX();
            int b = item.getY();

            if(!VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(a,b).contains(actor)) {
                continue;
            }

            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
            if(distance < minDistance) {
                selectedItem = item;
                minDistance = distance;
            }
        }
        return minDistance > maxDistance*maxDistance ? null : selectedItem;
    }
}
