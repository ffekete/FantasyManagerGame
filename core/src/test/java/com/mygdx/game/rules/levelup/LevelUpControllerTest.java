package com.mygdx.game.rules.levelup;

import com.mygdx.game.actor.component.skill.MagicSkill;
import com.mygdx.game.actor.component.skill.WeaponSkill;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.actor.monster.undead.Skeleton;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LevelUpControllerTest {

    private LevelUpController levelUpController = new LevelUpController();

    @Test
    public void testUnspentSkillPointIsEqualsToCost() {

        Warrior warrior = new Warrior();

        warrior.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));

        warrior.getWeaponSkills().put(WeaponSkill.Sword, 2);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 3);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Flail, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Mace, 1);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        warrior.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(warrior, skeleton);

        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Sword), is(2));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Dagger), is(3));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Warhammer), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Flail), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Mace), is(2));
        assertThat(warrior.getUnspentSkillPoints(), is(0));
        assertThat(warrior.getExperiencePoints(), is(2200L));
        assertThat(warrior.getLevel(), is(2));
    }

    @Test
    public void testUnspentSkillPointIsMoreThanToCost() {

        Warrior warrior = new Warrior();

        warrior.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));
        warrior.setUnspentSkillPoints(1);

        warrior.getWeaponSkills().put(WeaponSkill.Sword, 2);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 3);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Flail, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Mace, 1);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        warrior.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(warrior, skeleton);

        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Sword), is(2));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Dagger), is(4));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Warhammer), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Flail), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Mace), is(1));
        assertThat(warrior.getUnspentSkillPoints(), is(0));
        assertThat(warrior.getExperiencePoints(), is(2200L));
        assertThat(warrior.getLevel(), is(2));
    }

    @Test
    public void testUnspentSkillPointIsMoreThanToCost_LevelUpSkillTwice() {

        Warrior warrior = new Warrior();

        warrior.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));
        warrior.setUnspentSkillPoints(1);

        warrior.getWeaponSkills().put(WeaponSkill.Sword, 2);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Flail, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Mace, 1);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        warrior.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(warrior, skeleton);

        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Sword), is(2));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Dagger), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Warhammer), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Flail), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Mace), is(3));
        assertThat(warrior.getUnspentSkillPoints(), is(0));
        assertThat(warrior.getExperiencePoints(), is(2200L));
        assertThat(warrior.getLevel(), is(2));
    }

    @Test
    public void testNotEnoughSkillPointsToLevelUpFocusedSkills() {

        Warrior warrior = new Warrior();

        warrior.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));
        warrior.setUnspentSkillPoints(1);

        warrior.getWeaponSkills().put(WeaponSkill.Sword, 2);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 5);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Flail, 1);
        warrior.getWeaponSkills().put(WeaponSkill.Mace, 5);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        warrior.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(warrior, skeleton);

        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Sword), is(2));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Dagger), is(5));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Warhammer), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Flail), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Mace), is(5));
        assertThat(warrior.getUnspentSkillPoints(), is(2));
        assertThat(warrior.getExperiencePoints(), is(2200L));
        assertThat(warrior.getLevel(), is(2));
    }

    @Test
    public void testAllOnMax() {

        Warrior warrior = new Warrior();

        warrior.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));
        warrior.setUnspentSkillPoints(1);

        warrior.getWeaponSkills().put(WeaponSkill.Sword, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Flail, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Mace, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Staff, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Bow, 6);
        warrior.getWeaponSkills().put(WeaponSkill.TwoHandedSwords, 6);

        warrior.getMagicSkills().put(MagicSkill.AirMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.ColdMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.EarthMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.FireMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.ProtectiveMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.DarkMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.LifeMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.SummoningMagic, 6);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        warrior.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(warrior, skeleton);

        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Sword), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Dagger), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Warhammer), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Flail), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Mace), is(6));
        assertThat(warrior.getUnspentSkillPoints(), is(2));
        assertThat(warrior.getExperiencePoints(), is(2200L));
        assertThat(warrior.getLevel(), is(2));
    }

    @Test
    public void testThereIsOneToLevelUp_ZeroValue() {

        Warrior warrior = new Warrior();

        warrior.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));
        warrior.setUnspentSkillPoints(0);

        warrior.getWeaponSkills().put(WeaponSkill.Sword, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 0);
        warrior.getWeaponSkills().put(WeaponSkill.Flail, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Mace, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Staff, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Bow, 6);
        warrior.getWeaponSkills().put(WeaponSkill.TwoHandedSwords, 6);

        warrior.getMagicSkills().put(MagicSkill.AirMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.ColdMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.EarthMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.FireMagic, 5);
        warrior.getMagicSkills().put(MagicSkill.ProtectiveMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.DarkMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.LifeMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.SummoningMagic, 6);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        warrior.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(warrior, skeleton);

        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Sword), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Dagger), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Warhammer), is(1));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Flail), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Mace), is(6));

        assertThat(warrior.getMagicSkills().get(MagicSkill.FireMagic), is(5));
        assertThat(warrior.getUnspentSkillPoints(), is(0));
        assertThat(warrior.getExperiencePoints(), is(2200L));
        assertThat(warrior.getLevel(), is(2));
    }

    @Test
    public void testThereIsOneToLevelUp() {

        Warrior warrior = new Warrior();

        warrior.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));
        warrior.setUnspentSkillPoints(2);

        warrior.getWeaponSkills().put(WeaponSkill.Sword, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Dagger, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Warhammer, 5);
        warrior.getWeaponSkills().put(WeaponSkill.Flail, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Mace, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Staff, 6);
        warrior.getWeaponSkills().put(WeaponSkill.Bow, 6);
        warrior.getWeaponSkills().put(WeaponSkill.TwoHandedSwords, 6);

        warrior.getMagicSkills().put(MagicSkill.AirMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.ColdMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.EarthMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.FireMagic, 5);
        warrior.getMagicSkills().put(MagicSkill.ProtectiveMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.DarkMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.LifeMagic, 6);
        warrior.getMagicSkills().put(MagicSkill.SummoningMagic, 6);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        warrior.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(warrior, skeleton);

        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Sword), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Dagger), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Warhammer), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Flail), is(6));
        assertThat(warrior.getWeaponSkills().get(WeaponSkill.Mace), is(6));

        assertThat(warrior.getMagicSkills().get(MagicSkill.FireMagic), is(5));
        assertThat(warrior.getUnspentSkillPoints(), is(0));
        assertThat(warrior.getExperiencePoints(), is(2200L));
        assertThat(warrior.getLevel(), is(2));
    }

    @Test
    public void testThereIsOneToLevelUpForWizard() {

        Wizard wizard = new Wizard();

        wizard.setSkillFocusDefinition(Arrays.asList(WeaponSkill.Dagger, WeaponSkill.Mace));
        wizard.setUnspentSkillPoints(2);

        wizard.getWeaponSkills().put(WeaponSkill.Sword, 6);
        wizard.getWeaponSkills().put(WeaponSkill.Dagger, 6);
        wizard.getWeaponSkills().put(WeaponSkill.Warhammer, 5);
        wizard.getWeaponSkills().put(WeaponSkill.Flail, 6);
        wizard.getWeaponSkills().put(WeaponSkill.Mace, 6);
        wizard.getWeaponSkills().put(WeaponSkill.Staff, 6);
        wizard.getWeaponSkills().put(WeaponSkill.Bow, 6);
        wizard.getWeaponSkills().put(WeaponSkill.TwoHandedSwords, 6);

        wizard.getMagicSkills().put(MagicSkill.AirMagic, 6);
        wizard.getMagicSkills().put(MagicSkill.ColdMagic, 6);
        wizard.getMagicSkills().put(MagicSkill.EarthMagic, 6);
        wizard.getMagicSkills().put(MagicSkill.FireMagic, 5);
        wizard.getMagicSkills().put(MagicSkill.ProtectiveMagic, 6);
        wizard.getMagicSkills().put(MagicSkill.DarkMagic, 6);
        wizard.getMagicSkills().put(MagicSkill.LifeMagic, 6);
        wizard.getMagicSkills().put(MagicSkill.SummoningMagic, 6);

        //SkillFocusRegistry.INSTANCE.add(warrior, warrior.getSkillFocusDefinition());

        Skeleton skeleton = new Skeleton();

        wizard.addExperiencePoints(ExperienceLevelMapper.INSTANCE.getForLevel(2) - MonsterToExperienceMapper.INSTANCE.getFor(Skeleton.class));

        levelUpController.calculate(wizard, skeleton);

        assertThat(wizard.getWeaponSkills().get(WeaponSkill.Sword), is(6));
        assertThat(wizard.getWeaponSkills().get(WeaponSkill.Dagger), is(6));
        assertThat(wizard.getWeaponSkills().get(WeaponSkill.Warhammer), is(5));
        assertThat(wizard.getWeaponSkills().get(WeaponSkill.Flail), is(6));
        assertThat(wizard.getWeaponSkills().get(WeaponSkill.Mace), is(6));

        assertThat(wizard.getMagicSkills().get(MagicSkill.FireMagic), is(6));
        assertThat(wizard.getUnspentSkillPoints(), is(0));
        assertThat(wizard.getExperiencePoints(), is(2200L));
        assertThat(wizard.getLevel(), is(2));
    }
}