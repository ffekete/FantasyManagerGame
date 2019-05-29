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

    Point getDefaultSpawnPoint();
    void setDefaultSpawningPoint(Point point);

    MapType getMapType();

    int getTileVariation(int x, int y);

    enum MapType {
        DUNGEON_CAVE,
        DUNGEON_ROOMS,
        WORLD_MAP;
    }
}
