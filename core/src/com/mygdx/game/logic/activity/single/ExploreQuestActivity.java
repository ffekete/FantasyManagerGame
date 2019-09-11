package com.mygdx.game.logic.activity.single;

import com.mygdx.game.actor.Actor;

public class ExploreQuestActivity extends MovementActivity {

    public ExploreQuestActivity(Actor actor, int targetX, int targetY, int range) {
        super(actor, targetX, targetY, range);
    }

    @Override
    public void clear() {
        super.clear();
    }
}
