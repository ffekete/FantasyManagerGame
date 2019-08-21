package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.trait.Trait;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.activity.single.SupportActivity;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

import java.util.Optional;

public class FriendlySupportDecision implements Decision {

    private ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
    private MapRegistry mapRegistry = MapRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if(!actor.hasTrait(Trait.Friendly)) {
            return false;
        }

        if (actor.getActivityStack().getCurrent().getMainClass().equals(SupportActivity.class)) {
            return true;
        }

        Optional<Actor> targetActor = actorRegistry.getActors(actor.getCurrentMap())
                .stream()
                .filter(target -> !target.equals(actor)
                        && target.getAlignment().equals(actor.getAlignment())
                        && target.getCurrentMap().equals(actor.getCurrentMap())
                        && !target.getActivityStack().contains(SupportActivity.class)
                        && MathUtil.distance(actor.getCoordinates(), target.getCoordinates()) <= Config.Rules.FOLLOW_DISTANCE)
                .findFirst();

        if (targetActor.isPresent()) {
            actor.getActivityStack().clear();
            actor.getActivityStack().add(new SupportActivity(actor, targetActor.get(), 1, mapRegistry.getPathFinderFor(actor.getCurrentMap())));
            return true;
        }

        return false;

    }
}
