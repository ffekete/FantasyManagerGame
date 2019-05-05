package com.mygdx.game.creator.map;

import com.mygdx.game.creator.TileBase;
import com.mygdx.game.creator.map.dungeon.Tile;
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
}
