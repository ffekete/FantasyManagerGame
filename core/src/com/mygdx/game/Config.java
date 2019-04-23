package com.mygdx.game;

public class Config {
    public static final int WORLD_WIDTH = 1000;
    public static final int WORLD_HEIGHT = 1000;

    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;

    public static final int BASE_HUNGER_LEVEL = 0;
    public static final int BASE_HUNGER_LIMIT = 1000;

    public static class Engine {
        public static final int NUMBER_OF_THREADS = 4;
    }

    public static class Item {
        public static final int BREAD_HUNGER_LEVEL = 100;
        public static final int PICK_UP_ITEM_DISTANCE = 15;
    }

    public static class Dungeon {
        public static final int DUNGEON_WIDTH = 100;
        public static final int DUNGEON_HEIGHT = 100;
    }


}
