package com.mygdx.game.creator.dungeon;

import com.mygdx.game.Config;

import java.util.Random;

public class Cave2DungeonCreator implements DungeonCreator{

    private static final int CHANCE_TO_SPAWN_CELL = 52;
    private static final int CELL_SPAWN_THRESHOLD = 4;
    private static final int CELL_DEATH_THRESHOLD = 4;
    private static final int STEPS = 3;
    private static final float PERCENTAGE_THRESHOLD = 0.0f;

    //private int[][] dungeon;

    public int[][] create() {
        float percentage;

        long start = System.currentTimeMillis();


        int[][] dungeon;
        do {
            dungeon = new int[Config.DungeonConfig.DUNGEON_WIDTH][Config.DungeonConfig.DUNGEON_HEIGHT];
            percentage = generate(dungeon);

        } while (percentage < PERCENTAGE_THRESHOLD);

        System.out.println(percentage);
        System.out.println("Elapsed: " + (System.currentTimeMillis() - start) + " ms");
        return dungeon;
    }

    private void init(int x, int y, int[][] dungeon) {
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                dig(i,j,dungeon);
            }
        }
    }

    private void dig(int i, int j, int[][] dungeon) {
        dungeon[i][j] = new Random().nextInt(100) < CHANCE_TO_SPAWN_CELL ? 1 : 0;
    }

    private void evolve(int[][] dungeon) {
        int[][] newMap = new int[dungeon.length][dungeon[0].length];
        for(int i = 0; i < dungeon.length; i++) {
            for(int j = 0; j < dungeon[0].length; j++) {

                if(dungeon[i][j] == 0) {
                    if (calculate(i, j, dungeon) <= CELL_SPAWN_THRESHOLD) {
                        newMap[i][j] = 1;
                    } else {
                        newMap[i][j] = 0;
                    }
                } else {
                    if(calculate(i,j,dungeon) >= CELL_DEATH_THRESHOLD) {
                        newMap[i][j] = 0;
                    } else {
                        newMap[i][j] = 1;
                    }
                }
            }
        }
        System.arraycopy(newMap, 0, dungeon, 0, newMap.length);
        //return newMap;
    }

    private int calculate(int x, int y, int[][]map) {
        int sum = 0;

        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if(i == 0 && j == 0) continue;
                int checkX = x + i;
                int checkY = y + j;

                if(checkX < 0 || checkY < 0 || checkX >= map.length || checkY >= map[0].length) {
                    sum+=1;
                    continue;
                }

                sum+= map[checkX][checkY];
            }
        }
        return sum;
    }

    private int[] findFirstFreeSpace(int[][] dungeon) {
        for(int i = 0; i < dungeon.length; i++) {
            for(int j = 0; j < dungeon[0].length; j++) {
                if(dungeon[i][j] == 0 && calculate(i,j,dungeon) == 0) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    private float generate(int[][] dungeon) {
        init(Config.DungeonConfig.DUNGEON_WIDTH, Config.DungeonConfig.DUNGEON_HEIGHT, dungeon);
        //draw();
        for(int i = 0; i < STEPS; i++) {
            evolve(dungeon);
        }

        int[] pos = findFirstFreeSpace(dungeon);

        int totalGaps = 0;
        for(int i = 0; i < dungeon.length; i++) {
            for(int j = 0; j < dungeon[0].length; j++) {
                if(dungeon[i][j] == 0 ) totalGaps ++;
            }
        }

        if (pos == null) {
            return 0.0f;
        }
        fill(pos[0], pos[1], dungeon);

        int count = 0;
        for(int i = 0; i < dungeon.length; i++) {
            for(int j = 0; j < dungeon[0].length; j++) {
                if(dungeon[i][j] == 2 ) count ++;
            }
        }

        return (float)count / totalGaps * 100;
    }

    private void fill(int x, int y, int[][] dungeon) {
        if(x >= dungeon.length || y >= dungeon[0].length || x < 0 || y < 0 || dungeon[x][y] == 1 || dungeon[x][y] == 2) {
            return;
        }

        dungeon[x][y] = 2;

        fill(x-1, y, dungeon);
        fill(x-1, y-1, dungeon);
        fill(x-1, y+1, dungeon);
        fill(x, y-1, dungeon);
        fill(x, y+1, dungeon);
        fill(x+1, y-1, dungeon);
        fill(x+1, y, dungeon);
        fill(x+1, y+1, dungeon);
    }

}
