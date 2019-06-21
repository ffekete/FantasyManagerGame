package com.mygdx.game.spell;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.effect.FireDamage;
import com.mygdx.game.effect.MovementSpeedReduction;
import com.mygdx.game.spell.offensive.FireBall;
import com.mygdx.game.spell.offensive.FireBolt;
import com.mygdx.game.spell.offensive.Slow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpellEffectMapper {

    public static final SpellEffectMapper INSTANCE = new SpellEffectMapper();

    private final Map<Class<? extends Spell>, List<Class<? extends Effect>>> spellEffectsMap = ImmutableMap.<Class<? extends Spell>, List<Class<? extends Effect>>>builder()
            .put(Slow.class, ImmutableList.of(
                    MovementSpeedReduction.class
            ))
            .put(FireBall.class, ImmutableList.of(
                    FireDamage.class
            ))
            .put(FireBolt.class, ImmutableList.of(
                    FireDamage.class
            ))
            .build();

    public List<Class<? extends Effect>> getFor(Class<? extends Spell> clazz) {
        return spellEffectsMap.getOrDefault(clazz, new ArrayList<>());
    }

}
