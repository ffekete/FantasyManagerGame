package com.mygdx.game.logic.activity.manager.decision;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.compound.MoveAndSleepActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.SleepActivity;
import com.mygdx.game.logic.activity.single.SleepAtCampfireActivity;
import com.mygdx.game.logic.time.DayTimeCalculator;
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

import java.util.*;

public class SleepingDecision implements Decision {

    private final List<Point> allPoints = new ArrayList<>(1000);
    private final Pool<Point> pointPool = new Pool<Point>() {
        @Override
        protected Point newObject() {
            return new Point(0, 0);
        }
    };

    @Override
    public boolean decide(Actor actor) {

        if (actor.isSleeping()) {
            return true;
        }

        if ((DayTimeCalculator.INSTANCE.isItNight() || actor.isSleepy()) && Map2D.MapType.WORLD_MAP.equals(actor.getCurrentMap().getMapType())) {

            House house = HouseRegistry.INSTANCE.getOwnedHouses().getOrDefault(actor, null);

            if (house != null) {

                List<Bed> bed = house.getFurnitureOfType(Bed.class);


                if (bed.isEmpty() || MathUtil.distance(((WorldObject) bed.get(0)).getCoordinates(), actor.getCoordinates()) >= 20) {

                    // find a spot to camp

                    //find nearest campfire
                    Optional<CampFire> fires = ObjectRegistry.INSTANCE.getObjects(actor.getCurrentMap(), Cluster.of(actor.getX(), actor.getY())).flatMap(worldObjects -> worldObjects.stream().filter(o -> CampFire.class.isAssignableFrom(o.getClass()) && ((CampFire) o).hasFreeSpace()).map(o2 -> (CampFire) o2).findAny());

                    // start camp
                    CampFire campFire = fires.orElseGet(() -> ObjectFactory.create(CampFire.class, actor.getCurrentMap(), ObjectPlacement.FIXED.X(actor.getX()).Y(actor.getY())));

                    MoveAndSleepActivity moveAndSleepActivity = new MoveAndSleepActivity(Config.CommonActivity.SLEEP_PRIORITY, SleepActivity.class);
                    SleepAtCampfireActivity sleepActivity = new SleepAtCampfireActivity(actor, campFire);

                    Point target = campFire.getNextFreeSpace();
                    campFire.bookSpace(target);

                    moveAndSleepActivity.add(new MovementActivity(actor, target.getX(), target.getY(), 0));
                    moveAndSleepActivity.add(sleepActivity);

                    actor.getActivityStack().add(moveAndSleepActivity);
                    return true;
                } else {
                    System.out.println(actor.getName() + " has a bed, going to " + ((WorldObject) bed.get(0)).getX() + " " + ((WorldObject) bed.get(0)).getY());
                    MoveAndSleepActivity moveAndSleepActivity = new MoveAndSleepActivity(Config.CommonActivity.SLEEP_PRIORITY, SleepActivity.class);
                    moveAndSleepActivity.add(new MovementActivity(actor, (int) ((WorldObject) bed.get(0)).getX(), (int) ((WorldObject) bed.get(0)).getY(), 0));
                    moveAndSleepActivity.add(new SleepActivity(actor));

                    actor.getActivityStack().add(moveAndSleepActivity);
                    return true;
                }
            } else {
                // find a spot to camp

                //find nearest campfire
                Optional<CampFire> fires = ObjectRegistry.INSTANCE.getObjects(actor.getCurrentMap(), Cluster.of(actor.getX(), actor.getY())).flatMap(worldObjects -> worldObjects.stream().filter(o -> CampFire.class.isAssignableFrom(o.getClass()) && ((CampFire) o).hasFreeSpace()).map(o2 -> (CampFire) o2).findAny());

                // start camp
                CampFire campFire = fires.orElseGet(() -> {
                    Point p = findNextUnexploredArea(actor.getCurrentMap(), actor.getX(), actor.getY());
                    return ObjectFactory.create(CampFire.class, actor.getCurrentMap(), ObjectPlacement.FIXED.X(p.getX()).Y(p.getY()));
                });

                MoveAndSleepActivity moveAndSleepActivity = new MoveAndSleepActivity(Config.CommonActivity.SLEEP_PRIORITY, SleepActivity.class);
                SleepAtCampfireActivity sleepActivity = new SleepAtCampfireActivity(actor, campFire);

                Point target = campFire.getNextFreeSpace();
                campFire.bookSpace(target);

                moveAndSleepActivity.add(new MovementActivity(actor, target.getX(), target.getY(), 0));
                moveAndSleepActivity.add(sleepActivity);

                actor.getActivityStack().add(moveAndSleepActivity);
                return true;
            }
        }
        return false;
    }


    public Point findNextUnexploredArea(Map2D targetDungeon, int x, int y) {
        boolean[][] alreadyChecked = new boolean[targetDungeon.getWidth()][targetDungeon.getHeight()];
        if (x < 0 || y < 0 || x >= targetDungeon.getWidth() || y >= targetDungeon.getHeight() || targetDungeon.getTile(x, y).isObstacle()) {
            return null;
        }
        Deque<Point> points = new ArrayDeque<>();
        Point start = obtainPoint(x, y);
        points.add(start);

        while (!points.isEmpty()) {
            Point next = points.remove();
            int px = next.getX();
            int py = next.getY();

            if (px < 0 || py < 0 || px >= targetDungeon.getWidth() || py >= targetDungeon.getHeight() || alreadyChecked[px][py] || targetDungeon.isObstacle(px, py) || targetDungeon.getTile(px, py).isObstacle()) {

            } else {
                alreadyChecked[px][py] = true;

                if (ObjectRegistry.INSTANCE.getObjectGrid().get(targetDungeon)[px][py][0] == null && ObjectRegistry.INSTANCE.getObjectGrid().get(targetDungeon)[px][py][1] == null) {
                    for (Point p : allPoints) {
                        pointPool.free(p);
                    }
                    return new Point(px, py);
                }

                List<Point> newPoints = new ArrayList<>();

                if (py - 1 >= 0 && !alreadyChecked[px][py - 1])
                    newPoints.add(obtainPoint(px, py - 1));

                if (py + 1 <= targetDungeon.getHeight() - 1 && !alreadyChecked[px][py + 1])
                    newPoints.add(obtainPoint(px, py + 1));

                if (px + 1 <= targetDungeon.getWidth() - 1 && !alreadyChecked[px + 1][py])
                    newPoints.add(obtainPoint(px + 1, py));

                if (px - 1 >= 0 && !alreadyChecked[px - 1][py])
                    newPoints.add(obtainPoint(px - 1, py));

                if (Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
                    newPoints.add(obtainPoint(px + 1, py - 1));
                    newPoints.add(obtainPoint(px - 1, py + 1));
                    newPoints.add(obtainPoint(px - 1, py - 1));
                    newPoints.add(obtainPoint(px + 1, py + 1));
                }

                Collections.shuffle(newPoints, new Random(System.currentTimeMillis()));
                points.addAll(newPoints);
            }

        }
        // no more visited area
        return null;
    }


    private Point obtainPoint(int x, int y) {
        Point point = pointPool.obtain();
        point.update(x, y);
        allPoints.add(point);
        return point;
    }
}
