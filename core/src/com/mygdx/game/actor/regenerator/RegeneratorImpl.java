package com.mygdx.game.actor.regenerator;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;

public enum RegeneratorImpl implements Regenerator {

    MANA {

        private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
        private final MapRegistry mapRegistry = MapRegistry.INSTANCE;

        private int phase = 0;

        @Override
        public void regenerateAll() {

            phase = (phase + 1) % Config.Rules.MANA_REGENERATION_PERIOD;
            if (phase > 0)
                return;


            for (Actor a : actorRegistry.getAllActors()) {
                int mana = a.getMana();
                a.setMana(Math.min(mana + 1, a.getMaxMana()));
            }

        }
    },

    HP {

        private final ActorRegistry actorRegistry = ActorRegistry.INSTANCE;
        private final MapRegistry mapRegistry = MapRegistry.INSTANCE;

        private int phase = 0;

        @Override
        public void regenerateAll() {

            phase = (phase + 1) % Config.Rules.HP_REGENERATION_PERIOD;
            if (phase > 0)
                return;

            for (Actor a : actorRegistry.getAllActors()) {
                int hp = a.getHp();
                a.setHp(Math.min(hp + 1, a.getMaxHp()));
            }
        }
    }
}
