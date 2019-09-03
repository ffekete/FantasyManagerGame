package com.mygdx.game.logic.visibility;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.*;
import java.util.stream.Collectors;

public class VisibilityCalculator {

    public final int width;
    public final int height;

    private final VisibilityMapRegistry visibilityMapRegistry = VisibilityMapRegistry.INSTANCE;


    // for performance tuning
    private VisibilityMask.ChangedArea changedArea;

    public VisibilityCalculator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public VisibilityMask generateMask(Map2D map, List<Actor> points) {
        VisibilityMask mask = visibilityMapRegistry.getFor(map);
        if (mask == null) {
            mask = new VisibilityMask(width, height);
        } else {

        }

        for (Actor actor : points) {
            long s0 = System.currentTimeMillis();
            mask.reset(actor); // clear changed area
            //System.out.println("s0: " + (System.currentTimeMillis() - s0));

            long s1 = System.currentTimeMillis();
            calculateFor(actor, actor.getVisibilityRange(), mask, map); // add new changed areas
            //System.out.println("s1: " + (System.currentTimeMillis() - s1));
        }

        long s2 = System.currentTimeMillis();
        //refine(mask);
        //System.out.println("s2: " + (System.currentTimeMillis() - s2));

        return mask;
    }

    private List<Integer[]> midPointCircleDraw(int x_centre,
                                               int y_centre, int r) {

        List<Integer[]> points = new ArrayList<>();

        int x = r, y = 0;

        // Printing the initial point
        // on the axes after translation
        points.add(new Integer[]{x_centre - r, y_centre});
        points.add(new Integer[]{x_centre, y_centre - r});
        // When radius is zero only a single
        // point will be printed
        if (r > 0) {

            points.add(new Integer[]{x + x_centre, -y + y_centre});
            points.add(new Integer[]{y + x_centre, x + y_centre});
            points.add(new Integer[]{-y + x_centre, x + y_centre});
        }

        // Initialising the value of P
        int P = 1 - r;
        while (x > y) {

            y++;

            // Mid-point is inside or on the perimeter
            if (P <= 0)
                P = P + 2 * y + 1;

                // Mid-point is outside the perimeter
            else {
                x--;
                P = P + 2 * y - 2 * x + 1;
            }

            // All the perimeter points have already
            // been printed
            if (x < y)
                break;

            // Printing the generated point and its
            // reflection in the other octants after
            // translation
            points.add(new Integer[]{x + x_centre, y + y_centre});
            points.add(new Integer[]{-x + x_centre, y + y_centre});
            points.add(new Integer[]{x + x_centre, -y + y_centre});
            points.add(new Integer[]{-x + x_centre, -y + y_centre});

            // If the generated point is on the
            // line x = y then the perimeter points
            // have already been printed
            if (x != y) {
                points.add(new Integer[]{y + x_centre, x + y_centre});
                points.add(new Integer[]{-y + x_centre, x + y_centre});
                points.add(new Integer[]{y + x_centre, -x + y_centre});
                points.add(new Integer[]{-y + x_centre, -x + y_centre});
            }

        }
        return points;
    }

    private void line(int x, int y, int x2, int y2, VisibilityMask visibilityMask, Map2D map, Actor value) {
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) dx1 = -1;
        else if (w > 0) dx1 = 1;
        if (h < 0) dy1 = -1;
        else if (h > 0) dy1 = 1;
        if (w < 0) dx2 = -1;
        else if (w > 0) dx2 = 1;
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) dy2 = -1;
            else if (h > 0) dy2 = 1;
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {


            x = Math.max(x, 0);
            y = Math.max(y, 0);
            x = Math.min(x, width - 1);
            y = Math.min(y, height - 1);

            visibilityMask.setValue(x, y, value);

            if (x < map.getWidth() - 1 && !(map.isObstacle(x + 1, y) || map.getTile(x + 1, y).isObstacle())) {
                visibilityMask.setValue(x + 1, y, value);
            }

            if (x > 0 && !(map.isObstacle(x - 1, y) || map.getTile(x - 1, y).isObstacle())) {
                visibilityMask.setValue(x - 1, y, value);
            }

            if (y + 1 < map.getHeight() && !(map.isObstacle(x, y + 1) || map.getTile(x, y + 1).isObstacle())) {
                visibilityMask.setValue(x, y + 1, value);
            }

            if (y - 1 >= 0 && !(map.isObstacle(x, y - 1) || map.getTile(x, y - 1).isObstacle())) {
                visibilityMask.setValue(x, y - 1, value);
            }

            if ((x - 1 >= 0 && y - 1 >= 0) && !(map.isObstacle(x - 1, y - 1) || map.getTile(x - 1, y - 1).isObstacle())) {
                visibilityMask.setValue(x - 1, y - 1, value);
            }

            if ((y + 1 < map.getHeight() && x - 1 >= 0) && !(map.isObstacle(x - 1, y + 1) || map.getTile(x - 1, y + 1).isObstacle())) {
                visibilityMask.setValue(x - 1, y + 1, value);
            }


            if ((x + 1 < map.getWidth() && y - 1 >= 0) && !(map.isObstacle(x + 1, y - 1) || map.getTile(x + 1, y - 1).isObstacle())) {
                visibilityMask.setValue(x + 1, y - 1, value);
            }


            if (map.isObstacle(x, y) || map.getTile(x, y).isObstacle()) break;


            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
    }

    private void line(int x, int y, int x2, int y2, Map2D map, boolean canBeBlocked) {
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) dx1 = -1;
        else if (w > 0) dx1 = 1;
        if (h < 0) dy1 = -1;
        else if (h > 0) dy1 = 1;
        if (w < 0) dx2 = -1;
        else if (w > 0) dx2 = 1;
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) dy2 = -1;
            else if (h > 0) dy2 = 1;
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {


            x = Math.max(x, 0);
            y = Math.max(y, 0);
            x = Math.min(x, width - 1);
            y = Math.min(y, height - 1);

            map.getVisitedareaMap()[x][y] = VisitedArea.VISITED_BUT_NOT_VISIBLE;

            if (x < map.getWidth() - 1 && !(map.isObstacle(x + 1, y) || map.getTile(x + 1, y).isObstacle())) {
                map.getVisitedareaMap()[x + 1][y] = VisitedArea.VISITED_BUT_NOT_VISIBLE;
            }

            if (x > 0 && !(map.isObstacle(x - 1, y) || map.getTile(x - 1, y).isObstacle())) {
                map.getVisitedareaMap()[x - 1][y] = VisitedArea.VISITED_BUT_NOT_VISIBLE;
            }

            if (y + 1 < map.getHeight() && !(map.isObstacle(x, y + 1) || map.getTile(x, y + 1).isObstacle())) {
                map.getVisitedareaMap()[x][y + 1] = VisitedArea.VISITED_BUT_NOT_VISIBLE;
            }

            if (y - 1 >= 0 && !(map.isObstacle(x, y - 1) || map.getTile(x, y - 1).isObstacle())) {
                map.getVisitedareaMap()[x][y - 1] = VisitedArea.VISITED_BUT_NOT_VISIBLE;
            }

            if ((x - 1 >= 0 && y - 1 >= 0) && !(map.isObstacle(x - 1, y - 1) || map.getTile(x - 1, y - 1).isObstacle())) {
                map.getVisitedareaMap()[x - 1][y - 1] = VisitedArea.VISITED_BUT_NOT_VISIBLE;
            }

            if ((y + 1 < map.getHeight() && x - 1 >= 0) && !(map.isObstacle(x - 1, y + 1) || map.getTile(x - 1, y + 1).isObstacle())) {
                map.getVisitedareaMap()[x - 1][y + 1] = VisitedArea.VISITED_BUT_NOT_VISIBLE;
            }


            if ((x + 1 < map.getWidth() && y - 1 >= 0) && !(map.isObstacle(x + 1, y - 1) || map.getTile(x + 1, y - 1).isObstacle())) {
                map.getVisitedareaMap()[x + 1][y - 1] = VisitedArea.VISITED_BUT_NOT_VISIBLE;
            }

            if (canBeBlocked && (map.isObstacle(x, y) || map.getTile(x, y).isObstacle())) break;


            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
    }

    public void calculateFor(Point p, int range, Map2D map, boolean canBeBlocked) {

        List<Integer[]> points = midPointCircleDraw(p.getX(), p.getY(), range);

        for (Integer[] point : points) {
            line(p.getX(), p.getY(), point[0], point[1], map, canBeBlocked);
        }
    }

    private void calculateFor(Actor actor, int range, VisibilityMask visibilityMask, Map2D map) {

        visibilityMask.addChangedArea(actor.getX() - range - 2, actor.getY() - range - 2, actor.getX() + range + 2, actor.getY() + range + 2, actor);

        List<Integer[]> points = midPointCircleDraw(actor.getX(), actor.getY(), range);

        for (Integer[] point : points) {
            line(actor.getX(), actor.getY(), point[0], point[1], visibilityMask, map, actor);
        }
    }
}
