package com.mygdx.game.logic.activity.single;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.pathfinding.PathFinder;
import com.mygdx.game.logic.visibility.VisitedArea;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class ExplorationActivity implements Activity {

    private final Map2D targetDungeon;
    private final Actor actor;
    private MovementActivity movementActivity;
    private boolean firstRun = true;
    private boolean suspended = false;
    private int targetX = 0;
    private int targetY = 0;

    public ExplorationActivity(Map2D targetDungeon, Actor actor) {
        this.targetDungeon = targetDungeon;
        this.actor = actor;
        boolean[][] alreadyChecked = new boolean[targetDungeon.getWidth()][targetDungeon.getHeight()];
        Point target = findNextUnexploredArea(alreadyChecked, targetDungeon, actor.getX(), actor.getY());
        if(target != null) {
            targetX = target.getX();
            targetY = target.getY();
            this.movementActivity = new MovementActivity(actor, target.getX(), target.getY(), 0, new PathFinder());
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
        if(movementActivity.isDone()) {
            movementActivity.clear();
            boolean[][] alreadyChecked = new boolean[targetDungeon.getWidth()][targetDungeon.getHeight()];
            Point target = findNextUnexploredArea(alreadyChecked, targetDungeon, actor.getX(), actor.getY());
            if(target != null) {
                targetX = target.getX();
                targetY = target.getY();
                this.movementActivity = new MovementActivity(actor, target.getX(), target.getY(), 0, new PathFinder());
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

    public Point findNextUnexploredArea(boolean[][] alreadyChecked, Map2D targetDungeon, int x, int y) {
        if(x < 0 || y < 0 || x >= targetDungeon.getWidth() || y >= targetDungeon.getHeight() || targetDungeon.getTile(x,y).isObstacle()) {
            return null;
        }
        Deque<Point> points = new ArrayDeque<>();
        points.add(new Point(x,y));

        while(!points.isEmpty()) {
            Point next = points.remove();
            int px = next.getX();
            int py = next.getY();

            if(px < 0 || py < 0 || px >= targetDungeon.getWidth() || py >= targetDungeon.getHeight() || alreadyChecked[px][py] || targetDungeon.getTile(px,py).isObstacle()) {

            } else {
                alreadyChecked[px][py] = true;

                if(VisitedArea.NOT_VISITED.equals(targetDungeon.getVisitedareaMap()[px][py])) {
                    return new Point(px, py);
                }

                List<Point> newPoints = new ArrayList<>();

                newPoints.add(new Point(px, py - 1));
                newPoints.add(new Point(px, py + 1));
                newPoints.add(new Point(px + 1, py));
                newPoints.add(new Point(px-1, py));

                if(Config.Engine.ENABLE_8_WAYS_PATHFINDING) {
                    newPoints.add(new Point(px  +1, py -1));
                    newPoints.add(new Point(px - 1, py + 1));
                    newPoints.add(new Point(px - 1, py - 1));
                    newPoints.add(new Point(px + 1, py + 1));
                }

                Collections.shuffle(newPoints, new Random(System.currentTimeMillis()));
                points.addAll(newPoints);
            }

        }
        // no more visited area
        return null;
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
    }

    @Override
    public void resume() {
        movementActivity.resume();
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

}
