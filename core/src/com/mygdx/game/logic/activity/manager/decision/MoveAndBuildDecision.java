package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.compound.MoveAndBuildActivity;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.single.BuildActivity;
import com.mygdx.game.logic.activity.single.InteractActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.InteractiveObject;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.interactive.Ladder;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.List;

public class MoveAndBuildDecision implements Decision {

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(MoveAndBuildActivity.class)) {
            return true;
        }

        if (Alignment.FRIENDLY.equals(actor.getAlignment()) && Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())) {
            // find block to build
            List<BuildingBlock> blocks = objectRegistry.getBuildingBlock();
            if (!blocks.isEmpty()) {
                System.out.println("Found building block!");
                WorldObject worldObject = blocks.get(0);

                MoveAndBuildActivity moveAndBuildActivity = new MoveAndBuildActivity(Config.BuilderActivity.BUILD_PRIORITY, BuildActivity.class);

                moveAndBuildActivity.add(new MovementActivity(actor, (int) worldObject.getX(), (int) worldObject.getY(), 1, new PathFinder()));
                moveAndBuildActivity.add(new BuildActivity(actor, (BuildingBlock) worldObject));

                actor.getActivityStack().add(moveAndBuildActivity);
                return true;

            }
            // and leave
        }
        return false;
    }
}
