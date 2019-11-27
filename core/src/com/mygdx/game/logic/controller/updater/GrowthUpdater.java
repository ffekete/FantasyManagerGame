package com.mygdx.game.logic.controller.updater;

import com.mygdx.game.object.decoration.Growable;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.ObjectRegistry;

public class GrowthUpdater {

    public static final GrowthUpdater INSTANCE = new GrowthUpdater();

    public void update() {
        ObjectRegistry.INSTANCE.getObject(MapRegistry.INSTANCE.getWorldMap(), Growable.class).ifPresent(worldObjects -> worldObjects.stream().map(o -> (Growable) o).forEach(Growable::grow));
    }

}
