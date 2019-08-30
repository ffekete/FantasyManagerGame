package com.mygdx.game.object.house;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.furniture.IncompleteWoodenBed;
import com.mygdx.game.object.interactive.PracticeFigure;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.object.wall.IncompleteWoodenWall;
import com.mygdx.game.object.wall.WoodenWall;
import com.mygdx.game.object.wall.WoodenWallDoor;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HouseBuilderTest {

    @BeforeMethod
    public void setUp() {
        HouseRegistry.INSTANCE.getEmptyHouses().clear();
        HouseRegistry.INSTANCE.getOwnedHouses().clear();
        HouseRegistry.INSTANCE.getHouses().clear();
        ActorRegistry.INSTANCE.clear();
        ObjectRegistry.INSTANCE.clear();
        ObjectRegistry.INSTANCE.getObjectGrid().clear();
        MapRegistry.INSTANCE.getMaps().clear();
    }

    @Test
    public void shouldNotDetectBuiltHouse_simpleWall() {

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);

        long start = System.currentTimeMillis();
        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);
        System.out.println(System.currentTimeMillis() - start);

        assertThat(result.isEmpty(), is(true));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().isEmpty(), is(true));
    }

    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse() {

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);

        //ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(4).Y(4));
        ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(1).Y(1));
        ObjectFactory.create(PracticeFigure.class, map2D, ObjectPlacement.FIXED.X(4).Y(4));

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.size(), is(8));
        assertThat(result.isEmpty(), is(false));

        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(1));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getWalls().size(), is(8));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getX(), is(5));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getY(), is(5));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getX(), is(3));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getY(), is(3));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getFurnitures().size(), is(1));
    }

    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse_doorIncluded() {

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);

        ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(4).Y(4));
        ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(1).Y(1));

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWallDoor.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.size(), is(8));
        assertThat(result.isEmpty(), is(false));

        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(1));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getWalls().size(), is(8));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getX(), is(5));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getY(), is(5));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getX(), is(3));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getY(), is(3));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getFurnitures().size(), is(1));
    }

    @Test
    public void shouldNotDetectBuiltHouse_IncompleteWallInTheMiddle() {

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);


        // . . . . . . .
        // . . . . . . .
        // . . . . . . .
        // . . . x x x .
        // . . . x . I .
        // . . . x x x .
        // . . . . x . e


        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(IncompleteWoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        WorldObject externalBlock = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(true));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(0));
    }

    @Test
    public void shouldNotDetectBuiltHouse_HoleInTheMiddle() {

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);


        // . . . . . . .
        // . . . . . . .
        // . . . . . . .
        // . . . x x x .
        // . . . x . . .
        // . . . x x x .
        // . . . . x . e


        IncompleteWoodenBed bed1 = ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(4).Y(4));

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        // this is the hole
        //ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        WorldObject externalBlock = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(true));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(0));

        // but then we plug the hole
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(3));

        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(1));

        // add a bed later
        IncompleteWoodenBed bed2 = ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));

        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().stream().filter(house -> house.getFurnitures().contains(bed1)).count(), is(1L));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().stream().filter(house -> house.getFurnitures().contains(bed2)).count(), is(1L));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().stream().filter(house -> house.getFurnitures().size() == 2).count(), is(1L));
    }

    @Test
    public void shouldNotDetectBuiltHouse_Hole2() {

        // . . . . . . . .
        // . . . . . . . .
        // . . . . . . . .
        // . . . . x x . .
        // . . . x . x . .
        // . . . x x x x x
        // . . . . x . x .

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));

        // hole
        //ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(7).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(true));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(0));
    }


    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse_notAllBlocksAreForThisHouse_tailInTheLoop_twoHouses_twoActors() {

        // x x x . . . . .
        // x . x . . . . .
        // x x x . . . . .
        // . . . x x x . .
        // . . . x . x . .
        // . . . x x x x x
        // . . . . x . x .

        Actor actor = new Wizard();
        Actor actor2 = new Wizard();

        Map2D map2D = new DummyDungeonCreator().create(3);

        ActorRegistry.INSTANCE.add(map2D, actor);
        ActorRegistry.INSTANCE.add(map2D, actor2);

        MapRegistry.INSTANCE.add(map2D);

        IncompleteWoodenBed bed1 = ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(4).Y(4));
        IncompleteWoodenBed bed2 = ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(1).Y(1));

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));

        // first house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(0));


        // second house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(7).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.size(), is(12));

        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(0));
        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().size(), is(2));


        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().values().stream().filter(house -> house.getFurnitures().contains(bed1)).count(), is(1L));
        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().values().stream().filter(house -> house.getFurnitures().contains(bed2)).count(), is(1L));
        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().values().stream().filter(house -> house.getFurnitures().size() == 1).count(), is(2L));
    }

    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse_notAllBlocksAreForThisHouse_tailInTheLoop_twoHouses_threeActors() {

        // x x x . . . . .
        // x . x . . . . .
        // x x x . . . . .
        // . . . x x x . .
        // . . . x . x . .
        // . . . x x x x x
        // . . . . x . x .

        Actor actor = new Wizard();
        Actor actor2 = new Wizard();
        Actor actor3 = new Wizard();

        Map2D map2D = new DummyDungeonCreator().create(3);

        ActorRegistry.INSTANCE.add(map2D, actor);
        ActorRegistry.INSTANCE.add(map2D, actor2);
        ActorRegistry.INSTANCE.add(map2D, actor3);

        MapRegistry.INSTANCE.add(map2D);

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));

        // first house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(0));


        // second house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(7).Y(6));

        IncompleteWoodenBed bed1 = ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(4).Y(4));
        IncompleteWoodenBed bed2 = ObjectFactory.create(IncompleteWoodenBed.class, map2D, ObjectPlacement.FIXED.X(1).Y(1));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.size(), is(12));

        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(0));
        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().size(), is(2));

        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().values().stream().filter(house -> house.getFurnitures().contains(bed1)).count(), is(1L));
        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().values().stream().filter(house -> house.getFurnitures().contains(bed2)).count(), is(1L));
        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().values().stream().filter(house -> house.getFurnitures().size() == 1).count(), is(2L));
    }

    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse_notAllBlocksAreForThisHouse_tailInTheLoop_twoHouses_oneActors() {

        // x x x . . . . .
        // x . x . . . . .
        // x x x . . . . .
        // . . . x x x . .
        // . . . x . x . .
        // . . . x x x x x
        // . . . . x . x .

        Actor actor = new Wizard();

        Map2D map2D = new DummyDungeonCreator().create(3);

        ActorRegistry.INSTANCE.add(map2D, actor);

        MapRegistry.INSTANCE.add(map2D);

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));

        // first house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(0));


        // second house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(7).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.size(), is(12));

        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(1));
        assertThat(HouseRegistry.INSTANCE.getOwnedHouses().size(), is(1));
    }

    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse_notAllBlocksAreForThisHouse_tailInTheLoop_twoHouses() {

        // x x x . . . . .
        // x . x . . . . .
        // x x x . . . . .
        // . . . x x x . .
        // . . . x . x . .
        // . . . x x x x x
        // . . . . x . x .

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));

        // first house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(0).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(2));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(1));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(0));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(1).Y(0));


        // second house
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(7).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.size(), is(12));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(2));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).stream().anyMatch(walls -> walls.getWalls().size() == 8), is(true));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).stream().anyMatch(walls -> walls.getWalls().size() == 12), is(true));
    }

    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse_notAllBlocksAreForThisHouse_tailInTheLoop() {

        // . . . . . . . .
        // . . . . . . . .
        // . . . . . . . .
        // . . . x x x . .
        // . . . x . x . .
        // . . . x x x x x
        // . . . . x . x .

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(7).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.size(), is(12));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(1));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getWalls().size(), is(12));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getX(), is(7));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getY(), is(6));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getX(), is(3));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getY(), is(3));
    }

    @Test
    public void shouldDetectBuiltHouse_simpleRectangularHouse_notAllBlocksAreForThisHouse() {

        Map2D map2D = new DummyDungeonCreator().create(3);

        MapRegistry.INSTANCE.add(map2D);

        WoodenWall wall = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(5));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(6));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(5));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(3).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(4));
        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(5).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(4).Y(3));

        ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(2).Y(2));

        WorldObject externalBlock = ObjectFactory.create(WoodenWall.class, map2D, ObjectPlacement.FIXED.X(6).Y(6));

        Set<WorldObject> result = HouseBuiltDetector.INSTANCE.isAHouse(map2D, wall);

        assertThat(result.isEmpty(), is(false));
        assertThat(result.size(), is(9));
        assertThat(result.contains(externalBlock), is(false));
        assertThat(HouseRegistry.INSTANCE.getEmptyHouses().size(), is(1));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getWalls().size(), is(9));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getX(), is(5));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getBottomRight().getY(), is(6));

        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getX(), is(3));
        assertThat(new ArrayList<>(HouseRegistry.INSTANCE.getEmptyHouses()).get(0).getTopLeft().getY(), is(3));
    }

}