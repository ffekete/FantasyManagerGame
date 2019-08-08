package com.mygdx.game.actor;

import com.mygdx.game.Config;
import com.mygdx.game.actor.component.attribute.Attributes;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.Skill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.component.trait.Trait;
import com.mygdx.game.actor.component.trait.TraitList;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.effect.AttackSpeedReduction;
import com.mygdx.game.effect.Effect;
import com.mygdx.game.effect.MovementSpeedReduction;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.spelltome.SpellTome;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.stack.ActivityStack;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;
import com.mygdx.game.rules.levelup.LevelUpController;

import java.util.*;

public abstract class AbstractActor implements Actor {

    private String name;
    private Inventory inventory;
    private Map<Attributes, Integer> baseAttributes;
    private Map<WeaponSkill, Integer> weaponSkills;
    private Map<MagicSkill, Integer> magicSkills;
    private EffectRegistry effectRegistry = EffectRegistry.INSTANCE;
    private LevelUpController levelUpController = new LevelUpController();
    private TraitList traitList;

    private Point coordinates;
    private int hungerLevel;
    private Alignment alignment;

    private float xOffset = 0;
    private float yOffset = 0;

    private int actualHp;
    private int hpModifier = 3;

    private int actualMana;

    private Equipable leftHand = null;
    private Equipable rightHand = null;

    private ActivityStack activityStack = new ActivityStack(new PriorityQueue<>(), this);

    private Map2D currentMap;

    private Armor wornArmor = null;

    private SpellTome spellTome;

    private long experiencePoints = 0L;
    private int level = 1;

    private List<Skill> skillFocusDefinition;

    private int unspentSkillPoints = 0;

    private int money;

    public AbstractActor() {
        this.traitList = new TraitList();
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

        // remove first from actorGrid
        if(coordinates != null) {
            ActorRegistry.INSTANCE.getActorGrid().computeIfAbsent(currentMap, value -> new Actor[currentMap.getWidth()][currentMap.getHeight()]);
            ActorRegistry.INSTANCE.getActorGrid().remove(currentMap)[getX()][getY()] = null;
        }

        this.coordinates = point;

        // this is ugly, but ensures that actorGrid is updated when actor moves
        ActorRegistry.INSTANCE.getActorGrid().computeIfAbsent(currentMap, value -> new Actor[currentMap.getWidth()][currentMap.getHeight()]);
        ActorRegistry.INSTANCE.getActorGrid().get(currentMap)[getX()][getY()] = this;
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
        int baseSpeed = 40 - getAttribute(Attributes.Dexterity);

        int modifier = effectRegistry.getAll(this).stream().filter(effect -> MovementSpeedReduction.class.equals(effect.getClass())).map(Effect::getPower).reduce(0, (a, b) -> a+b);

        return baseSpeed - modifier;

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
        if (Shield.class.isAssignableFrom(equipable.getClass())) {
            leftHand = equipable;
            inventory.remove(equipable);
            equipable.onEquip(this);
            System.out.println(name + " equiped in left hand " + equipable);

        } else if (Weapon.class.isAssignableFrom(equipable.getClass())) {
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
    public void die(Actor killer) {
        System.out.println(getName() + " I'm killed by " + killer.getName());
        activityStack.getCurrent().cancel();
        ActorDeathHandler.INSTANCE.kill(this);
        if(this.equals(CameraPositionController.INSTANCE.getFocusedOn())) {
            CameraPositionController.INSTANCE.removeFocus();
        }
        levelUpController.calculate(killer, this);
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

    @Override
    public int getMana() {
        return actualMana;
    }

    @Override
    public void setMana(int value) {
        actualMana = value;
    }

    @Override
    public int getMaxMana() {
        return (this.getAttribute(Attributes.Wisdom) + this.getAttribute(Attributes.Intelligence) / 2);
    }

    @Override
    public SpellTome getSpellTome() {
        return spellTome;
    }

    @Override
    public void setSpellTome(SpellTome spellTome) {
        this.spellTome = spellTome;
    }

    @Override
    public void addExperiencePoints(long value) {
        this.experiencePoints += value;
    }

    @Override
    public long getExperiencePoints() {
        return experiencePoints;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public List<Skill> getSkillFocusDefinition() {
        return skillFocusDefinition;
    }

    @Override
    public void setSkillFocusDefinition(List<Skill> skills) {
        this.skillFocusDefinition = skills;
    }

    @Override
    public int getSkillLevel(Skill skill) {
        return WeaponSkill.class.isAssignableFrom(skill.getClass()) ? getWeaponSkills().get(skill) : getMagicSkills().get(skill);
    }

    @Override
    public void setUnspentSkillPoints(int points) {
        this.unspentSkillPoints = points;
    }

    @Override
    public int getUnspentSkillPoints() {
        return this.unspentSkillPoints;
    }

    @Override
    public void increaseSkillLevel(Skill skill) {
        if(WeaponSkill.class.isAssignableFrom(skill.getClass())) {
            int actualLevel = getWeaponSkills().get(skill);
            getWeaponSkills().put((WeaponSkill) skill, actualLevel + 1);
        } else {
            int actualLevel = getMagicSkills().get(skill);
            getMagicSkills().put((MagicSkill) skill, actualLevel + 1);
        }
    }

    @Override
    public int getMoney() {
        return money;
    }

    @Override
    public void addMoney(int amount) {
        this.money += amount;
        System.out.println("Received " + amount + " of money, new amount: " + money);
    }

    @Override
    public boolean hasTrait(Trait trait) {
        return traitList.has(trait);
    }

    @Override
    public void addTrait(Trait trait) {
        traitList.add(trait);
    }
}
