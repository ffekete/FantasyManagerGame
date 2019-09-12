package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.DungeonLeavingActivity;
import com.mygdx.game.logic.activity.single.InteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.interactive.Ladder;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.List;

public class LeaveDungeonDecision implements Decision {

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if(actor.getActivityStack().contains(DungeonLeavingActivity.class)) {
            return true;
        }

        if(Alignment.FRIENDLY.equals(actor.getAlignment()) && !Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType()) && actor.getCurrentMap().areAllLevelsExplored() && !actor.getActivityStack().contains(MoveAndInteractActivity.class)) {
            // find dungeon entrance
            List<WorldObject> entrances = objectRegistry.getObject(actor.getCurrentMap(), Ladder.class).get();
            for (WorldObject worldObject : entrances) {
                if (!((Ladder)worldObject).getTo().equals(actor.getCurrentMap())) {
                    MoveAndInteractActivity moveAndInteractActivity = new MoveAndInteractActivity(Config.Activity.INTERACT_PRIORITY, DungeonLeavingActivity.class);

                    moveAndInteractActivity.add(new MovementActivity(actor, (int)worldObject.getX(), (int)worldObject.getY(), 1));
                    moveAndInteractActivity.add(new InteractActivity(actor, (InteractiveObject) worldObject));

                    actor.getActivityStack().add(moveAndInteractActivity);
                    return true;
                }
            }
            // and leave
        }
        return false;
    }
}
