package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.Point;
import com.mygdx.game.object.CraftingObject;
import com.mygdx.game.quest.MoveToLocationQuest;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.QuestRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.registry.TextureRegistry;
import com.mygdx.game.stage.StageConfigurer;

public class EmptyAreaPopupMenuBuilder {

    public static final EmptyAreaPopupMenuBuilder INSTANCE = new EmptyAreaPopupMenuBuilder();

    private Drawable background = new TextureRegionDrawable(TextureRegistry.INSTANCE.getFor(MenuItem.Window));

    private TextButton.TextButtonStyle textButtonStyle;

    private Window.WindowStyle windowStyle;
    private Window window;

    private TextButton moveToQuestButton;
    private TextButton exitButton;

    public EmptyAreaPopupMenuBuilder() {
        windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = RendererToolsRegistry.INSTANCE.getBitmapFontSmallest();
        windowStyle.background = background;
        window = new Window("Details...", windowStyle);
        StageConfigurer.INSTANCE.getFor(GameState.Sandbox).addActor(window);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = RendererToolsRegistry.INSTANCE.getBitmapFontTiny();
        textButtonStyle.fontColor = Color.BLACK;

        moveToQuestButton = new TextButton("", textButtonStyle);
        exitButton = new TextButton("Exit", textButtonStyle);

    }

    public Window build(Point mouseCoord, Point realWorldCoord) {
        float newWidth = 200, newHeight = 100;
        window.setBounds(mouseCoord.getX() - 100,
                Gdx.graphics.getHeight() - mouseCoord.getY() - 50, newWidth, newHeight);

        window.clear();

        window.addActor(new com.badlogic.gdx.scenes.scene2d.Actor() {
            @Override
            public void act(float delta) {
                moveToQuestButton.setText("Explore quest");
                moveToQuestButton.addListener(new ClickListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        MoveToLocationQuest moveToLocationQuest = new MoveToLocationQuest(realWorldCoord, MapRegistry.INSTANCE.getCurrentMapToShow());
                        QuestRegistry.INSTANCE.add(MapRegistry.INSTANCE.getCurrentMapToShow(), moveToLocationQuest);
                        window.setVisible(false);
                        return true;
                    }
                });
            }


        });

        window.add(moveToQuestButton).row();
        window.add(exitButton).row();
        window.setVisible(true);

        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                window.setVisible(false);
                return true;
            }
        });

        return window;
    }

}
