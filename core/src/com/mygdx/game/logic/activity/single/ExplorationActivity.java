package com.mygdx.game.logic.activity.single;

import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;

import java.util.*;

public class ExplorationActivity implements Activity {

    private final Pool<Point> pointPool = new Pool<Point>() {
        @Override
        protected Point newObject() {
            return new Point(0, 0);
        }
    };

    private final Map2D targetDungeon;
    private final Actor actor;
    private MovementActivity movementActivity;
    private boolean firstRun = true;
    private boolean suspended = false;
    private int targetX = 0;
    private int targetY = 0;

    // performance enhancement
    private Point point;
    private final List<Point> allPoints = new ArrayList<>();

    public ExplorationActivity(Map2D targetDungeon, Actor actor) {
        this.targetDungeon = targetDungeon;

        this.actor = actor;
        Point target = findNextUnexploredArea(targetDungeon, actor.getX(), actor.getY());
        if (target != null) {
            targetX = target.getX();
            targetY = target.getY();
            this.movementActivity = new MovementActivity(actor, target.getX(), target.getY(), 0);
        } else {
            targetX = actor.getX();
            targetY = actor.getY();
            this.movementActivity = new MovementActivity(actor, targetX, targetY, 0);
        }
    }

    @Override
    public boolean isDone() {
        return targetDungeon.isExplored();
    }

    @Override
    public void update() {
        movementActivity.update();
        // if dungeon explored or someone else explored the area
        if (movementActivity.isDone()) {
            movementActivity.clear();

            Point target = findNextUnexploredArea(targetDungeon, actor.getX(), actor.getY());
            if (target != null) {
                targetX = target.getX();
                targetY = target.getY();
                this.movementActivity = new MovementActivity(actor, target.getX(), target.getY(), 0);
                movementActivity.init();
            }
        }
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public Point findNextUnexploredArea(Map2D targetDungeon, int x, int y) {
        boolean[][] alreadyChecked = new boolean[targetDungeon.getWidth()][targetDungeon.getHeight()];

        if (x < 0 || y < 0 || x >= targetDungeon.getWidth() || y >= targetDungeon.getHeight() || targetDungeon.getTile(x, y).isObstacle()) {
            return null;
        }
        Deque<Point> points = new ArrayDeque<>();
        points.add(obtainPoint(x, y));

        while (!points.isEmpty()) {
            Point next = points.remove();
            int px = next.getX();
            int py = next.getY();

            if (px < 0 || py < 0 || px >= targetDungeon.getWidth() || py >= targetDungeon.getHeight() || alreadyChecked[px][py]) {

            } else if(targetDungeon.getTile(px, py).isObstacle() || targetDungeon.isObstacle(px, py)) {
                alreadyChecked[px][py] = true;

            } else {
                alreadyChecked[px][py] = true;

                if (VisitedArea.NOT_VISITED.equals(targetDungeon.getVisitedareaMap()[px][py])) {
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
        point = pointPool.obtain();
        point.update(x, y);
        allPoints.add(point);
        return point;
    }

    @Override
    public void init() {
        movementActivity.init();
        firstRun = false;
    }

    @Override
    public void cancel() {
        movementActivity.cancel();
    }

    @Override
    public int getPriority() {
        return Config.Activity.EXPLORATION_PRIORITY;
    }

    @Override
    public boolean isFirstRun() {
        return firstRun;
    }

    @Override
    public void suspend() {
        movementActivity.suspend();
        suspended = true;
    }

    @Override
    public void resume() {

        movementActivity.clear();

        Point target = findNextUnexploredArea(targetDungeon, actor.getX(), actor.getY());
        if (target != null) {
            targetX = target.getX();
            targetY = target.getY();
            this.movementActivity = new MovementActivity(actor, target.getX(), target.getY(), 0);
            movementActivity.init();
        }

        suspended = false;
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    @Override
    public void clear() {
        System.out.println("I explored all!");
    }

    @Override
    public boolean isCancellable() {
        //return targetDungeon.isExplored();
        return false;
    }

    @Override
    public Class getCurrentClass() {
        return this.getClass();
    }

    @Override
    public void countDown() {
        movementActivity.countDown();
    }

    @Override
    public boolean isTriggered() {
        return movementActivity.isTriggered();
    }

    @Override
    public int compareTo(Activity o) {
        return movementActivity.compareTo(o);
    }


    @Override
    public Activity getCurrentActivity() {
        return movementActivity;
    }

    @Override
    public Class<? extends Activity> getMainClass() {
        return this.getClass();
    }
}
