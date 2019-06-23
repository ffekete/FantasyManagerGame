package com.mygdx.game.actor.factory;

import com.mygdx.game.actor.component.skill.Skill;

import java.util.List;

public interface SkillPriorityFactory {

    List<Skill> create();
}
