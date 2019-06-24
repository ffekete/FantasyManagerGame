package com.mygdx.game.item.projectile;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.weapon.bow.Bow;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.ArrowAction;
import com.mygdx.game.logic.attack.AttackController;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.spell.PhysicalDamage;
import com.mygdx.game.spell.ProjectilePathCalculator;

import java.util.ArrayList;
import java.util.List;

public class Arrow implements Projectile {

    private Point start;
    private Point end;
    private List<Point> path;
    private Action arrowAction;
    private ProjectilePathCalculator projectilePathCalculator = new ProjectilePathCalculator();
    private Actor shooter;
    private Actor target;
    private int delay = 2;
    private Bow weapon;

    public Arrow(Bow weapon) {
        this.weapon = weapon;
        path = new ArrayList<>();
    }

    @Override
    public void init(Actor shooter, Actor target) {
        start = shooter.getCoordinates();
        end = target.getCoordinates();
        calculatePath();
        arrowAction = new ArrowAction(shooter, target.getCoordinates());
        ActionRegistry.INSTANCE.add(shooter.getCurrentMap(), arrowAction);
        this.shooter = shooter;
        this.target = target;
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
        if(AttackController.INSTANCE.calculateRangedAttack(shooter, target))
            new PhysicalDamage(shooter).calculate(target, weapon.getDamage());
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
