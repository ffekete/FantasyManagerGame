package com.mygdx.game.logic.visibility;

import com.mygdx.game.logic.Point;

import java.util.ArrayList;
import java.util.List;

public class VisibilityCalculator {

    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public static final int VISIBILITY_RANGE = 15;

    public static int[][] generateMask(int[][] map, Point... points) {
        int[][] mask = new int[WIDTH][HEIGHT];
        for(Point point : points) {
            calculateFor(point.getX(), point.getY(), VISIBILITY_RANGE, mask, map);
        }

        refine(mask);

        return mask;
    }

    private static List<Integer[]> midPointCircleDraw(int x_centre,
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

    private static void line(int x,int y,int x2, int y2, int color, int[][] visibilityMask, int[][] map) {
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
            x = Math.min(x, WIDTH-1);
            y = Math.min(y, HEIGHT-1);

            if (map[x][y] == 1) break;
            visibilityMask[x][y] = color;


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

    private static void refine(int[][] mask) {
        for(int i = 1; i < WIDTH-1; i++) {
            for(int j = 1; j < HEIGHT-1; j++) {
                int sum =  mask[i-1][j] + mask[i+1][j] + mask[i][j-1] + mask[i][j+1];
                if(mask[i][j] == 0 && sum >= 3) {
                    mask[i][j] = 1;
                }
            }
        }
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        int[][] visibilityMask;
        int[][] map = new int[WIDTH][HEIGHT];

        for(int i = 0; i < 20; i++) {
            map[11][i+5] = 1;
            map[11+i][5] = 1;
            map[11+i][25] = 1;
            map[30][5+i] = 1;

        }

        visibilityMask = generateMask(map, new Point(35, 10), new Point(0,0));

        System.out.println(System.currentTimeMillis() - start);

        print(visibilityMask, map);
    }

    private static void print(int[][] visibilityMask, int[][] map) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println("");
        }


        System.out.println(" ----------------------- ");
        System.out.println("");

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                System.out.print(visibilityMask[i][j]);
            }
            System.out.println("");
        }
    }

    private static void calculateFor(int x, int y, int range, int[][] visibilityMask, int[][] map) {
        List<Integer[]> points = midPointCircleDraw(x, y, range);

        for (Integer[] point : points) {
            line(x, y, point[0], point[1],1, visibilityMask, map);
        }
    }
}
