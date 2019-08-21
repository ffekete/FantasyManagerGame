package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.CraftingObject;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.stage.StageConfigurer;

public class CraftingObjectPopupMenuBuilder {

    public static final CraftingObjectPopupMenuBuilder INSTANCE = new CraftingObjectPopupMenuBuilder();

    private Drawable background = new TextureRegionDrawable(TextureRegistry.INSTANCE.getFor(MenuItem.CutableBuildMenuBackground));

    private TextButton.TextButtonStyle textButtonStyle;

    private Window.WindowStyle windowStyle;
    private Window window;

    private TextButton userButton;
    private TextButton itemButton;
    private TextButton progressButton;
    private TextButton exitButton;

    public CraftingObjectPopupMenuBuilder() {
        windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = RendererToolsRegistry.INSTANCE.getBitmapFontSmallest();
        windowStyle.background = background;
        window = new Window("Details...", windowStyle);
        StageConfigurer.INSTANCE.getFor(GameState.Sandbox).addActor(window);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = RendererToolsRegistry.INSTANCE.getBitmapFontTiny();

        itemButton = new TextButton("", textButtonStyle);
        userButton = new TextButton("", textButtonStyle);
        progressButton = new TextButton("", textButtonStyle);
        exitButton = new TextButton("Exit", textButtonStyle);

    }

    public Window build(CraftingObject worldObject, Point mouseCoord, Point realWorldCoord) {
        float newWidth = 200, newHeight = 100;
        window.setBounds(mouseCoord.getX() - 100,
                Gdx.graphics.getHeight() - mouseCoord.getY() - 50, newWidth, newHeight);

        window.clear();

        window.addActor(new com.badlogic.gdx.scenes.scene2d.Actor() {
            @Override
            public void act(float delta) {
                progressButton.setText("Progress: " + worldObject.getProgress().intValue());
                userButton.setText((worldObject.getUser() != null ? worldObject.getUser().getName() : "nobody ") + " is using this item.");
                itemButton.setText("Crafting: " + (worldObject.getItem() != null ? worldObject.getItem().getName() : "nothing"));
            }


        });

        window.add(userButton).row();
        window.add(itemButton).row();
        window.add(progressButton).row();
        window.add(exitButton).row();
        window.setVisible(true);

        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //StageConfigurer.INSTANCE.getFor(GameState.Sandbox).getActors().removeValue(window, true);
                window.setVisible(false);
                return true;
            }
        });

        return window;
    }

}
