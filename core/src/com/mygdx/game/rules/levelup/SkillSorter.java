package com.mygdx.game.rules.levelup;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.Skill;

import java.util.List;

public interface SkillSorter {

    public void sort(List<Skill> skillList, Actor actor);

}
