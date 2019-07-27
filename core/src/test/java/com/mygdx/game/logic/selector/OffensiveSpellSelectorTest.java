package com.mygdx.game.logic.selector;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.trait.Trait;
import com.mygdx.game.actor.hero.Ranger;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.SkeletonWarrior;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveThenAttackActivity;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.logic.activity.single.ExplorationActivity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.spell.Spell;
import com.mygdx.game.spell.offensive.FireBall;
import com.mygdx.game.spell.offensive.FireBolt;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OffensiveSpellSelectorTest {

    @Test
    public void testSpell_ShouldAvoidFriends() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Actor target = new SkeletonWarrior();
        Actor target2 = new SkeletonWarrior();
        Actor target3 = new Warrior();
        Actor wizard = new Wizard();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        // support cannot see target
        //visibilityMask.setValue(1,1, support);
        visibilityMask.setValue(5,1, target);

        MapRegistry.INSTANCE.add(dungeon);

        target.setCurrentMap(dungeon);
        target2.setCurrentMap(dungeon);
        target3.setCurrentMap(dungeon);
        wizard.setCurrentMap(dungeon);


        wizard.getSpellTome().clear();
        FireBolt fireBolt = new FireBolt();
        FireBall fireBall = new FireBall();
        wizard.getSpellTome().add(fireBolt);
        wizard.getSpellTome().add(fireBall);

        wizard.setMana(100);

        target.setCoordinates(Point.of(1, 1));
        target2.setCoordinates(Point.of(2, 1));
        target3.setCoordinates(Point.of(3, 1));
        wizard.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, target2);
        ActorRegistry.INSTANCE.add(dungeon, target3);
        ActorRegistry.INSTANCE.add(dungeon, wizard);

        Optional<Spell> spell = OffensiveSpellSelector.Strongest.find(wizard, target);

        assertThat(spell.isPresent(), is(true));
        assertThat(spell.get(), is(fireBolt));
    }

    @Test
    public void testSpell_ShouldSelectNothing_notEnoughMana() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Actor target = new SkeletonWarrior();
        Actor target2 = new SkeletonWarrior();
        Actor target3 = new SkeletonWarrior();
        Actor wizard = new Wizard();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        // support cannot see target
        //visibilityMask.setValue(1,1, support);
        visibilityMask.setValue(5,1, target);

        MapRegistry.INSTANCE.add(dungeon);

        target.setCurrentMap(dungeon);
        target2.setCurrentMap(dungeon);
        target3.setCurrentMap(dungeon);
        wizard.setCurrentMap(dungeon);


        wizard.getSpellTome().clear();
        FireBolt fireBolt = new FireBolt();
        FireBall fireBall = new FireBall();
        wizard.getSpellTome().add(fireBolt);
        wizard.getSpellTome().add(fireBall);

        wizard.setMana(0);

        target.setCoordinates(Point.of(1, 1));
        target2.setCoordinates(Point.of(2, 1));
        target3.setCoordinates(Point.of(3, 1));
        wizard.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, target2);
        ActorRegistry.INSTANCE.add(dungeon, target3);
        ActorRegistry.INSTANCE.add(dungeon, wizard);

        Optional<Spell> spell = OffensiveSpellSelector.Strongest.find(wizard, target);

        assertThat(spell.isPresent(), is(false));
    }

    @Test
    public void testSpell_ShouldSelectFireball_noFriends() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Actor target = new SkeletonWarrior();
        Actor target2 = new SkeletonWarrior();
        Actor target3 = new SkeletonWarrior();
        Actor wizard = new Wizard();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        // support cannot see target
        //visibilityMask.setValue(1,1, support);
        visibilityMask.setValue(5,1, target);

        MapRegistry.INSTANCE.add(dungeon);

        target.setCurrentMap(dungeon);
        target2.setCurrentMap(dungeon);
        target3.setCurrentMap(dungeon);
        wizard.setCurrentMap(dungeon);


        wizard.getSpellTome().clear();
        FireBolt fireBolt = new FireBolt();
        FireBall fireBall = new FireBall();
        wizard.getSpellTome().add(fireBolt);
        wizard.getSpellTome().add(fireBall);

        wizard.setMana(100);

        target.setCoordinates(Point.of(1, 1));
        target2.setCoordinates(Point.of(2, 1));
        target3.setCoordinates(Point.of(3, 1));
        wizard.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, target2);
        ActorRegistry.INSTANCE.add(dungeon, target3);
        ActorRegistry.INSTANCE.add(dungeon, wizard);

        Optional<Spell> spell = OffensiveSpellSelector.Strongest.find(wizard, target);

        assertThat(spell.isPresent(), is(true));
        assertThat(spell.get(), is(fireBall));
    }

    @Test
    public void testSpell_ShouldSelectFireball_casterIsLunatic() {
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        Actor target = new SkeletonWarrior();
        Actor target2 = new SkeletonWarrior();
        Actor target3 = new Warrior();
        Actor wizard = new Wizard();

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        // support cannot see target
        //visibilityMask.setValue(1,1, support);
        visibilityMask.setValue(5,1, target);

        MapRegistry.INSTANCE.add(dungeon);

        target.setCurrentMap(dungeon);
        target2.setCurrentMap(dungeon);
        target3.setCurrentMap(dungeon);
        wizard.setCurrentMap(dungeon);


        wizard.getSpellTome().clear();
        FireBolt fireBolt = new FireBolt();
        FireBall fireBall = new FireBall();
        wizard.getSpellTome().add(fireBolt);
        wizard.getSpellTome().add(fireBall);

        wizard.setMana(100);

        wizard.addTrait(Trait.Lunatic);

        target.setCoordinates(Point.of(1, 1));
        target2.setCoordinates(Point.of(2, 1));
        target3.setCoordinates(Point.of(3, 1));
        wizard.setCoordinates(Point.of(5, 1));

        ActorRegistry.INSTANCE.add(dungeon, target);
        ActorRegistry.INSTANCE.add(dungeon, target2);
        ActorRegistry.INSTANCE.add(dungeon, target3);
        ActorRegistry.INSTANCE.add(dungeon, wizard);

        Optional<Spell> spell = OffensiveSpellSelector.Strongest.find(wizard, target);

        assertThat(spell.isPresent(), is(true));
        assertThat(spell.get(), is(fireBall));
    }

}