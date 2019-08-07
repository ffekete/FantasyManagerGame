package com.mygdx.game;

public class Config {

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    public static final int BASE_HUNGER_LEVEL = 0;
    public static final int BASE_HUNGER_LIMIT = 1000;
    public static final boolean SHOW_ELAPSED_TIME = false;
    public static final boolean SHOW_ELAPSED_TIME_IN_RENDERER = false;

    public static class Rules {
        public static final int MANA_REGENERATION_PERIOD = 150;
        public static final int HP_REGENERATION_PERIOD = 300;
        public static final int SPELL_CAST_FREQUENCY = 120;
        public static final double FOLLOW_DISTANCE = 15.d;
    }

    public static class Engine {
        public static final int NUMBER_OF_THREADS = 4;
        public static final boolean ENABLE_8_WAYS_PATHFINDING = false;
        public static final float ACTOR_HEIGHT = 0.7f;
    }

    public static final int ATTACK_DISTANCE = 15;

    public static class WorldMap {
        public static final int WORLD_WIDTH = 2000;
        public static final int WORLD_HEIGHT = 2000;
        public static final int CLUSTER_DIVIDER = 10;
    }

    public static class Actor {
        public static final int LOW_HP_THRESHOLD_DIVIDER = 3;
        public static final int LOW_MANA_THRESHOLD_DIVIDER = 3;
        public static final int MAX_STARTER_SKILL_LEVEL = 2;
        public static final String LIGHT_SOURCE_COLOR = "FF7733";
    }

    public static class Time {
        public static final int TICK_PER_HOUR = 1000;
        public static final int HOUR_PER_DAY = 24;
    }

    public static class Item {
        public static final int BREAD_HUNGER_LEVEL = 100;
        public static final int PICK_UP_ITEM_DISTANCE = 15;
        public static final int HEALING_POTION_STRENGTH = 20;
        public static final int MANA_POTION_STRENGTH = 20;

        // power lists
        public static final int BLACK_PLATE_MAIL_POWER = 10;
        public static final int MEDIUM_SHIELD_POWER = 6;
        public static final int SMALL_SHIELD_POWER = 5;
        public static final int FLAME_TONGUE_POWER = 10;
        public static final int POISON_FANG_POWER = 10;
        public static final int SHORT_SWORD_POWER = 5;
        public static final int SHORT_SWORD_PLUS_ONE_POWER = 6;
        public static final int SHORT_SWORD_PLUS_FOUR_POWER = 9;
        public static final int JADE_STAFF_POWER = 4;
        public static final int LONGBOW_POWER = 6;
    }

    public static class Spell {
        public final static int FIREBALL_RANGE = 3;
        public final static int FIREBALL_MANA_COST = 5;

        public final static int FIREBOLT_MANA_COST = 3;
        public static final int SLOW_MANA_COST = 2;

        public final static int FIREBALL_STRENGTH = 8;
        public final static int FIREBOLT_STRENGTH = 3;
        public static final int SLOW_STRENGTH = 4;
        public static final int SLOW_MOVEMENT_SPEED_REDUCE_AMOUNT = -60;
        public static final int SLOW_ATTACK_SPEED_REDUCE_AMOUNT = -30;

        public static final int POISON_CLOUD_RANGE = 3;
        public static final int POISON_CLOUD_STRENGTH = 10;
        public static int POISON_CLOUD_MANA_COST = 7;
    }

    public static class Screen {
        public static final int WIDTH = 1920;
        public static final int HEIGHT = 1200;
        public static final int CELL_WIDTH = 200;
        public static final int CANVAS_WIDTH = WIDTH - CELL_WIDTH;
    }

    public static class Activity {

        public static final int HEALING_POTION_CONSUME_PRIORITY = 7;
        public static final int MANA_POTION_CONSUME_PRIORITY = 8;
        public static final int ANTIVENOM_POTION_CONSUME_PRIORITY = 9;
        public static final int EQUIP_PRIORITY = 10;
        public static final int RANGED_ATTACK_PRIORITY = 89;
        public static final int OFFENSIVE_SPELL_CAST_PRIORITY = 90;
        public static final int ATTACK_PRIORITY = 91;
        public static final int MOVE_THEN_ATTACK_PRIORITY = 92;
        public static final int OPEN_CHEST_PRIORITY = 93;
        public static final int MOVE_PICKUP_PRIORITY = 94;
        public static final int PICKUP_PRIORITY = 95;
        public static final int MOVE_PICKUP_EAT_PRIORITY = 96;
        public static final int EAT_PRIORITY = 97;
        public static final int BUILD_PRIORITY = 98;
        public static final int INTERACT_PRIORITY = 98;
        public static final int SUPPORT_PRIORITY = 99;
        public static final int EXPLORATION_PRIORITY = 100;
        public static final int WAIT_PRIORITY = 101;
        public static final int MOVEMENT_PRIORITY = 120;
        public static final int IDLE_PRIORITY = 140;
    }

    public static class Dungeon {
        public static final int MULTILEVEL_DUNGEON_MAX_SIZE = 10;
        public static final int MULTILEVEL_DUNGEON_MIN_SIZE = 6;

        public static final int VISITED_AREA_THRESHOLD = 20;
        public static final int DUNGEON_WIDTH = 40;
        public static final int DUNGEON_HEIGHT = 40;

        public static final int ROOMS_DUNGEON_WIDTH = 200;
        public static final int ROOMS_DUNGEON_HEIGHT = 200;

        public static final int TILE_VARIATION_FREQUENCY = 20;
        public static final int WORLD_OBJECT_SPAWN_RATE = 10;
        public static final int CHEST_SPAWN_RATE = 90;
    }


    public static class Object {
        public static final float WOODEN_WALL_WORLD_MAP_SIZE = 1f;
    }
}
