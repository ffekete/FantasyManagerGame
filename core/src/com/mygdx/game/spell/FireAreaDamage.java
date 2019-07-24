package com.mygdx.game.spell;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.logic.selector.AreaBasedEnemiesSelector;
import com.mygdx.game.logic.selector.SelectionUtils;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;

import java.util.List;

public class FireAreaDamage {

    public static final FireAreaDamage INSTANCE = new FireAreaDamage();

    private final AreaBasedEnemiesSelector areaBasedEnemiesSelector = new AreaBasedEnemiesSelector();

    private FireAreaDamage() {

    }

    public void calculate(Actor initiator, Point center, Map2D map, int distance, int damage) {

        List<Actor> affectedActors = areaBasedEnemiesSelector.findAllEnemiesWithinRange(center, map, distance);

        for(int i = 0; i < affectedActors.size(); i++) {
            affectedActors.get(i).setHp(affectedActors.get(i).getHp() - damage - initiator.getSkillLevel(MagicSkill.FireMagic));

            if(affectedActors.get(i).getHp() <= 0)
                affectedActors.get(i).die(initiator);
        }

    }

}
