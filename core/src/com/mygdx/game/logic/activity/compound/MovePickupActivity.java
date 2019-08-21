package com.mygdx.game.logic.activity.compound;

import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.CompoundActivity;

public class MovePickupActivity extends CompoundActivity {

    public MovePickupActivity(int priority, Class<? extends Activity> mainClazz) {
        super(priority, mainClazz);
    }
}
