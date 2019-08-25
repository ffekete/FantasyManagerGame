package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Prey;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.PreCalculatedMovementActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.activity.single.SleepActivity;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HuntingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return true;
        }

        if(actor.getActivityStack().getCurrent().getMainClass().equals(SimpleAttackActivity.class)) {
            return true;
        }

        List<Actor> preys = new ArrayList<>();

        for(Cluster cluster : Cluster.ofSurrounding(actor.getCurrentMap(), actor.getX(), actor.getY(), 4)) {
            preys.addAll(ActorRegistry.INSTANCE
                    .getActor(actor.getCurrentMap(), cluster)
                    .stream()
                    .filter(actor1 -> Prey.class.isAssignableFrom(actor1.getClass()))
                    .collect(Collectors.toList()));
        }

        preys.sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor o1, Actor o2) {
                return (int) MathUtil.distance(o1.getCoordinates(), o2.getCoordinates());
            }
        });

        if(!preys.isEmpty()) {
            CompoundActivity compoundActivityForActor = new MoveThenAttackActivity(Config.Activity.MOVE_THEN_ATTACK_PRIORITY, SimpleAttackActivity.class);
            compoundActivityForActor.add(new MovementActivity(actor, preys.get(0).getX(), preys.get(0).getY(), 1, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap())));
            compoundActivityForActor.add(new SimpleAttackActivity(actor, preys.get(0)));
            actor.getActivityStack().add(compoundActivityForActor);
        }

        return false;
    }
}
