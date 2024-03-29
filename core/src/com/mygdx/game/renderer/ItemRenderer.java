package com.mygdx.game.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.item.Item;
import com.mygdx.game.logic.time.DayTimeCalculator;
import com.mygdx.game.logic.visibility.VisibilityMask;
import com.mygdx.game.logic.visibility.VisitedArea;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.ItemRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.registry.VisibilityMapRegistry;
import com.mygdx.game.renderer.camera.CameraPositionController;

public class ItemRenderer implements Renderer<Map2D> {

    public static final ItemRenderer INSTANCE = new ItemRenderer();

    private final TextureRegistry textureRegistry = TextureRegistry.INSTANCE;
    private final ItemRegistry itemRegistry = ItemRegistry.INSTANCE;

    private final float offset = (1f - Config.Engine.ACTOR_HEIGHT) / 2f;

    @Override
    public void draw(Map2D dungeon, SpriteBatch spriteBatch) {

        if (CameraPositionController.INSTANCE.getZoom() > Config.Engine.ZOOM_MAX_TO_SMALL_MAP) {
            return;
        }

        VisibilityMask visibilityMask = VisibilityMapRegistry.INSTANCE.getFor(dungeon);


        if(dungeon.getMapType().equals(Map2D.MapType.WORLD_MAP)) {
            spriteBatch.setColor(Color.WHITE);
        }
        else if(DayTimeCalculator.INSTANCE.isItNight()) {
            spriteBatch.setColor(Config.Engine.NIGHT_COLOR);
        } else if(DayTimeCalculator.INSTANCE.isDawn()) {
            spriteBatch.setColor(Config.Engine.DAWN_COLOR);
        }
        else if(DayTimeCalculator.INSTANCE.isDusk()) {
            spriteBatch.setColor(Config.Engine.DUSK_COLOR);
        }
        else {
            spriteBatch.setColor(Color.WHITE);
        }

        for (Item item : itemRegistry.getAllItems(dungeon)) {
            if (!visibilityMask.getValue(item.getX(), item.getY()).isEmpty() && dungeon.getVisitedareaMap()[item.getX()][item.getY()] != VisitedArea.NOT_VISITED ) {
                spriteBatch.draw(textureRegistry.getFor(item.getClass()), item.getX(), item.getY() + offset, 0, 0, 1, 1, Config.Engine.ACTOR_HEIGHT, Config.Engine.ACTOR_HEIGHT, 0, 0, 0, textureRegistry.getFor(item.getClass()).getWidth(), textureRegistry.getFor(item.getClass()).getHeight(), false, false);
            }
        }
    }

}
