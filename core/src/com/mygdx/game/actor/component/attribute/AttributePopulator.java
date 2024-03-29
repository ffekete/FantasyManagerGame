package com.mygdx.game.actor.component.attribute;

import com.google.common.collect.ImmutableMap;
import com.mygdx.game.actor.Actor;

import java.util.Map;
import java.util.Random;

public interface AttributePopulator {

    void populateFor(Actor actor);

    enum ClassSpecificAttrbutePopulator implements AttributePopulator {

        Ranger {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Wisdom, new Random().nextInt(5) + 5);
            }
        },
        Warrior {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Wisdom, new Random().nextInt(5) + 5);
            }
        },
        Wizard {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Wisdom, new Random().nextInt(10) + 10);
            }
        },
        Skeleton {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(10) + 5);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 2);
                actor.setAttribute(Attributes.Reflexes, 0);
                actor.setAttribute(Attributes.Intelligence, 0);
                actor.setAttribute(Attributes.Wisdom, 0);
            }
        },

        SkeletonWarrior {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Reflexes, 5);
                actor.setAttribute(Attributes.Intelligence, 0);
                actor.setAttribute(Attributes.Wisdom, 0);
            }
        },

        Lich {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(10) + 5);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(10) + 5);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Wisdom, new Random().nextInt(10) + 10);
            }
        },

        Goblin {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(5) + 8);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(5));
                actor.setAttribute(Attributes.Wisdom, new Random().nextInt(5));
            }
        },

        Orc {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(10) + 10);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Wisdom, new Random().nextInt(5) + 5);
            }
        },

        BlackTroll {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(15) + 13);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(15) + 13);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(2) + 2);
                actor.setAttribute(Attributes.Wisdom, 1);
            }
        },

        CaveTroll {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(15) + 10);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(15) + 10);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(5) + 5);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(2) + 2);
                actor.setAttribute(Attributes.Wisdom, 1);
            }
        },

        Wolf {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(10) + 5);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(15) + 5);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(5) + 10);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(15) + 5);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(2) + 1);
                actor.setAttribute(Attributes.Wisdom, 0);
            }
        },
        Rabbit {
            @Override
            public void populateFor(Actor actor) {
                actor.setAttribute(Attributes.Strength, new Random().nextInt(1) + 1);
                actor.setAttribute(Attributes.Endurance, new Random().nextInt(1) + 1);
                actor.setAttribute(Attributes.Dexterity, new Random().nextInt(15) + 10);
                actor.setAttribute(Attributes.Reflexes, new Random().nextInt(15) + 5);
                actor.setAttribute(Attributes.Intelligence, new Random().nextInt(1) + 1);
                actor.setAttribute(Attributes.Wisdom, 0);
            }
        };

        static final Map<Class<? extends Actor>, ClassSpecificAttrbutePopulator> attributePopulatorMap = ImmutableMap.<Class<? extends Actor>, ClassSpecificAttrbutePopulator>builder()
                .put(com.mygdx.game.actor.hero.Ranger.class, Ranger)
                .put(com.mygdx.game.actor.hero.Warrior.class, Warrior)
                .put(com.mygdx.game.actor.monster.undead.Skeleton.class, Skeleton)
                .put(com.mygdx.game.actor.monster.undead.SkeletonWarrior.class, SkeletonWarrior)
                .put(com.mygdx.game.actor.monster.undead.Lich.class, Lich)
                .put(com.mygdx.game.actor.monster.greenskins.Goblin.class, Goblin)
                .put(com.mygdx.game.actor.monster.greenskins.Orc.class, Orc)
                .put(com.mygdx.game.actor.hero.Wizard.class, Wizard)
                .put(com.mygdx.game.actor.wildlife.Wolf.class, Wolf)
                .put(com.mygdx.game.actor.wildlife.Rabbit.class, Rabbit)
                .put(com.mygdx.game.actor.monster.greenskins.CaveTroll.class, CaveTroll)
                .put(com.mygdx.game.actor.monster.greenskins.BlackTroll.class, BlackTroll)
                .build();

        public static void populate(Actor actor) {
            attributePopulatorMap.get(actor.getClass()).populateFor(actor);
        }
    }

}
