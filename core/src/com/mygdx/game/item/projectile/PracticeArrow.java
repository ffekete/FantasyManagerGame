package com.mygdx.game.item.projectile;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.ArrowAction;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.spell.ProjectilePathCalculator;

import java.util.ArrayList;
import java.util.List;

public class PracticeArrow implements Projectile {

    private Point start;
    private Point end;
    private List<Point> path;
    private Action arrowAction;
    private ProjectilePathCalculator projectilePathCalculator = new ProjectilePathCalculator();
    private int delay = 2;

    public PracticeArrow(Point target) {
        path = new ArrayList<>();
        end = target;
    }

    @Override
    public void init(Actor shooter, Actor notUsed) {
        start = shooter.getCoordinates();
        calculatePath();
        arrowAction = new ArrowAction(shooter, end);
        ActionRegistry.INSTANCE.add(shooter.getCurrentMap(), arrowAction);
    }

    @Override
    public void update() {
        delay --;
        if (delay == 0 && !path.isEmpty()) {
            arrowAction.setCoordinates(path.remove(0));
            delay = 2;
        }
    }

    @Override
    public void finish() {

    }

    @Override
    public boolean isFinished() {
        return path.isEmpty();
    }

    private void calculatePath() {
        path = new ArrayList<>();
        projectilePathCalculator.calculate(start.getX(), start.getY(), end.getX(), end.getY(), path);
    }


}
