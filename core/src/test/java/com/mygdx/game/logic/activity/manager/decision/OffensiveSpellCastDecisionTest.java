package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.greenskins.Goblin;
import com.mygdx.game.effect.MovementSpeedReduction;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.spell.offensive.FireBolt;
import com.mygdx.game.spell.offensive.Slow;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OffensiveSpellCastDecisionTest {

    @Test
    public void shouldChooseToCast() {

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        wizard.setCurrentMap(dungeon);
        goblin.setCurrentMap(dungeon);

        wizard.setCoordinates(Point.of(0,0));
        goblin.setCoordinates(Point.of(3,3));

        wizard.setMana(150);

        visibilityMask.setValue(3,3, wizard);
        visibilityMask.setValue(0,0, goblin);

        ActorRegistry.INSTANCE.add(dungeon, wizard);
        ActorRegistry.INSTANCE.add(dungeon, goblin);

        OffensiveSpellCastDecision offensiveSpellCastDecision = new OffensiveSpellCastDecision();

        offensiveSpellCastDecision.decide(wizard);

        assertThat(wizard.getActivityStack().contains(OffensiveSpellCastActivity.class), is(true));
    }

    @Test
    public void shouldChooseToCast2() {

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        wizard.getSpellTome().clear();
        wizard.getSpellTome().add(new FireBolt());

        wizard.setCurrentMap(dungeon);
        goblin.setCurrentMap(dungeon);

        wizard.setCoordinates(Point.of(0,0));
        goblin.setCoordinates(Point.of(0,1));

        wizard.setMana(Config.Spell.FIREBOLT_MANA_COST);

        visibilityMask.setValue(0,1, wizard);
        visibilityMask.setValue(0,0, goblin);

        ActorRegistry.INSTANCE.add(dungeon, wizard);
        ActorRegistry.INSTANCE.add(dungeon, goblin);

        OffensiveSpellCastDecision offensiveSpellCastDecision = new OffensiveSpellCastDecision();

        offensiveSpellCastDecision.decide(wizard);

        assertThat(wizard.getActivityStack().contains(OffensiveSpellCastActivity.class), is(true));
    }

    @Test
    public void shouldNotChooseToCast_notEnoughMana() {

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        wizard.getSpellTome().clear();
        wizard.getSpellTome().add(new FireBolt());

        wizard.setCurrentMap(dungeon);
        goblin.setCurrentMap(dungeon);

        wizard.setCoordinates(Point.of(0,0));
        goblin.setCoordinates(Point.of(0,1));

        wizard.setMana(Config.Spell.FIREBOLT_MANA_COST -1);

        visibilityMask.setValue(0,1, wizard);
        visibilityMask.setValue(0,0, goblin);

        ActorRegistry.INSTANCE.add(dungeon, wizard);
        ActorRegistry.INSTANCE.add(dungeon, goblin);

        OffensiveSpellCastDecision offensiveSpellCastDecision = new OffensiveSpellCastDecision();

        offensiveSpellCastDecision.decide(wizard);

        assertThat(wizard.getActivityStack().contains(OffensiveSpellCastActivity.class), is(false));
    }

    @Test
    public void shouldNotChooseToCast_targetAlreadyHasSlow() {

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Map2D dungeon = new DummyDungeonCreator().create(5);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        wizard.getSpellTome().clear();
        wizard.getSpellTome().add(new Slow());
        wizard.getSpellTome().add(new FireBolt());

        wizard.setCurrentMap(dungeon);
        goblin.setCurrentMap(dungeon);

        wizard.setCoordinates(Point.of(0,0));
        goblin.setCoordinates(Point.of(0,1));

        wizard.setMana(Config.Spell.SLOW_MANA_COST);

        visibilityMask.setValue(0,1, wizard);
        visibilityMask.setValue(0,0, goblin);

        ActorRegistry.INSTANCE.add(dungeon, wizard);
        ActorRegistry.INSTANCE.add(dungeon, goblin);

        EffectRegistry.INSTANCE.add(new MovementSpeedReduction(-5), goblin);

        OffensiveSpellCastDecision offensiveSpellCastDecision = new OffensiveSpellCastDecision();

        offensiveSpellCastDecision.decide(wizard);

        assertThat(wizard.getActivityStack().contains(OffensiveSpellCastActivity.class), is(false));
    }

}