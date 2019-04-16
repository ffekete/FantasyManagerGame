package com.mygdx.game.creator.map;

import com.mygdx.game.Config;

import java.util.Random;

public class RandomMapGenerator implements MapGenerator {

    @Override
    public Integer[][] generate(int max) {
        Integer[][] map = new Integer[Config.WORLD_WIDTH][Config.WORLD_HEIGHT];

        for(int i = 0; i < Config.WORLD_WIDTH; i++)
            for (int j = 0; j < Config.WORLD_HEIGHT; j++) {
                map[i][j] = new Random().nextInt(max);
            }

        return map;
    }
}
