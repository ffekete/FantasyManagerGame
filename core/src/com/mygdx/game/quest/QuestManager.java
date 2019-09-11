package com.mygdx.game.quest;

import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.QuestRegistry;

public class QuestManager {

    public static final QuestManager INSTANCE = new QuestManager();

    private final QuestRegistry questRegistry = QuestRegistry.INSTANCE;

    public void update() {
        for (Map2D map : MapRegistry.INSTANCE.getMaps()) {
            for (Quest quest : questRegistry.getUnfinished(map)) {

                if(quest.isFirstRun()) {
                    quest.init();
                }

                if (quest.isFinished()) {
                    quest.finish();
                    QuestRegistry.INSTANCE.finish(quest);
                } else if (quest.isCancellable()) {
                    quest.cancel();
                }
            }
        }
    }

}
