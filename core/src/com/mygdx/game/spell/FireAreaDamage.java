package com.mygdx.game.spell;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.SelectionUtils;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;

import java.util.List;

public class FireAreaDamage {

    private final Actor initiator;

    public FireAreaDamage(Actor initiator) {
        this.initiator = initiator;
    }

    public void calculate(Point center, Map2D map, int distance, int damage) {

        List<Actor> affectedActors = SelectionUtils.findAllEnemiesWithinRange(center, map, distance);
        for(int i = 0; i < affectedActors.size(); i++) {
            affectedActors.get(i).setHp(affectedActors.get(i).getHp() - damage);

            if(affectedActors.get(i).getHp() <= 0)
                affectedActors.get(i).die(initiator);
        }

    }

}
