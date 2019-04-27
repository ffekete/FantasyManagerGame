package com.mygdx.game.logic.visibility;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.Tile;
import com.mygdx.game.faction.Alignment;

import java.util.HashSet;
import java.util.Set;

public class VisibilityMask {

    private static final int DEFAULT_VALE = 0;

    private final int width;
    private final int height;

    private Set<Actor>[][] mask;

    public VisibilityMask(int width, int height) {
        this.width = width;
        this.height = height;
        mask = new HashSet[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                mask[i][j] = new HashSet<>();
            }
        }
    }

    public Tile[][] mask(Map2D map, VisitedArea[][] visitedAreaMap) {
        if (width != map.getWidth() || height != map.getHeight())
            throw new IllegalArgumentException("Map sizes are not matching with mask!");
        Tile[][] newMap = new Tile[map.getWidth()][map.getHeight()];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(!mask[i][j].isEmpty() && mask[i][j].stream().noneMatch(visitor -> visitor.getAlignment().equals(Alignment.FRIENDLY))) {
                    if (visitedAreaMap[i][j] == VisitedArea.NOT_VISITED) {
                        newMap[i][j] = Tile.EMPTY;
                    } else {
                        visitedAreaMap[i][j] = VisitedArea.VISITED_BUT_NOT_VISIBLE; // visited but not seen
                        newMap[i][j] = map.getTile(i, j);
                    }
                }
                else if (mask[i][j].isEmpty()) {
                    if (visitedAreaMap[i][j] == VisitedArea.NOT_VISITED) {
                        newMap[i][j] = Tile.EMPTY;
                    } else {
                        visitedAreaMap[i][j] = VisitedArea.VISITED_BUT_NOT_VISIBLE; // visited but not seen
                        newMap[i][j] = map.getTile(i, j);
                    }
                } else {
                    visitedAreaMap[i][j] = VisitedArea.VISIBLE; // visited and seen
                    newMap[i][j] = map.getTile(i, j);

                }
            }
        }

        return newMap;
    }


    public void setValue(int x, int y, Actor value) {
        mask[x][y].add(value);
    }

    public void setAllValue(int x, int y, Set<Actor> values) {
        mask[x][y].addAll(values);
    }

    public Set<Actor> getValue(int x, int y) {
        return mask[x][y];
    }
}
