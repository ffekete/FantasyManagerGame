package com.mygdx.game.logic.activity.compound;

import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.CompoundActivity;

public class MoveAndBuildActivity extends CompoundActivity {

    public MoveAndBuildActivity(int priority, Class<? extends Activity> mainClazz) {
        super(priority, mainClazz);
    }
}
