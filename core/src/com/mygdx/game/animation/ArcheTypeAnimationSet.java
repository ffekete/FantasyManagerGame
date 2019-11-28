package com.mygdx.game.animation;

import java.util.Map;

public class ArcheTypeAnimationSet {

    String type;
    BodyPart body;
    BodyPart eyes;
    BodyPart hair;
    Map<String, String> armor;

    public BodyPart getBody() {
        return body;
    }

    public BodyPart getEyes() {
        return eyes;
    }

    public BodyPart getHair() {
        return hair;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getArmor() {
        return armor;
    }
}


