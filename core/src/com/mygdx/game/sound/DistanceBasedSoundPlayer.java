package com.mygdx.game.sound;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.common.util.MathUtil;
import com.mygdx.game.logic.Point;
import com.mygdx.game.registry.RendererToolsRegistry;

public class DistanceBasedSoundPlayer {

    public static void play(Sound sound, Point actualCoordinates, float maxVolume, float minVolume) {
        int startX = (int) RendererToolsRegistry.INSTANCE.getCamera().position.x;
        int startY = (int)RendererToolsRegistry.INSTANCE.getCamera().position.y;

        float distance = (float) MathUtil.distance(Point.of(startX, startY), actualCoordinates);
        sound.play(Math.min(Math.max(minVolume, maxVolume - distance / 500f), maxVolume), 1f, 1f);
    }

}
