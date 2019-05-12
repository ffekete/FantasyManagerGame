package com.mygdx.game.common.util;

import com.mygdx.game.logic.Point;

public class MathUtil {

    public static double distance(Point p1, Point p2) {
        if(p1 == null || p2 == null)
            return Double.MAX_VALUE;

        int a = Math.abs(p1.getX() - p2.getX());
        int b = Math.abs(p1.getY() - p2.getY());

        return Math.sqrt(a*a + b*b);
    }
}
