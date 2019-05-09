package com.mygdx.game.logic.visibility;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.TileBase;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.Tile;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
import com.mygdx.game.utils.GdxUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

    class ChangedArea {
        Point p1;
        Point p2;
        Actor actor;

        public ChangedArea(Point p1, Point p2, Actor actor) {
            this.p1 = p1;
            this.p2 = p2;
            this.actor = actor;
        }
    }

    private final List<ChangedArea> changedAreas = new ArrayList<>();

    public List<ChangedArea> getChangedAreas() {
        return changedAreas;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void reset() {
        for (ChangedArea area : changedAreas) {
            for (int i = Math.max(0, area.p1.getX()); i <= Math.min(area.p2.getX(), width-1); i++) {
                for (int j = Math.max(0, area.p1.getY()); j <= Math.min(area.p2.getY(), height-1); j++) {

                    if(distance(area.actor.getCoordinates(), Point.of(i,j)) > 15) {
                        mask[i][j].remove(area.actor);
                    }
                }
            }
        }
        changedAreas.clear();
    }

    public void mask(Map2D map, VisitedArea[][] visitedAreaMap) {
        if (width != map.getWidth() || height != map.getHeight())
            throw new IllegalArgumentException("Map sizes are not matching with mask!");

        for (ChangedArea area : changedAreas) {
            for (int i = Math.max(0, area.p1.getX()); i <= Math.min(area.p2.getX(), width-1); i++) {
                for (int j = Math.max(0, area.p1.getY()); j <= Math.min(area.p2.getY(), height-1); j++) {
                    if (!mask[i][j].isEmpty() && mask[i][j].stream().noneMatch(actor -> actor.getAlignment().equals(Alignment.FRIENDLY))) {
                        if (visitedAreaMap[i][j] == VisitedArea.NOT_VISITED) {
                        } else {
                            visitedAreaMap[i][j] = VisitedArea.VISITED_BUT_NOT_VISIBLE; // visited but not seen
                        }
                    } else if (mask[i][j].isEmpty()) {
                        if (visitedAreaMap[i][j] == VisitedArea.NOT_VISITED) {
                        } else {
                            visitedAreaMap[i][j] = VisitedArea.VISITED_BUT_NOT_VISIBLE; // visited but not seen
                        }
                    } else {
                        visitedAreaMap[i][j] = VisitedArea.VISIBLE; // visited and seen
                    }
                }
            }
        }
    }


    private double distance(Point p1, Point p2) {
        if(p1 == null || p2 == null)
            return Double.MAX_VALUE;

        int a = Math.abs(p1.getX() - p2.getX());
        int b = Math.abs(p1.getY() - p2.getY());

        return Math.sqrt(a*a + b*b);
    }

    public void setValue(int x, int y, Actor actor) {
        mask[x][y].add(actor);
    }

    public void setAllValue(int x, int y, Set<Actor> values) {
        mask[x][y].addAll(values);
    }

    public Set<Actor> getValue(int x, int y) {
        return mask[x][y];
    }

    public void addChangedArea(int x1, int y1, int x2, int y2, Actor actor) {
        changedAreas.add(new ChangedArea(new Point(x1, y1), new Point(x2, y2), actor));
    }
}
