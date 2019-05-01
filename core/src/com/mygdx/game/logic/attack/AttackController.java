package com.mygdx.game.logic.attack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.actor.component.Attributes;
import com.mygdx.game.item.weapon.Weapon;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AttackController {

    public static final AttackController INSTANCE = new AttackController();

    private Map<Actor, Actor> actors = new HashMap<>();

    public void clearAttackingHistory(Actor actor, Actor target) {
        actors.remove(actor);
        actors.remove(target);
    }

    public Direction getAttackingDirection(Actor actor) {
        Actor target = actors.getOrDefault(actor, null);
        if(target != null) {
            if(actor.getX() < target.getX())
                return Direction.RIGHT;

            if(actor.getX() > target.getX())
                return Direction.LEFT;

            if(actor.getY() < target.getY())
                return Direction.UP;

            return Direction.DOWN;
        }
        return null;
    }

    public Actor getTargetFor(Actor attacker) {
        return actors.get(attacker);
    }

    public void calculateAttack(Actor attacker, Actor victim) {

        if(!(actors.containsKey(attacker) && actors.get(attacker).equals(victim))) {
            actors.put(attacker, victim);
        }

        if(attacker.getWeapons().isEmpty()) {
            attackWithFist(attacker, victim);
        } else {
            for (Weapon weapon : attacker.getWeapons()) {
                attackWithWeapon(attacker, victim, weapon);
            }
        }
    }

    private void attackWithFist(Actor attacker, Actor victim) {
        int damage = new Random().nextInt(attacker.getAttribute(Attributes.Strength) / 4);
        int toHit = new Random().nextInt(100);
        int hitThreshold = 20
                + attacker.getAttribute(Attributes.Reflexes)
                + attacker.getAttribute(Attributes.Dexterity) * 2
                + 1 * 5; // todo add weapon skill here

        int evasion = victim.getAttribute(Attributes.Reflexes) * 2 + victim.getAttribute(Attributes.Dexterity) + victim.getDefenseValue();

        if(toHit < hitThreshold - evasion) {
            System.out.println(attacker.getName() + " attacked " + victim.getName() + " damage: " + damage);
            victim.setHp(victim.getHp() - damage);
        }
    }

    private void attackWithWeapon(Actor attacker, Actor victim, Weapon weapon) {
        int damage = weapon.getDamage();
        int toHit = new Random().nextInt(100);
        int hitThreshold = 20
                + attacker.getAttribute(Attributes.Reflexes)
                + attacker.getAttribute(Attributes.Dexterity) * 2
                + 1 * 5; // todo add weapon skill here

        int evasion = victim.getAttribute(Attributes.Reflexes) * 2 + victim.getAttribute(Attributes.Dexterity);

        if(toHit < hitThreshold - evasion) {
            System.out.println(attacker.getName() + " attacked " + victim.getName() + " damage: " + damage);
            victim.setHp(victim.getHp() - damage);
        }
    }

}
