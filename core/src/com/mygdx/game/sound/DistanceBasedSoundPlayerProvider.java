package com.mygdx.game.sound;

public class DistanceBasedSoundPlayerProvider {

    public static final DistanceBasedSoundPlayerProvider INSTANCE = new DistanceBasedSoundPlayerProvider();

    public DistanceBasedSoundPlayer provide() {
        return new DistanceBasedSoundPlayer();
    }

}
