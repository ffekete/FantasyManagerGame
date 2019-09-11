package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.activity.compound.MoveAndCutDownActivity;
import com.mygdx.game.logic.activity.single.ExecuteCutDownActivity;
import com.mygdx.game.logic.activity.single.ExploreQuestActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.command.Command;
import com.mygdx.game.logic.command.CutDownCommand;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.Cuttable;
import com.mygdx.game.quest.MoveToLocationQuest;
import com.mygdx.game.quest.Quest;
import com.mygdx.game.registry.CommandRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.registry.QuestRegistry;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmbarkOnExplorationQuestDecision implements Decision {

    private final ObjectRegistry objectRegistry = ObjectRegistry.INSTANCE;

    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return false;
        }

        if (actor.getActivityStack().contains(ExploreQuestActivity.class)) {
            return true;
        }

        if (Alignment.FRIENDLY.equals(actor.getAlignment())) {
            // find item to chop down
            Optional<Quest> quests = QuestRegistry.INSTANCE.getUnfinished(actor.getCurrentMap())
                    .stream()
                    .filter(quest -> MoveToLocationQuest.class.isAssignableFrom(quest.getClass()))
                    .findFirst();

            if (quests.isPresent()) {
                MoveToLocationQuest quest = (MoveToLocationQuest) quests.get();

                quest.embark(actor);

                ExploreQuestActivity exploreQuestActivity = new ExploreQuestActivity(actor, quest.getCoordinates().getX(), quest.getCoordinates().getY(), 0);

                actor.getActivityStack().add(exploreQuestActivity);
                return true;

            }
            // and leave
        }
        return false;
    }
}
