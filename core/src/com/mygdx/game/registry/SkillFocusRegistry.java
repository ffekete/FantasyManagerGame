package com.mygdx.game.registry;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.Skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillFocusRegistry {

    public static final SkillFocusRegistry INSTANCE = new SkillFocusRegistry();

    Map<Actor, List<Skill>> skillFocusMap = new HashMap<>();

    public void add(Actor actor, List<Skill> skills) {
        skillFocusMap.put(actor, skills);
    }

    public void remove(Actor actor) {
        skillFocusMap.remove(actor);
    }

    public List<Skill> getFor(Actor actor) {
        return skillFocusMap.get(actor);
    }
}
