package com.mygdx.game.actor.regenerator;

import com.mygdx.game.Config;
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
            if(phase > 0)
                return;

            for(int i = 0; i < mapRegistry.getMaps().size(); i++) {
                for(int j = 0; j < actorRegistry.getActors(mapRegistry.getMaps().get(i)).size(); j++) {
                    int mana = actorRegistry.getActors(mapRegistry.getMaps().get(i)).get(j).getMana();
                    actorRegistry.getActors(mapRegistry.getMaps().get(i)).get(j)
                            .setMana(Math.min(mana + 1, actorRegistry.getActors(mapRegistry.getMaps().get(i)).get(j).getMaxMana()));
                }
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
            if(phase > 0)
                return;

            for(int i = 0; i < mapRegistry.getMaps().size(); i++) {
                for(int j = 0; j < actorRegistry.getActors(mapRegistry.getMaps().get(i)).size(); j++) {
                    int hp = actorRegistry.getActors(mapRegistry.getMaps().get(i)).get(j).getHp();
                    actorRegistry.getActors(mapRegistry.getMaps().get(i)).get(j)
                            .setHp(Math.min(hp + 1, actorRegistry.getActors(mapRegistry.getMaps().get(i)).get(j).getMaxHp()));
                }
            }
        }
    }
}
