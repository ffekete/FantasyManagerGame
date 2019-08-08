package com.mygdx.game.map;

import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.visibility.VisibilityCalculator;
import com.mygdx.game.logic.visibility.VisitedArea;

public interface Map2D {

    TileBase getTile(int x, int y);
    void setTile(int x, int y, TileBase value);
    int getHeight();
    int getWidth();
    VisitedArea[][] getVisitedareaMap();
    void setVisitedAreaMap(VisitedArea[][] map);

    VisibilityCalculator getVisibilityCalculator();

    boolean isExplored();
    boolean areAllLevelsExplored();

    Point getDefaultSpawnPoint();
    void setDefaultSpawningPoint(Point point);

    MapType getMapType();

    boolean isObstacle(int x, int y);
    void setObstacle(int x, int y, boolean value);

    int getTileVariation(int x, int y);

    int getTraverseCost(int x, int y);

    void setTraverseCost(int x, int y, int val);

    enum MapType {
        DUNGEON_CAVE,
        DUNGEON_ROOMS,
        WORLD_MAP;
    }
}
