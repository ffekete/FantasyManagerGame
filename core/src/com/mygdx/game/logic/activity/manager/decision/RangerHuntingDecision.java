package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Prey;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.item.weapon.RangedWeapon;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.HuntingActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.RangedAttackActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class RangerHuntingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if(actor.getActivityStack().contains(RangedAttackActivity.class)) {
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

            Actor prey;

            do {
                prey = preys.poll();
            } while(!preys.isEmpty() && !VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(prey.getX(),prey.getY()).contains(actor));

            if(!VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(prey.getX(),prey.getY()).contains(actor)) {
                return false;
            }

            if(actor.getRightHandItem() != null && RangedWeapon.class.isAssignableFrom(actor.getRightHandItem().getClass())) {

                MoveAndRangedAttackTargetDecision decision = new MoveAndRangedAttackTargetDecision();
                decision.setTarget(prey);

                return decision.decide(actor);
            }
        }

        return false;
    }
}
