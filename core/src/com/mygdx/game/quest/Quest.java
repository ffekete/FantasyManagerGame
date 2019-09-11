package com.mygdx.game.quest;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;

import java.util.List;

public interface Quest {

    void init();

    List<Actor> getEmbarkedHeroes();

    void embark(Actor actor);

    boolean isFinished();

    void finish();

    boolean isCancellable();

    void cancel();

    boolean isFirstRun();

    int getRecommendedLevel();

    Map2D getMap();
}
