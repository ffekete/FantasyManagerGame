package com.mygdx.game.music;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.MusicRegistry;

public class MusicPlayer {

    public static final MusicPlayer INSTANCE = new MusicPlayer();

    private Sound music;
    private Sound previousMusic;

    public void choose() {
//        for(Map2D.MapType mapType : Map2D.MapType.values()) {
//            if(!MapRegistry.INSTANCE.getCurrentMapToShow().getMapType().equals(mapType)) {
//                MusicRegistry.INSTANCE.getFor(mapType).stop();
//            }
//        }
        music = MusicRegistry.INSTANCE.getFor(MapRegistry.INSTANCE.getCurrentMapToShow().getMapType());

//        if(previousMusic != music) {
//            if(previousMusic != null)
//                previousMusic.pause();
//            previousMusic = music;
//            music.play();
//        }

    }

}
