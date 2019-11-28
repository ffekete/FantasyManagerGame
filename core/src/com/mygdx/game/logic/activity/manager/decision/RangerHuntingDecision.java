package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Prey;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.item.weapon.RangedWeapon;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.logic.activity.single.RangedAttackActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.registry.ActorRegistry;
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

        if (actor.isAttacking()) {
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
                    .filter(actor1 -> {
                        if(Prey.class.isAssignableFrom(actor1.getClass()))
                            System.out.println("Ehune: " + MathUtil.distance(actor.getCoordinates(), actor1.getCoordinates()));
                        return Prey.class.isAssignableFrom(actor1.getClass()) && MathUtil.distance(actor.getCoordinates(), actor1.getCoordinates()) < actor.getAttackRange();
                    })
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
