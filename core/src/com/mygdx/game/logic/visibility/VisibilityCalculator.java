package com.mygdx.game.logic.visibility;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.map.Map2D;
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
        if(mask == null) {
            mask = new VisibilityMask(width, height);
        } else {
            mask.reset();
        }

        for (Actor actor : points) {
            calculateFor(actor, actor.getVisibilityRange(), mask, map);
        }

        refine(mask);

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
            if (map.isObstacle(x,y) || map.getTile(x,y).isObstacle()) break;


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

    private void refine(VisibilityMask mask) {
        for(int k = 0; k <  mask.getChangedAreas().size(); k++) {
            changedArea = mask.getChangedAreas().get(k);
            for (int i = Math.max(1, changedArea.p1.getX()); i < Math.min(changedArea.p2.getX(), mask.getWidth()-1); i++) {
                for (int j = Math.max(1, changedArea.p1.getY()); j < Math.min(changedArea.p2.getY(), mask.getHeight()-1); j++) {
                    int a = mask.getValue(i - 1, j).size() > 0 ? 1 : 0;
                    int b = mask.getValue(i + 1, j).size() > 0 ? 1 : 0;
                    int c = mask.getValue(i, j - 1).size() > 0 ? 1 : 0;
                    int d = mask.getValue(i, j + 1).size() > 0 ? 1 : 0;
                    int sum = a + b + c + d;
                    if (((mask.getValue(i, j).isEmpty() && sum >= 3)
                            || (mask.getValue(i, j).size() < sum))) {

                        Map<Actor, Integer> actors = new HashMap<>();

                        for (Actor actor : mask.getValue(i - 1, j)) {
                            if (actors.containsKey(actor)) {
                                actors.put(actor, actors.get(actor) + 1);
                            } else {
                                actors.put(actor, 1);
                            }
                        }

                        for (Actor actor : mask.getValue(i + 1, j)) {
                            if (actors.containsKey(actor)) {
                                actors.put(actor, actors.get(actor) + 1);
                            } else {
                                actors.put(actor, 1);
                            }
                        }

                        for (Actor actor : mask.getValue(i, j - 1)) {
                            if (actors.containsKey(actor)) {
                                actors.put(actor, actors.get(actor) + 1);
                            } else {
                                actors.put(actor, 1);
                            }
                        }

                        for (Actor actor : mask.getValue(i, j + 1)) {
                            if (actors.containsKey(actor)) {
                                actors.put(actor, actors.get(actor) + 1);
                            } else {
                                actors.put(actor, 1);
                            }
                        }

                        Set<Actor> finalActors = actors.keySet().stream().filter(key -> actors.get(key) >= 3).collect(Collectors.toSet());

                        mask.setAllValue(i, j, finalActors);
                    }
                }
            }
        }
    }

    private void calculateFor(Actor actor, int range, VisibilityMask visibilityMask, Map2D map) {

        visibilityMask.addChangedArea(actor.getX() - range -2, actor.getY() - range -2, actor.getX() + range + 2, actor.getY() + range +2, actor);

        List<Integer[]> points = midPointCircleDraw(actor.getX(), actor.getY(), range);

        for (Integer[] point : points) {
            line(actor.getX(), actor.getY(), point[0], point[1], visibilityMask, map, actor);
        }
    }
}
