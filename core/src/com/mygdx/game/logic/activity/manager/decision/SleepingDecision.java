package com.mygdx.game.logic.activity.manager.decision;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.CompoundActivity;
import com.mygdx.game.logic.activity.compound.MoveAndSleepActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.SleepActivity;
import com.mygdx.game.logic.activity.single.SleepAtCampfireActivity;
import com.mygdx.game.logic.activity.single.SleepOutsideActivity;
import com.mygdx.game.map.Cluster;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.object.decoration.CampFire;
import com.mygdx.game.object.factory.ObjectFactory;
import com.mygdx.game.object.furniture.Bed;
import com.mygdx.game.object.house.House;
import com.mygdx.game.object.placement.ObjectPlacement;
import com.mygdx.game.registry.HouseRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SleepingDecision implements Decision {
    @Override
    public boolean decide(Actor actor) {

        if (actor.getActivityStack().contains(SleepAtCampfireActivity.class) || actor.getActivityStack().contains(SleepActivity.class) || actor.getActivityStack().contains(MoveAndSleepActivity.class) || actor.getActivityStack().contains(SleepOutsideActivity.class)) {
            return true;
        }

        if (actor.isSleepy() && Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())) {

            House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);

            if (house != null) {

                Optional<Bed> bed = house.getFurnitures().stream().filter(furniture -> Bed.class.isAssignableFrom(furniture.getClass())).map(furniture -> (Bed) furniture).findAny();


                if (!bed.isPresent()) {

                    // find a spot to camp

                    // start camp
                    CampFire campFire = ObjectFactory.create(CampFire.class, actor.getCurrentMap(), ObjectPlacement.FIXED.X(actor.getX()).Y(actor.getY()));

                    MoveAndSleepActivity moveAndSleepActivity = new MoveAndSleepActivity(Config.CommonActivity.SLEEP_PRIORITY);
                    SleepAtCampfireActivity sleepActivity = new SleepAtCampfireActivity(actor, campFire);

                    Point target = campFire.getNextFreeSpace();

                    moveAndSleepActivity.add(new MovementActivity(actor, target.getX(), target.getY(), 0, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap())));
                    moveAndSleepActivity.add(sleepActivity);

                    actor.getActivityStack().clear();
                    actor.getActivityStack().add(sleepActivity);
                    return true;
                } else {
                    MoveAndSleepActivity moveAndSleepActivity = new MoveAndSleepActivity(Config.CommonActivity.SLEEP_PRIORITY);
                    moveAndSleepActivity.add(new MovementActivity(actor, (int) ((WorldObject) bed.get()).getX(), (int) ((WorldObject) bed.get()).getY(), 0, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap())));
                    moveAndSleepActivity.add(new SleepActivity(actor));
                    actor.getActivityStack().clear();
                    actor.getActivityStack().add(moveAndSleepActivity);
                }
            } else {
                // find a spot to camp

                //find nearest campfire
                Optional<CampFire> fires = ObjectRegistry.INSTANCE.getObjects(actor.getCurrentMap(), Cluster.of(actor.getX(), actor.getY())).flatMap(worldObjects ->  worldObjects.stream().filter(o -> CampFire.class.isAssignableFrom(o.getClass()) && ((CampFire)o).hasFreeSpace()).map(o2 -> (CampFire)o2).findAny());

                // start camp
                CampFire campFire = fires.orElseGet(() -> ObjectFactory.create(CampFire.class, actor.getCurrentMap(), ObjectPlacement.FIXED.X(actor.getX()).Y(actor.getY())));

                MoveAndSleepActivity moveAndSleepActivity = new MoveAndSleepActivity(Config.CommonActivity.SLEEP_PRIORITY);
                SleepAtCampfireActivity sleepActivity = new SleepAtCampfireActivity(actor, campFire);

                Point target = campFire.getNextFreeSpace();
                campFire.bookSpace(target);

                moveAndSleepActivity.add(new MovementActivity(actor, target.getX(), target.getY(), 0, MapRegistry.INSTANCE.getPathFinderFor(actor.getCurrentMap())));
                moveAndSleepActivity.add(sleepActivity);

                actor.getActivityStack().clear();
                actor.getActivityStack().add(moveAndSleepActivity);
                return true;
            }
        }
        return false;
    }
}
