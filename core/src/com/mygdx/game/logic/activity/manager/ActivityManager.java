package com.mygdx.game.logic.activity.manager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.*;
import com.mygdx.game.actor.wildlife.Rabbit;
import com.mygdx.game.actor.wildlife.Wolf;
import com.mygdx.game.actor.worker.Builder;
import com.mygdx.game.actor.worker.Shopkeeper;
import com.mygdx.game.actor.worker.Smith;
import com.mygdx.game.logic.activity.manager.decision.*;
import com.mygdx.game.logic.activity.manager.decision.shopkeeper.ShopKeepingDecision;

import java.util.List;
import java.util.Map;

public class ActivityManager {

    private Map<Class<? extends Actor>, List<Decision>> decisionTable;

    public ActivityManager() {

        decisionTable = ImmutableMap.<Class<? extends Actor>, List<Decision>>builder()

                .put(Builder.class, ImmutableList.of(
                        new SleepingDecision(),
                        new MoveAndCutDecision(),
                        new MoveAndDropDecision(),
                        new MoveAndBuildDecision()))

                .put(Smith.class, ImmutableList.of(
                        new SleepingDecision(),
                        new SmithingDecision()))

                .put(Shopkeeper.class, ImmutableList.of(
                        new ShopKeepingDecision(),
                        new SleepingDecision()))

                .put(Ranger.class, ImmutableList.of(
                        new ConsumeHealingpotionDecision(),
                        new ConsumeManapotionDecision(),
                        new ConsumeAntiVenomPotionDecision(),
                        new EquipDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new OpenChestDecision(),
                        new MovePickupDecision(),
                        new SleepingDecision(),
                        new HuntingDecision(),
                        new EatingDecision(),
                        new ShootingTrainingDecision(),
                        new LeaveDungeonDecision(),
                        new DungeonVisitingDecision(),
                        new FriendlySupportDecision(),
                        new SupportDecision(),
                        new ExplorationDecision(),
                        new MovePickupEatDecision(),
                        new WanderingDecision())
                )

                .put(Warrior.class, ImmutableList.of(
                        new ConsumeHealingpotionDecision(),
                        new ConsumeManapotionDecision(),
                        new ConsumeAntiVenomPotionDecision(),
                        new EquipDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new OpenChestDecision(),
                        new MovePickupDecision(),
                        new SleepingDecision(),
                        new EatingDecision(),
                        new PhysicalTrainingDecision(),
                        new LeaveDungeonDecision(),
                        new DungeonVisitingDecision(),
                        new FriendlySupportDecision(),
                        new ExplorationDecision(),
                        new MovePickupEatDecision(),
                        new WanderingDecision())
                )

                .put(Wizard.class, ImmutableList.of(
                        new ConsumeHealingpotionDecision(),
                        new ConsumeManapotionDecision(),
                        new ConsumeAntiVenomPotionDecision(),
                        new EquipDecision(),
                        new OffensiveSpellCastDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new OpenChestDecision(),
                        new MovePickupDecision(),
                        new SleepingDecision(),
                        new EatingDecision(),
                        new MentalTrainingDecision(),
                        new LeaveDungeonDecision(),
                        new DungeonVisitingDecision(),
                        new FriendlySupportDecision(),
                        new ExplorationDecision(),
                        new MovePickupEatDecision(),
                        new WanderingDecision())
                )

                .put(SkeletonWarrior.class, ImmutableList.of(
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )

                .put(Skeleton.class, ImmutableList.of(
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )

                .put(Lich.class, ImmutableList.of(
                        new ConsumeManapotionDecision(),
                        new OffensiveSpellCastDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )

                .put(Orc.class, ImmutableList.of(
                        new ConsumeAntiVenomPotionDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )

                .put(Goblin.class, ImmutableList.of(
                        new ConsumeAntiVenomPotionDecision(),
                        new MoveAndRangedAttackDecision(),
                        new MoveAndAttackDecision(),
                        new WanderingDecision())
                )

                .put(Wolf.class, ImmutableList.of(
                        new PredatorSleepingDecision(),
                        new HuntingDecision(),
                        new WanderingDecision())
                )

                .put(Rabbit.class, ImmutableList.of(
                        new WildLifeSleepingDecision(),
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
