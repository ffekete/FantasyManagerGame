package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.compound.MoveAndCutDownActivity;
import com.mygdx.game.logic.activity.single.ExecuteCutDownActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.command.Command;
import com.mygdx.game.logic.command.CutDownCommand;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.Cuttable;
import com.mygdx.game.registry.CommandRegistry;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.List;
import java.util.stream.Collectors;

public class MoveAndCutDecision implements Decision {

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(ExecuteCutDownActivity.class)) {
            return true;
        }

        if (Alignment.FRIENDLY.equals(actor.getAlignment()) && Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())) {
            // find item to chop down
            List<Command> commands = CommandRegistry.INSTANCE.getCommands().stream()
                    .filter(command -> CutDownCommand.class.isAssignableFrom(command.getClass()))
                    //.filter(command -> command.getExecutor() != actor)
                    .collect(Collectors.toList());

            if (!commands.isEmpty()) {
                Command<Cuttable> command = commands.get(0);

                MoveAndCutDownActivity moveAndCutDownActivity = new MoveAndCutDownActivity(Config.BuilderActivity.CHOP_DOWN_PRIORITY, ExecuteCutDownActivity.class);
                ExecuteCutDownActivity executeCutDownActivity = new ExecuteCutDownActivity(actor, command);
                command.setExecutor(actor);

                moveAndCutDownActivity.add(new MovementActivity(actor, (int) command.getTarget().getX(), (int) command.getTarget().getY(), 1, new PathFinder()));
                moveAndCutDownActivity.add(executeCutDownActivity);

                actor.getActivityStack().add(moveAndCutDownActivity);
                return true;

            }
            // and leave
        }
        return false;
    }
}
