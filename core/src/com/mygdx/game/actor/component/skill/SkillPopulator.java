package com.mygdx.game.actor.component.skill;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;

import java.util.Random;
import java.util.function.Consumer;

public interface SkillPopulator {

    void populate(Actor actor);

    enum WeaponSkillPopulatorStrategy implements SkillPopulator {

        RANDOM((actor) -> {
            for(WeaponSkill s : WeaponSkill.values()) {
                actor.getWeaponSkills().put(s, new Random().nextInt(Config.Actor.MAX_STARTER_SKILL_LEVEL));
            }
        }),

        ZEROED((actor) -> {
            for(WeaponSkill s : actor.getWeaponSkills().keySet()) {
                actor.getWeaponSkills().put(s, 0);
            }
        });

        private Consumer<Actor> populate;

        WeaponSkillPopulatorStrategy(Consumer<Actor> populate) {
            this.populate = populate;
        }

        @Override
        public void populate(Actor actor) {
            this.populate.accept(actor);
        }
    }

    enum MagicSkillPopulatorStrategy implements SkillPopulator {

        RANDOM((actor) -> {
            for(MagicSkill s : MagicSkill.values()) {
                actor.getMagicSkills().put(s, new Random().nextInt(Config.Actor.MAX_STARTER_SKILL_LEVEL));
            }
        }),

        ZEROED((actor) -> {
            for(MagicSkill s : actor.getMagicSkills().keySet()) {
                actor.getMagicSkills().put(s, 0);
            }
        });

        private Consumer<Actor> populate;

        MagicSkillPopulatorStrategy(Consumer<Actor> populate) {
            this.populate = populate;
        }

        @Override
        public void populate(Actor actor) {
            this.populate.accept(actor);
        }
    }
}
