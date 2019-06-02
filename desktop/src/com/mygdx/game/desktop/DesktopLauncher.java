package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.CaveDungeonRendererSample;
import com.mygdx.game.WorldMapSample;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples = 8;
		config.fullscreen = true;
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new WorldMapSample(), config);
	}
}
