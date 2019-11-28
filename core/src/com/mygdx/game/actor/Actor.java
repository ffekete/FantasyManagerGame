package com.mygdx.game.actor;

import com.mygdx.game.actor.component.attribute.Attributes;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.Skill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.component.trait.Trait;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.Item;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.spelltome.SpellTome;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.stack.ActivityStack;
import com.mygdx.game.map.Map2D;

import java.util.List;
import java.util.Map;

public interface Actor {

    int getX();
    int getY();

    ActivityStack getActivityStack();
    Map2D getCurrentMap();
    void setCurrentMap(Map2D map);

    int getMovementSpeed();
    void setCoordinates(Point point);
    void setCoordinates(int x, int y);
    Point getCoordinates();
    boolean isHungry();
    void increaseHunger(int amount);
    void decreaseHunger(int amount);
    boolean isSleepy();
    void increaseSleepiness(int amount);
    void decreaseSleepiness(int amount);
    int getSleepinessLevel();
    int getHungerLevel();
    void pickUp(Item item);
    Inventory getInventory();
    float getxOffset();
    float getyOffset();
    void setxOffset(float xOffset);
    void setyOffset(float yOffset);
    Alignment getAlignment();
    void setAlignment(Alignment alignment);
    int getAttackSpeed();
    String getName();
    void setName(String name);

    boolean isSleeping();
    void equip(Equipable equipable);
    void unequip(Equipable equipable);
    int getHp();
    int getMaxHp();
    void setHp(int value);
    List<Weapon> getWeapons();
    int getAttribute(Attributes attributes);
    Equipable getLeftHandItem();
    Equipable getRightHandItem();
    int getDefenseValue();
    Armor getWornArmor();
    void setWornArmor(Armor armor);
    void setLeftHandItem(Equipable equipable);
    void setRightHandItem(Equipable equipable);
    void die(Actor killer);
    int getAttackRange();
    void setAttribute(Attributes attribute, int value);
    int getMana();
    void setMana(int value);
    int getMaxMana();
    SpellTome getSpellTome();
    void setSpellTome(SpellTome spellTome);

    void addExperiencePoints(long value);
    long getExperiencePoints();

    List<Skill> getSkillFocusDefinition();
    void setSkillFocusDefinition(List<Skill> skills);

    int getSkillLevel(Skill skill);
    int getMoney();
    void addMoney(int amount);
    void removeMoney(int amount);

    boolean hasTrait(Trait trait);
    void addTrait(Trait trait);

    String getActorClass();

    // ******************   BodyPartsBasedActorAnimation  ********************
    Activity getCurrentActivity();

    int getVisibilityRange();

    Map<WeaponSkill, Integer> getWeaponSkills();
    Map<MagicSkill, Integer> getMagicSkills();

    int getLevel();
    void setLevel(int level);

    void setUnspentSkillPoints(int points);
    int getUnspentSkillPoints();

    void increaseSkillLevel(Skill skill);

    void increaseTrainingNeeds(int amount);
    int getTrainingNeeds();
    boolean wantsTraining();
    List<Item> drop();
    BodyType getBodyType();
    void setAppearance(Appearance appearance);
    Appearance getAppearance();
}
