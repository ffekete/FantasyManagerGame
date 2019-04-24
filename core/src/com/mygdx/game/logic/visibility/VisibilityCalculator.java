package com.mygdx.game.logic.visibility;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.Tile;
import com.mygdx.game.creator.map.dungeon.Dungeon;
import com.mygdx.game.logic.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VisibilityCalculator {

    public final int width;
    public final int height;

    public VisibilityCalculator(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public VisibilityMask generateMask(Map2D map, int range, List<Actor> points) {
        VisibilityMask mask = new VisibilityMask(width, height);
        for(Actor actor : points) {
            calculateFor(actor, range, mask, map);
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

    private void line(int x,int y,int x2, int y2, VisibilityMask visibilityMask, Map2D map, Actor value) {
        int w = x2 - x ;
        int h = y2 - y ;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;
        }
        int numerator = longest >> 1 ;
        for (int i=0;i<=longest;i++) {


            x = Math.max(x, 0);
            y = Math.max(y, 0);
            x = Math.min(x, width-1);
            y = Math.min(y, height-1);

            visibilityMask.setValue(x, y, value);
            if (map.getTile(x,y).isObstacle()) break;


            numerator += shortest ;
            if (!(numerator<longest)) {
                numerator -= longest ;
                x += dx1 ;
                y += dy1 ;
            } else {
                x += dx2 ;
                y += dy2 ;
            }
        }
    }

    private void refine(VisibilityMask mask) {
        for(int i = 1; i < width-1; i++) {
            for(int j = 1; j < height-1; j++) {
                int a = mask.getValue(i-1, j).size() > 0 ? 1 : 0;
                int b = mask.getValue(i+1, j).size() > 0 ? 1 : 0;
                int c = mask.getValue(i, j-1).size() > 0 ? 1 : 0;
                int d = mask.getValue(i, j+1).size() > 0 ? 1 : 0;
                int sum =  a + b + c + d;
                if(mask.getValue(i, j).isEmpty() && sum >= 3) {
                    Set<Actor> finalActors = new HashSet<>();
                    finalActors.addAll(mask.getValue(i-1, j));
                    finalActors.addAll(mask.getValue(i+1, j));
                    finalActors.addAll(mask.getValue(i, j -1));
                    finalActors.addAll(mask.getValue(i, j+1));

                    mask.setAllValue(i,j, finalActors);
                }
            }
        }
    }

    private void calculateFor(Actor actor, int range, VisibilityMask visibilityMask, Map2D map) {
        List<Integer[]> points = midPointCircleDraw(actor.getX(), actor.getY(), range);

        for (Integer[] point : points) {
            line(actor.getX(), actor.getY(), point[0], point[1], visibilityMask, map, actor);
        }
    }
}
