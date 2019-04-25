package com.mygdx.game.logic.time;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Config;

public class DayTimeCalculator {

    public static final DayTimeCalculator INSTANCE = new DayTimeCalculator();

    private long actualTime = 0L;

    public void update() {
        actualTime++;
    }

    public long getHour() {
        return actualTime / Config.Time.TICK_PER_HOUR % Config.Time.HOUR_PER_DAY;
    }

    public long getDay() {
        return actualTime / Config.Time.TICK_PER_HOUR / Config.Time.HOUR_PER_DAY;
    }

}
