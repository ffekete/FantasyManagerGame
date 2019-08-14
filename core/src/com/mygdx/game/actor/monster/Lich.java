package com.mygdx.game.actor.monster;

import com.mygdx.game.actor.AbstractActor;
import com.mygdx.game.actor.CasterActor;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.component.trait.Trait;
import com.mygdx.game.actor.factory.SkillFocusFactory;
import com.mygdx.game.actor.hero.Hero;
import com.mygdx.game.item.spelltome.SpellTome;
import com.mygdx.game.spell.offensive.FireBall;
import com.mygdx.game.spell.offensive.FireBolt;
import com.mygdx.game.spell.offensive.PoisonCloud;
import com.mygdx.game.spell.offensive.Slow;

import java.util.Random;

import static com.mygdx.game.faction.Alignment.ENEMY;
import static com.mygdx.game.faction.Alignment.FRIENDLY;

public class Lich extends AbstractActor implements CasterActor {

    public Lich() {
        setAlignment(ENEMY);

        SpellTome spellTome = new SpellTome();
        spellTome.add(new PoisonCloud());

        this.setSpellTome(spellTome);

        addTrait(Trait.Lunatic);

        getMagicSkills().put(MagicSkill.FireMagic, new Random().nextInt(4) + 1);
        getMagicSkills().put(MagicSkill.DarkMagic, new Random().nextInt(4) + 1);
        getWeaponSkills().put(WeaponSkill.Sword, new Random().nextInt(3) + 1);
    }

    @Override
    public boolean isHungry() {
        return false;
    }

    @Override
    public void increaseHunger(int amount) {

    }

    @Override
    public void decreaseHunger(int amount) {

    }

    @Override
    public int getHungerLevel() {
        return 0;
    }

    @Override
    public boolean isSleepy() {
        return false;
    }

    @Override
    public void increaseSleepiness(int amount) {

    }

    @Override
    public void decreaseSleepiness(int amount) {

    }

    @Override
    public int getSleepinessLevel() {
        return 0;
    }

    @Override
    public String getActorClass() {
        return "Lich priest";
    }
}
