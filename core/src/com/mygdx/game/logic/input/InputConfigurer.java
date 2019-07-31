package com.mygdx.game.logic.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class InputConfigurer {

    public static final InputConfigurer INSTANCE = new InputConfigurer();

    public void setInputProcessor(InputProcessor... inputProcessors) {
        InputMultiplexer im = new InputMultiplexer();

        for(InputProcessor inputProcessor : inputProcessors) {
            im.addProcessor(inputProcessor);
        }

        Gdx.input.setInputProcessor(im);
    }

}
