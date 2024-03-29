package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Prey;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class HuntingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if(actor.getActivityStack().contains(SimpleAttackActivity.class)) {
            return true;
        }

        if(!actor.isHungry()) {
            return false;
        }

        PriorityQueue<Actor> preys = new PriorityQueue<>(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                return (int) MathUtil.distance(o1.getCoordinates(), o2.getCoordinates());
            }
        });

        for(Cluster cluster : Cluster.ofSurrounding(actor.getCurrentMap(), actor.getX(), actor.getY(), 4)) {
            preys.addAll(ActorRegistry.INSTANCE
                    .getActor(actor.getCurrentMap(), cluster)
                    .stream()
                    .filter(actor1 -> Prey.class.isAssignableFrom(actor1.getClass()))
                    .collect(Collectors.toList()));
        }

        if(!preys.isEmpty()) {
            Actor prey = preys.poll();

            CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.WildlifeActivity.HUNTING_PRIORITY, SimpleAttackActivity.class);
            compoundActivityForActor.add(new MovementActivity(actor, prey.getX(), prey.getY(), 1));
            compoundActivityForActor.add(new SimpleAttackActivity(actor, prey));
            actor.getActivityStack().add(compoundActivityForActor);
            return true;
        }

        return false;
    }
}
