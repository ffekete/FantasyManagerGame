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
import com.mygdx.game.item.ItemFactory;
import com.mygdx.game.item.OneHandedItem;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.factory.Placement;
import com.mygdx.game.item.money.MoneyBag;
import com.mygdx.game.item.shield.Shield;
import com.mygdx.game.item.spelltome.SpellTome;
import com.mygdx.game.item.weapon.TwohandedWeapon;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.single.OffensiveSpellCastActivity;
import com.mygdx.game.logic.activity.single.RangedAttackActivity;
import com.mygdx.game.logic.activity.single.SimpleAttackActivity;
import com.mygdx.game.logic.activity.single.SleepActivity;
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
    private Appearance appearance;

    private Point coordinates;
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

    private final Map<Needs, Integer> needs = new HashMap<>();

    private Gender gender;

    public AbstractActor() {
        this.traitList = new TraitList();
        this.baseAttributes = new HashMap<>();
        this.weaponSkills = new HashMap<>();
        this.magicSkills = new HashMap<>();

        this.coordinates = new Point(0, 0);

        for (Attributes a : Attributes.values()) {
            baseAttributes.put(a, 0);
        }

        for (WeaponSkill s : WeaponSkill.values()) {
            weaponSkills.put(s, 0);
        }
        actualHp = getMaxHp();
        this.inventory = new Inventory();

        needs.put(Needs.Eat, Config.Rules.BASE_HUNGER_LEVEL);
        needs.put(Needs.Sleep, Config.Rules.BASE_SLEEPINESS_LEVEL);
        needs.put(Needs.Training, new Random().nextInt(100));
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
        if (coordinates != null) {
            ActorRegistry.INSTANCE.getActorGrid().computeIfAbsent(currentMap, value -> new Actor[currentMap.getWidth()][currentMap.getHeight()]);
            ActorRegistry.INSTANCE.getActorGrid().get(currentMap)[getX()][getY()] = null;
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
        return needs.get(Needs.Eat) >= Config.Rules.BASE_HUNGER_LIMIT * 0.75f;
    }

    @Override
    public void increaseHunger(int amount) {
        needs.put(Needs.Eat, Math.min(Config.Rules.BASE_HUNGER_LIMIT, needs.get(Needs.Eat) + amount));
    }

    @Override
    public void decreaseHunger(int amount) {
        needs.put(Needs.Eat, Math.max(0, needs.get(Needs.Eat) - amount));
    }

    @Override
    public int getHungerLevel() {
        return needs.get(Needs.Eat);
    }

    @Override
    public void pickUp(Item item) {
        item.pickedUp(this);
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

        int modifier = effectRegistry.getAll(this).stream().filter(effect -> MovementSpeedReduction.class.equals(effect.getClass())).map(Effect::getPower).reduce(0, (a, b) -> a + b);

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

        int modifier = effectRegistry.getAll(this).stream().filter(effect -> AttackSpeedReduction.class.equals(effect.getClass())).map(Effect::getPower).reduce(0, (a, b) -> a + b);

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
        if(equipable == null)
            return;
        // if it is shield
        if (Shield.class.isAssignableFrom(equipable.getClass())) {
            unequip(this.leftHand);
            leftHand = equipable;
            inventory.remove(equipable);
            equipable.onEquip(this);
            System.out.println(name + " equipped in left hand " + equipable);

        } else if (OneHandedItem.class.isAssignableFrom(equipable.getClass())) {
            unequip(this.rightHand);
            rightHand = equipable;
            inventory.remove(equipable);
            equipable.onEquip(this);
            System.out.println(name + " equipped in right hand " + equipable);
        } else if (TwohandedWeapon.class.isAssignableFrom(equipable.getClass())) {
            unequip(this.rightHand);
            unequip(this.leftHand);
            rightHand = equipable;
            equipable.onEquip(this);
            System.out.println(name + " equipped in both hands " + equipable);
        } else if (Armor.class.isAssignableFrom(equipable.getClass())) {
            unequip(this.getWornArmor());
            this.wornArmor = (Armor) equipable;
            equipable.onEquip(this);
            System.out.println(name + " equipped armor " + equipable);
        } else throw new RuntimeException("WTF?");
        inventory.remove(equipable);
    }

    @Override
    public void unequip(Equipable equipable) {
        if (equipable == null) {
            return;
        }

        if (Shield.class.isAssignableFrom(equipable.getClass())) {
            leftHand = null;
            inventory.add(equipable);
            equipable.onRemove(this);
            System.out.println(name + " unequipped in left hand " + equipable);

        } else if (Weapon.class.isAssignableFrom(equipable.getClass())) {
            rightHand = null;
            inventory.add(equipable);
            equipable.onRemove(this);
            System.out.println(name + " unequipped in right hand " + equipable);
        } else if (Armor.class.isAssignableFrom(equipable.getClass())) {
            this.wornArmor = null;
            inventory.add(equipable);
            equipable.onRemove(this);
            System.out.println(name + " unequipped armor " + equipable);
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
        if (leftHand != null && Weapon.class.isAssignableFrom(leftHand.getClass()))
            result.add(((Weapon) leftHand));
        if (rightHand != null && Weapon.class.isAssignableFrom(rightHand.getClass()))
            result.add(((Weapon) rightHand));
        return result;
    }

    @Override
    public int getDefenseValue() {
        if (leftHand != null && Shield.class.isAssignableFrom(leftHand.getClass()))
            return ((Shield) leftHand).getDefense();
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
        if (this.equals(CameraPositionController.INSTANCE.getFocusedOn())) {
            CameraPositionController.INSTANCE.removeFocus();
        }
        levelUpController.calculate(killer, this);
    }

    @Override
    public int getAttackRange() {
        return getRightHandItem() == null ? 1 : ((Weapon) getRightHandItem()).getRange();
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
        if (WeaponSkill.class.isAssignableFrom(skill.getClass())) {
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
    public void removeMoney(int amount) {
        this.money = Math.max(0, money - amount);
    }

    @Override
    public boolean hasTrait(Trait trait) {
        return traitList.has(trait);
    }

    @Override
    public void addTrait(Trait trait) {
        traitList.add(trait);
    }

    @Override
    public boolean isSleepy() {
        return needs.get(Needs.Sleep) >= Config.Rules.BASE_SLEEPINESS_LIMIT * 0.75f;
    }

    @Override
    public void increaseSleepiness(int amount) {
        needs.put(Needs.Sleep, Math.min(Config.Rules.BASE_SLEEPINESS_LIMIT, needs.get(Needs.Sleep) + amount));
    }

    @Override
    public void decreaseSleepiness(int amount) {
        needs.put(Needs.Sleep, Math.max(0, needs.get(Needs.Sleep) - amount));
    }

    @Override
    public int getSleepinessLevel() {
        return needs.get(Needs.Sleep);
    }

    @Override
    public boolean isSleeping() {
        return activityStack.contains(SleepActivity.class);
    }

    @Override
    public void increaseTrainingNeeds(int amount) {
        needs.put(Needs.Training, needs.get(Needs.Training) + amount);
    }

    @Override
    public int getTrainingNeeds() {
        return needs.get(Needs.Training);
    }

    @Override
    public boolean wantsTraining() {
        return needs.get(Needs.Training) >= Config.Rules.BASE_TRAINIG_LIMIT * 0.75;
    }

    @Override
    public void setCoordinates(int x, int y) {
        this.coordinates.update(x, y);
    }

    @Override
    public List<Item> drop() {
        List<Item> items = new ArrayList<>();
        if (this.getLeftHandItem() != null) {
            items.add(this.getLeftHandItem());
            this.unequip(this.getLeftHandItem());
        }

        if (this.getRightHandItem() != null) {
            items.add(this.getRightHandItem());
            this.unequip(this.getRightHandItem());
        }

        if (this.getWornArmor() != null) {
            items.add(this.getWornArmor());
            this.unequip(this.getWornArmor());
        }

        for (Item item : this.getInventory().getAll()) {
            items.add(item);
            item.setCoordinates(this.getCoordinates());
        }
        this.getInventory().clear();

        if (money > 0) {
            MoneyBag moneyBag = ItemFactory.INSTANCE.create(MoneyBag.class, currentMap, Placement.FIXED.X(getX()).Y(getY()));
            moneyBag.setAmount(money);
            items.add(moneyBag);
            money = 0;
        }

        return items;
    }

    @Override
    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    @Override
    public Appearance getAppearance() {
        return this.appearance;
    }

    @Override
    public boolean isAttacking() {
        return this.getActivityStack().contains(SimpleAttackActivity.class) ||
                this.getActivityStack().contains(RangedAttackActivity.class) ||
                this.getActivityStack().contains(OffensiveSpellCastActivity.class);
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
