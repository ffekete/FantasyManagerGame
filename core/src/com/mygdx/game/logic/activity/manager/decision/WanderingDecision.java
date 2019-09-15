package com.mygdx.game.logic.activity.manager.decision;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.compound.WaitMoveActivity;
import com.mygdx.game.logic.activity.single.IdleActivity;
import com.mygdx.game.logic.activity.single.MovementActivity;
import com.mygdx.game.logic.activity.single.TimedIdleActivity;
import com.mygdx.game.logic.activity.single.WaitActivity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;

import java.util.*;

public class WanderingDecision implements Decision {


    private final List<Point> allPoints = new ArrayList<>(1000);
    private final Pool<Point> pointPool = new Pool<Point>() {
        @Override
        protected Point newObject() {
            return new Point(0, 0);
        }
    };

    @Override
    public boolean decide(Actor actor) {

        if(actor.isSleeping()) {
            return false;
        }

        if (Alignment.FRIENDLY.equals(actor.getAlignment())) {
            if (actor.getActivityStack().isEmpty() ||
                    (actor.getActivityStack().contains(IdleActivity.class) && actor.getActivityStack().getSize() == 1)) {
                // Nothing else to do, wandering around the map for now


                Point target = findNextUnexploredArea(actor.getCurrentMap(), actor.getX(), actor.getY());

                Activity activity = new MovementActivity(actor, target.getX(), target.getY(), 0);
                actor.getActivityStack().add(activity);
                return true;
            }
        } else { // enemies are waiting and moving
            if (actor.getActivityStack().isEmpty() ||
                    (actor.getActivityStack().contains(IdleActivity.class) && actor.getActivityStack().getSize() == 1)) {
                // Nothing else to do, wandering around the map for now
                WaitMoveActivity activity = new WaitMoveActivity(Config.Activity.MOVEMENT_PRIORITY, WaitActivity.class);
                activity.add(new TimedIdleActivity(5 + new Random().nextInt(15)));

                Point target = findNextUnexploredArea(actor.getCurrentMap(), actor.getX(), actor.getY());
                if(target != null) {
                    activity.add(new MovementActivity(actor, target.getX(), target.getY(), 0));
                    actor.getActivityStack().add(activity);
                    return true;
                } else {
                    return false;
                }
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

            if (px < 0 || py < 0 || px >= targetDungeon.getWidth() || py >= targetDungeon.getHeight() || alreadyChecked[px][py] || targetDungeon.getTile(px, py).isObstacle()) {

            } else {
                alreadyChecked[px][py] = true;

                if (MathUtil.distance(Point.of(px, py), start) >= 3) {
                    for (Point p : allPoints) {
                        pointPool.free(p);
                    }
                    return obtainPoint(px, py);
                }

                List<Point> newPoints = new ArrayList<>();

                if (py - 1 >= 0 && !alreadyChecked[px][py - 1])
                    newPoints.add(obtainPoint(px, py - 1));

                if (py + 1 < targetDungeon.getHeight() - 1 && !alreadyChecked[px][py + 1])
                    newPoints.add(obtainPoint(px, py + 1));

                if (px + 1 < targetDungeon.getWidth() - 1 && !alreadyChecked[px + 1][py])
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
