package com.mygdx.game.spell;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.Skeleton;
import com.mygdx.game.actor.monster.SkeletonWarrior;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.MapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;

public class FireAreaDamageTest {

    @Test
    public void testCalculate() throws Exception {

        Dungeon dungeon = (Dungeon) new DummyDungeonCreator().create(3);
        Dungeon dungeon2 = (Dungeon) new DummyDungeonCreator().create(3);

        Actor initiator = new Wizard();
        Actor target1 = new Skeleton();
        Actor target2 = new SkeletonWarrior();
        Actor target3 = new SkeletonWarrior();
        Actor noTarget = new Warrior();
        Actor notEvenInDungeon = new Wizard();

        initiator.setCurrentMap(dungeon);
        target1.setCurrentMap(dungeon);
        target2.setCurrentMap(dungeon);
        target3.setCurrentMap(dungeon);
        noTarget.setCurrentMap(dungeon);
        notEvenInDungeon.setCurrentMap(dungeon2);

        initiator.setCoordinates(Point.of(1,1));
        target1.setCoordinates(Point.of(10,10));
        target2.setCoordinates(Point.of(13,13));
        target3.setCoordinates(Point.of(7,7));
        noTarget.setCoordinates(Point.of(16,15));
        notEvenInDungeon.setCoordinates(Point.of(10, 10));

        ActorRegistry.INSTANCE.add(dungeon, initiator);
        ActorRegistry.INSTANCE.add(dungeon, target1);
        ActorRegistry.INSTANCE.add(dungeon, target2);
        ActorRegistry.INSTANCE.add(dungeon, target3);
        ActorRegistry.INSTANCE.add(dungeon, noTarget);
        ActorRegistry.INSTANCE.add(dungeon2, notEvenInDungeon);

        initiator.getMagicSkills().put(MagicSkill.FireMagic, 4);

        target1.setHp(15);
        target2.setHp(9);
        target3.setHp(10);
        noTarget.setHp(15);
        notEvenInDungeon.setHp(1);

        FireAreaDamage.INSTANCE.calculate(initiator, Point.of(10, 10), dungeon, 5, 10);

        assertThat(target1.getHp(), is(1));
        assertThat(target2.getHp(), is(-5));
        assertThat(target3.getHp(), is(-4));
        assertThat(noTarget.getHp(), is(15));
        assertThat(notEvenInDungeon.getHp(), is(1));

    }
}