package com.mygdx.game.logic.visibility;

import com.mygdx.game.actor.factory.ActorFactory;
import com.mygdx.game.actor.factory.Placement;
import com.mygdx.game.actor.hero.Wizard;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.map.worldmap.WorldMapGenerator;
import com.mygdx.game.registry.ActorRegistry;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;

public class VisibilityCalculatorTest {

    @Test
    public void performance() {
        Map2D map2D = new WorldMapGenerator().create(3);

        for(int i = 0; i < 200; i++) {
            ActorFactory.INSTANCE.create(Wizard.class, map2D, Placement.RANDOM);
        }

        VisibilityCalculator visibilityCalculator = new VisibilityCalculator(map2D.getWidth(), map2D.getHeight());
        visibilityCalculator.generateMask(map2D, ActorRegistry.INSTANCE.getActors(map2D));


//        for(int i = 0; i < 150; i++)
//            ActorMovementHandler.INSTANCE.getChangedCoordList().remove(0);
//        System.out.println(ActorMovementHandler.INSTANCE.getChangedCoordList().size());

        List<Long> avg = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            long s = System.currentTimeMillis();
            visibilityCalculator.generateMask(map2D, ActorMovementHandler.INSTANCE.getChangedCoordList());
            avg.add(System.currentTimeMillis() - s);
        }

        long av = avg.stream().reduce((a,b) -> a+b).get() / 100;

        System.out.println(av);
        assertThat(av, is(lessThan(45L)));
    }

}