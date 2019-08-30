package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.item.factory.Placement;
import com.mygdx.game.item.weapon.bow.LongBow;
import com.mygdx.game.item.weapon.sword.ShortSword;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveAndInteractActivity;
import com.mygdx.game.logic.activity.manager.ActivityManager;
import com.mygdx.game.logic.activity.single.PickUpItemActivity;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.map.dungeon.Dungeon;
import com.mygdx.game.object.decoration.TreasureChest;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ItemPickUpTest {

    @Test
    public void testDecideBetweenTwoItems() {

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Item longBow = new ItemFactory().create(LongBow.class, dungeon, Placement.FIXED.X(1).Y(1));
        Item longBow2 = new ItemFactory().create(LongBow.class, dungeon, Placement.FIXED.X(5).Y(5));

        Actor actor = new Warrior();
        actor.setCurrentMap(dungeon);
        actor.equip(new ShortSword());
        actor.setCoordinates(Point.of(3,3));

        visibilityMask.setValue(1,1, actor);
        visibilityMask.setValue(5,5, actor);

        MapRegistry.INSTANCE.add(dungeon);

        new ActivityManager().manage(actor);
        new ActivityManager().manage(actor);

        assertThat(actor.getActivityStack().getSize(), is(2));
        assertThat(actor.getActivityStack().contains(PickUpItemActivity.class), is(true));
    }

    @Test
    public void testDecideBetweenTwoItems_oneIsTReasureChest() {

        VisibilityMask visibilityMask = new VisibilityMask(100, 100);
        Dungeon dungeon = new DummyDungeonCreator().create(3);
        VisibilityMapRegistry.INSTANCE.add(dungeon, visibilityMask);

        Item longBow = new ItemFactory().create(LongBow.class, dungeon, Placement.FIXED.X(1).Y(0));
        TreasureChest treasureChest = ObjectFactory.create(TreasureChest.class, dungeon, ObjectPlacement.FIXED.X(5).Y(0));
        treasureChest.setMoney(100);

        Actor actor = new Warrior();
        actor.setCurrentMap(dungeon);
        actor.equip(new ShortSword());
        actor.setCoordinates(Point.of(3,0));

        visibilityMask.setValue(1,0, actor);
        visibilityMask.setValue(5,0, actor);

        MapRegistry.INSTANCE.add(dungeon);

        new ActivityManager().manage(actor);

        assertThat(actor.getActivityStack().getSize(), is(2));
        assertThat(actor.getActivityStack().contains(MoveAndInteractActivity.class), is(true));

        new ActivityManager().manage(actor);

        assertThat(actor.getActivityStack().getSize(), is(2));
        assertThat(actor.getActivityStack().contains(MoveAndInteractActivity.class), is(true));
    }

}