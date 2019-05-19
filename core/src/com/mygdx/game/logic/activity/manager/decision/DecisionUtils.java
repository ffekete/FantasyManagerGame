package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.List;
import java.util.Set;

class DecisionUtils {

    private static Actor actorForClosestEnemy;
    private static Item item;

    static Actor findClosestEnemy(Actor actor, List<Actor> actors, Integer maxDistance) {
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

    static Item findClosestItem(Actor actor, List<Item> items, Integer maxDistance, Class<? extends Item> clazz) {
        Item selectedItem = null;
        int x = actor.getX();
        int y = actor.getY();

        float minDistance = Float.MAX_VALUE;
        for(int i = 0; i < items.size(); i++) {
            item = items.get(i);
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
