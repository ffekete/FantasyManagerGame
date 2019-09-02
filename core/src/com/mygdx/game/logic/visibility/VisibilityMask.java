package com.mygdx.game.logic.visibility;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.faction.Alignment;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.map.Map2D;

import java.util.*;

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

    private final Map<Actor, List<ChangedArea>> changedAreas = new HashMap<>();

    public Map<Actor, List<ChangedArea>> getChangedAreas() {
        return changedAreas;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void reset(Actor actor) {
        changedAreas.computeIfAbsent(actor, v -> new ArrayList<>());
        for (ChangedArea area : changedAreas.get(actor)) {
            for (int i = Math.max(0, area.p1.getX()); i <= Math.min(area.p2.getX(), width - 1); i++) {
                for (int j = Math.max(0, area.p1.getY()); j <= Math.min(area.p2.getY(), height - 1); j++) {

                    //if(distance(area.actor.getCoordinates(), Point.of(i,j)) > 15) {
                    mask[i][j].remove(area.actor);
                    //}
                }
            }
        }
        changedAreas.get(actor).clear();
    }

    public void mask(Map2D map, VisitedArea[][] visitedAreaMap) {
        if (width != map.getWidth() || height != map.getHeight())
            throw new IllegalArgumentException("Map sizes are not matching with mask!");

        for (Actor actor : ActorMovementHandler.INSTANCE.getChangedCoordList()) {

            changedAreas.computeIfAbsent(actor, v -> new ArrayList<>());
            for (ChangedArea area : changedAreas.get(actor)) {
                for (int i = Math.max(0, area.p1.getX()); i <= Math.min(area.p2.getX(), width - 1); i++) {
                    for (int j = Math.max(0, area.p1.getY()); j <= Math.min(area.p2.getY(), height - 1); j++) {
                        if (!mask[i][j].isEmpty() && mask[i][j].stream().noneMatch(actor1 -> actor1.getAlignment().equals(Alignment.FRIENDLY))) {
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
        changedAreas.computeIfAbsent(actor, v -> new ArrayList<>());
        changedAreas.get(actor).add(new ChangedArea(new Point(x1, y1), new Point(x2, y2), actor));
    }
}
