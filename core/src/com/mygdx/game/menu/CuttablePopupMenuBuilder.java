package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.logic.GameState;
import com.mygdx.game.logic.Point;
import com.mygdx.game.logic.action.Action;
import com.mygdx.game.logic.action.TargetMarkerAction;
import com.mygdx.game.logic.command.CutDownCommand;
import com.mygdx.game.object.Cuttable;
import com.mygdx.game.object.Targetable;
import com.mygdx.game.object.WorldObject;
import com.mygdx.game.registry.ActionRegistry;
import com.mygdx.game.registry.CommandRegistry;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;
import com.mygdx.game.stage.StageConfigurer;

public class CuttablePopupMenuBuilder {

    public static final CuttablePopupMenuBuilder INSTANCE = new CuttablePopupMenuBuilder();

    private final MapRegistry mapRegistry = MapRegistry.INSTANCE;

    public Window build(WorldObject worldObject, Point mouseCoord, Point realWorldCoord) {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = RendererToolsRegistry.INSTANCE.getBitmapFontSmallest();
        windowStyle.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("bg.png")));
        //windowStyle.background =

        Window window = new Window("Actions...", windowStyle);
        float newWidth = 200, newHeight = 100;
        window.setBounds(mouseCoord.getX() - 100,
                Gdx.graphics.getHeight() - mouseCoord.getY() - 50, newWidth, newHeight);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = RendererToolsRegistry.INSTANCE.getBitmapFontSmallest();
        TextButton cutDownButton = new TextButton("Cut down", textButtonStyle);
        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        window.add(cutDownButton).row();
        window.add(exitButton);

        exitButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                StageConfigurer.INSTANCE.getFor(GameState.Sandbox).getActors().removeValue(window, true);
                return true;
            }
        });

        cutDownButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Action action = new TargetMarkerAction((Targetable) worldObject);
                action.setCoordinates(realWorldCoord);
                ActionRegistry.INSTANCE.add(mapRegistry.getCurrentMapToShow(), action);
                CommandRegistry.INSTANCE.add(new CutDownCommand((Cuttable) worldObject));
                window.setVisible(false);
                return true;

            }
        });
        return window;
    }

}
