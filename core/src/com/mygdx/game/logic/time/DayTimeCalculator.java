package com.mygdx.game.logic.time;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.Config;

public class DayTimeCalculator {

    public static final DayTimeCalculator INSTANCE = new DayTimeCalculator();

    private long actualTime = 0L;

    public void update() {
        actualTime++;
    }

    public void setActualTime(long l) {
        this.actualTime = l;
    }

    public long getHour() {
        return actualTime / Config.Time.TICK_PER_HOUR % Config.Time.HOUR_PER_DAY;
    }

    public long getDay() {
        return actualTime / Config.Time.TICK_PER_HOUR / Config.Time.HOUR_PER_DAY;
    }

    public boolean isItNight() {
        if(getHour() >= 22 || getHour() <= 5) {
            return true;
        }
        return false;
    }

    public boolean isDawn() {
        if(getHour() >= 20 && getHour() < 22) {
            return true;
        }
        return false;
    }

    public boolean isDusk() {
        if(getHour() > 5 && getHour() <= 7) {
            return true;
        }
        return false;
    }

}
