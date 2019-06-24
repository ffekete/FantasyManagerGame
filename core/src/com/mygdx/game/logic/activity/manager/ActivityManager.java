package com.mygdx.game.logic.activity.manager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.Goblin;
import com.mygdx.game.actor.monster.Orc;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.actor.monster.SkeletonWarrior;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.manager.decision.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ActivityManager {

    private Map<Class<? extends Actor>, List<Decision>> decisionTable;

    public ActivityManager() {

        decisionTable = ImmutableMap.<Class<? extends Actor>, List<Decision>>builder()

                .put(Ranger.class, ImmutableList.of(
                        new ConsumeHealingpotionDecision(),
                        new EquipDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new MovePickupDecision(),
                        new EatingDecision(),
                        new LeaveDungeonDecision(),
                        new DungeonVisitingDecision(),
                        new ExplorationDecision(),
                        new MovePickupEatDecision(),
                        new WanderingDecision())
                )

                .put(Warrior.class, ImmutableList.of(
                        new ConsumeHealingpotionDecision(),
                        new EquipDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new MovePickupDecision(),
                        new EatingDecision(),
                        new LeaveDungeonDecision(),
                        new DungeonVisitingDecision(),
                        new ExplorationDecision(),
                        new MovePickupEatDecision(),
                        new WanderingDecision())
                )

                .put(Wizard.class, ImmutableList.of(
                        new ConsumeHealingpotionDecision(),
                        new EquipDecision(),
                        new OffensiveSpellCastDecision(),
                        new MoveAndAttackDecision(),
                        new MovePickupDecision(),
                        new EatingDecision(),
                        new LeaveDungeonDecision(),
                        new DungeonVisitingDecision(),
                        new ExplorationDecision(),
                        new MovePickupEatDecision(),
                        new WanderingDecision())
                )

                .put(SkeletonWarrior.class, ImmutableList.of(
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )

                .put(Skeleton.class, ImmutableList.of(
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )


                .put(Orc.class, ImmutableList.of(
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )


                .put(Goblin.class, ImmutableList.of(
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )

                .build();
    }

    public void manage(Actor actor) {
        for (int i = 0; i < decisionTable.get(actor.getClass()).size(); i++) {
            if (decisionTable.get(actor.getClass()).get(i).decide(actor)) {
                break;
            }
        }
    }
}
