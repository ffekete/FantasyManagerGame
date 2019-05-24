package com.mygdx.game.logic.activity;

import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.actor.hero.Warrior;
import com.mygdx.game.creator.map.Map2D;
import com.mygdx.game.creator.map.dungeon.DummyDungeonCreator;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.activity.single.ExplorationActivity;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.registry.MapRegistry;

import org.testng.annotations.Test;

public class ExplorationActivityTest {

    private ExplorationActivity activity;

    @Test
    public void t() {
        Map2D dungeon = new DummyDungeonCreator().create(5);

        VisitedArea[][] map = new VisitedArea[Config.Dungeon.DUNGEON_WIDTH][Config.Dungeon.DUNGEON_HEIGHT];

        for (int i = 0; i < Config.Dungeon.DUNGEON_WIDTH; i++) {
            for (int j = 0; j < Config.Dungeon.DUNGEON_HEIGHT; j++) {
                map[i][j] = VisitedArea.NOT_VISITED;
            }
        }

        map[5][5] = VisitedArea.VISITED_BUT_NOT_VISIBLE;

        dungeon.setVisitedAreaMap(map);
        MapRegistry.INSTANCE.add(dungeon);
        Actor actor = new Warrior();
        actor.setCoordinates(new Point(5,5));
        actor.setCurrentMap(dungeon);
        activity = new ExplorationActivity(dungeon, actor);
        //activity.init();
        boolean[][] checked = new boolean[Config.Dungeon.DUNGEON_WIDTH][Config.Dungeon.DUNGEON_HEIGHT];
        Point p = activity.findNextUnexploredArea(checked, dungeon, 5,5);

        System.out.println(p.getX() + " " + p.getY());
    }

}