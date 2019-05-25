package com.mygdx.game.actor;

import com.mygdx.game.actor.component.attribute.Attributes;
import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.inventory.Inventory;
import com.mygdx.game.item.Item;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.item.Equipable;
import com.mygdx.game.item.armor.Armor;
import com.mygdx.game.item.weapon.Weapon;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.Activity;
import com.mygdx.game.logic.activity.stack.ActivityStack;

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
    Point getCoordinates();
    boolean isHungry();
    void increaseHunger(int amount);
    void decreaseHunger(int amount);
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

    void equip(Equipable equipable);
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
    void die();
    int getAttackRange();
    void setAttribute(Attributes attribute, int value);


    // ******************   BodyPartsBasedActorAnimation  ********************
    Activity getCurrentActivity();

    int getVisibilityRange();

    Map<WeaponSkill, Integer> getWeaponSkills();
    Map<MagicSkill, Integer> getMagicSkills();
}
