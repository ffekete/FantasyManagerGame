package com.mygdx.game.input.mouse.processor;

import com.google.common.collect.ImmutableMap;
import com.mygdx.game.builder.BuildingBlock;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.object.wall.IncompleteWoodenDoorWall;
import com.mygdx.game.registry.ObjectRegistry;
import com.mygdx.game.utils.MapUtils;

import java.util.Map;

public class BuildingActionValidator {

    interface CustomPredicate {
        public boolean validate(Map2D map2D, int x, int y, Class<? extends BuildingBlock> blockClass);
    }

    private Map<Class<? extends BuildingBlock>, CustomPredicate> validator = ImmutableMap.<Class<? extends BuildingBlock>, CustomPredicate>builder()
            .put(IncompleteWoodenDoorWall.class, (Map2D map, int x, int y, Class<? extends BuildingBlock> block) -> {
                return MapUtils.bitmask4bit(ObjectRegistry.INSTANCE.getObjectGrid().get(map), x, y, IncompleteWoodenDoorWall.class) == 10;
            }).build();

    public boolean validate(Map2D map, int x, int y, Class<? extends BuildingBlock> blockClass) {
        if (validator.containsKey(blockClass)) {
            return validator.get(blockClass).validate(map, x, y, blockClass);
        }
        return true;
    }

}
