package com.mygdx.game.logic.selector;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.List;

public class ClosestEnemySelector {

    public Actor find(Actor actor, List<Actor> actors, Integer maxDistance) {
        Actor selectedActor = null;
        int x = actor.getX();
        int y = actor.getY();
        float minDistance = Integer.MAX_VALUE;

        Actor actorForClosestEnemy;

        for (int i = 0; i < actors.size(); i++) {
            actorForClosestEnemy = actors.get(i);
            if (!actor.getAlignment().getEnemies().contains(actorForClosestEnemy.getAlignment())) {
                continue;
            }

            int a = actorForClosestEnemy.getX();
            int b = actorForClosestEnemy.getY();

            VisibilityMask mask = VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap());
            if (mask != null && !mask.getValue(a, b).contains(actor)) {
                continue;
            }

            float distance = Math.abs(x - a) * Math.abs(x - a) + Math.abs(y - b) * Math.abs(y - b);
            if (distance < minDistance) {
                selectedActor = actorForClosestEnemy;
                minDistance = distance;
            }
        }
        return minDistance > maxDistance * maxDistance ? null : selectedActor;
    }

}
