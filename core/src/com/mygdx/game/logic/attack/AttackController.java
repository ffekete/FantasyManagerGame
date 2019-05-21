package com.mygdx.game.logic.attack;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.Direction;
import com.mygdx.game.actor.component.Attributes;
import com.mygdx.game.item.weapon.Weapon;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AttackController {

    public static final AttackController INSTANCE = new AttackController(new WeaponSkillSelector(), new ShieldSelector());

    private final WeaponSkillSelector weaponSkillSelector;
    private final ShieldSelector shieldSelector;

    private Map<Actor, Actor> actors = new HashMap<>();

    private AttackController(WeaponSkillSelector weaponSkillSelector, ShieldSelector shieldSelector) {
        this.weaponSkillSelector = weaponSkillSelector;
        this.shieldSelector = shieldSelector;
    }

    public void clearAttackingHistory(Actor actor) {
        actors.remove(actor);
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

    public void registerAttackHistory(Actor attacker, Actor target) {
        if(!actors.containsKey(attacker) || actors.get(attacker) != target) {
            actors.put(attacker, target);
        }
    }

    public Actor getTargetFor(Actor attacker) {
        return actors.get(attacker);
    }

    public void calculateAttack(Actor attacker, Actor victim) {
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
                + attacker.getAttribute(Attributes.Dexterity) * 3; // fist attack has no skill

        int evasion = victim.getAttribute(Attributes.Reflexes) * 2 + victim.getAttribute(Attributes.Dexterity) + victim.getDefenseValue();

        if(toHit < hitThreshold - evasion) {
            victim.setHp(victim.getHp() - damage);
        }
    }

    private void attackWithWeapon(Actor attacker, Actor victim, Weapon weapon) {
        int damage = weapon.getDamage();
        int toHit = new Random().nextInt(100);

        float bestSkill = weaponSkillSelector.findBestSkillFor(attacker, weapon);

        int hitThreshold = 20
                + attacker.getAttribute(Attributes.Reflexes)
                + attacker.getAttribute(Attributes.Dexterity) * 2
                + (int)bestSkill * 5;

        int evasion = shieldSelector.getShieldValue(victim) + victim.getAttribute(Attributes.Reflexes) * 2 + victim.getAttribute(Attributes.Dexterity);

        if(toHit < hitThreshold - evasion) {
            victim.setHp(victim.getHp() - damage);
            weapon.onHit(victim);
        }
    }




}
