package com.mygdx.game.actor;

import com.mygdx.game.Config;
import com.mygdx.game.actor.component.attribute.Attributes;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.item.Item;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.effect.AttackSpeedReduction;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.stack.ActivityStack;
import com.mygdx.game.object.light.LightSource;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.registry.LightSourceRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class AbstractActor implements Actor {

    private String name;
    private Inventory inventory;
    private Map<Attributes, Integer> baseAttributes;
    private Map<WeaponSkill, Integer> weaponSkills;
    private Map<MagicSkill, Integer> magicSkills;
    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;

    private Point coordinates;
    private int hungerLevel;
    private Alignment alignment;

    private float xOffset = 0;
    private float yOffset = 0;

    private int actualHp;
    private int hpModifier = 3;

    private Equipable leftHand = null;
    private Equipable rightHand = null;

    private ActivityStack activityStack = new ActivityStack(new PriorityQueue<>(), this);

    private Map2D currentMap;

    private Armor wornArmor = null;

    public AbstractActor() {
        this.hungerLevel = Config.BASE_HUNGER_LEVEL;
        this.baseAttributes = new HashMap<>();
        this.weaponSkills = new HashMap<>();
        this.magicSkills = new HashMap<>();

        for (Attributes a : Attributes.values()) {
            baseAttributes.put(a, 0);
        }

        for(WeaponSkill s : WeaponSkill.values()) {
            weaponSkills.put(s, 0);
        }
        actualHp = getMaxHp();
        this.inventory = new Inventory();
    }

    @Override
    public int getX() {
        return coordinates.getX();
    }

    @Override
    public int getY() {
        return coordinates.getY();
    }

    @Override
    public ActivityStack getActivityStack() {
        return this.activityStack;
    }

    @Override
    public void setCoordinates(Point point) {
        this.coordinates = point;
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    // override this later for monsters!
    @Override
    public boolean isHungry() {
        return hungerLevel == Config.BASE_HUNGER_LIMIT;
    }

    @Override
    public void increaseHunger(int amount) {
        hungerLevel = Math.min(Config.BASE_HUNGER_LIMIT, hungerLevel + amount);
    }

    @Override
    public void decreaseHunger(int amount) {
        hungerLevel = Math.max(0, hungerLevel - amount);
    }

    @Override
    public int getHungerLevel() {
        return hungerLevel;
    }

    @Override
    public void pickUp(Item item) {
        this.inventory.add(item);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public int getAttribute(Attributes a) {
        return baseAttributes.get(a);
    }

    public int getMovementSpeed() {
        return 40 - getAttribute(Attributes.Dexterity);
    }

    @Override
    public Map2D getCurrentMap() {
        return currentMap;
    }

    @Override
    public void setCurrentMap(Map2D map) {
        this.currentMap = map;
    }

    @Override
    public float getxOffset() {
        return xOffset;
    }

    @Override
    public float getyOffset() {
        return yOffset;
    }

    @Override
    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    @Override
    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    @Override
    public Alignment getAlignment() {
        return this.alignment;
    }

    @Override
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public int getAttackSpeed() {
        int baseSpeed = 50 - getAttribute(Attributes.Reflexes);

        int modifier = effectRegistry.getAll(this).stream().filter(effect -> AttackSpeedReduction.class.equals(effect.getClass())).map(Effect::getPower).reduce(0, (a, b) -> a+b);

        return baseSpeed - modifier;

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void equip(Equipable equipable) {
        // if it is shield
        if (Shield.class.isAssignableFrom(equipable.getClass()) && leftHand == null) {
            leftHand = equipable;
            inventory.remove(equipable);
            equipable.onEquip(this);
            System.out.println(name + " equiped in left hand " + equipable);

        } else if (Weapon.class.isAssignableFrom(equipable.getClass()) && rightHand == null) {
            rightHand = equipable;
            inventory.remove(equipable);
            equipable.onEquip(this);
            System.out.println(name + " equiped in right hand " + equipable);
        }
    }

    @Override
    public Equipable getLeftHandItem() {
        return leftHand;
    }

    @Override
    public Equipable getRightHandItem() {
        return rightHand;
    }

    @Override
    public Activity getCurrentActivity() {
        return activityStack.getCurrent().getCurrentActivity();
    }

    @Override
    public int getHp() {
        return actualHp;
    }

    @Override
    public int getMaxHp() {
        return (getAttribute(Attributes.Endurance) + getAttribute(Attributes.Strength) / 2 + hpModifier);
    }

    @Override
    public void setHp(int value) {
        actualHp = value;
    }

    @Override
    public List<Weapon> getWeapons() {
        List<Weapon> result = new ArrayList<>();
        if(leftHand != null && Weapon.class.isAssignableFrom(leftHand.getClass()))
            result.add(((Weapon)leftHand));
        if(rightHand != null && Weapon.class.isAssignableFrom(rightHand.getClass()))
            result.add(((Weapon)rightHand));
        return result;
    }

    @Override
    public int getDefenseValue() {
        if(leftHand != null && Shield.class.isAssignableFrom(leftHand.getClass()))
            return ((Shield)leftHand).getDefense();
        return 0;
    }

    @Override
    public Armor getWornArmor() {
        return wornArmor;
    }

    @Override
    public void setWornArmor(Armor armor) {
        this.wornArmor = armor;
    }

    @Override
    public void setLeftHandItem(Equipable equipable) {
        this.leftHand = equipable;
    }

    @Override
    public void setRightHandItem(Equipable equipable) {
        this.rightHand = equipable;
    }

    @Override
    public void die() {
        System.out.println("I'm dead." + getName());
        activityStack.getCurrent().cancel();
        ActorDeathHandler.INSTANCE.kill(this);
        LightSource lightSource = LightSourceRegistry.INSTANCE.getFor(this);
        LightSourceRegistry.INSTANCE.remove(getCurrentMap(), lightSource);
        LightSourceRegistry.INSTANCE.remove(this);
    }

    @Override
    public int getAttackRange() {
        return getRightHandItem() == null ? 1 : ((Weapon)getRightHandItem()).getRange();
    }

    @Override
    public int getVisibilityRange() {
        return 6;
    }

    @Override
    public Map<WeaponSkill, Integer> getWeaponSkills() {
        return weaponSkills;
    }

    @Override
    public Map<MagicSkill, Integer> getMagicSkills() {
        return magicSkills;
    }

    @Override
    public void setAttribute(Attributes attribute, int value) {
        this.baseAttributes.put(attribute, value);
    }
}
