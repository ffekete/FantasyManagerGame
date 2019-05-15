package com.mygdx.game;

public class Config {

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    public static final int BASE_HUNGER_LEVEL = 0;
    public static final int BASE_HUNGER_LIMIT = 1000;
    public static final boolean SHOW_ELAPSED_TIME = false;

    public static class Engine {
        public static final int NUMBER_OF_THREADS = 4;
        public static final boolean ENABLE_8_WAYS_PATHFINDING = false;
    }

    public static final int ATTACK_DISTANCE = 15;

    public static class WorldMap {
        public static final int WORLD_WIDTH = 2000;
        public static final int WORLD_HEIGHT = 2000;
        public static final int CLUSTER_DIVIDER = 10;
    }

    public static class Actor {
        public static final int LOW_HP_THRESHOLD_DIVIDER = 3;
    }

    public static class Time {
        public static final int TICK_PER_HOUR = 1000;
        public static final int HOUR_PER_DAY = 24;
    }

    public static class Item {
        public static final int BREAD_HUNGER_LEVEL = 100;
        public static final int PICK_UP_ITEM_DISTANCE = 15;
        public static final int HEALING_POTION_STRENGTH = 20;
    }

    public static class Activity {

        public static final int HEALING_POTION_CONSUME_PRIORITY = 10;
        public static final int EQUIP_PRIORITY = 10;
        public static final int ATTACK_PRIORITY = 96;
        public static final int MOVE_THEN_ATTACK_PRIORITY = 96;
        public static final int MOVE_PICKUP_PRIORITY = 97;
        public static final int PICKUP_PRIORITY = 97;
        public static final int MOVE_PICKUP_EAT_PRIORITY = 98;
        public static final int EAT_PRIORITY = 98;
        public static final int INTERACT_PRIORITY = 99;
        public static final int EXPLORATION_PRIORITY = 100;
        public static final int WAIT_PRIORITY = 101;
        public static final int MOVEMENT_PRIORITY = 120;
        public static final int IDLE_PRIORITY = 140;
    }

    public static class Dungeon {
        public static final int VISITED_AREA_THRESHOLD = 10;
        public static final int DUNGEON_WIDTH = 100;
        public static final int DUNGEON_HEIGHT = 100;
        public static final int TILE_VARIATION_FREQUENCY = 20;
    }


}
