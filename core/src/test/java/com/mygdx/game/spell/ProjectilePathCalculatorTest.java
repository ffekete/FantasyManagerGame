package com.mygdx.game.spell;

import com.mygdx.game.logic.Point;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProjectilePathCalculatorTest {


    private ProjectilePathCalculator projectilePathCalculator = new ProjectilePathCalculator();

    @Test
    public void test() {
        List<Point> path = new ArrayList<>();
        projectilePathCalculator.calculate(1,1, 3,1, path);

        assertThat(path.size(), is(3));
        assertThat(path.get(0), is(Point.of(1,1)));
        assertThat(path.get(1), is(Point.of(2,1)));
        assertThat(path.get(2), is(Point.of(3,1)));
    }

    @Test
    public void test2() {
        List<Point> path = new ArrayList<>();
        projectilePathCalculator.calculate(1,1, 4,2, path);

        assertThat(path.size(), is(4));
        assertThat(path.get(0), is(Point.of(1,1)));
        assertThat(path.get(1), is(Point.of(2,1)));
        assertThat(path.get(2), is(Point.of(3,2)));
        assertThat(path.get(3), is(Point.of(4,2)));
    }

}