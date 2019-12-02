package com.mygdx.game.actor;

public enum BodyType {

    Humanoid("humanoid"),
    Cyclops("cyclops"),
    Centaur("centaur"),
    Goblin("goblin"),
    Orc("orc"),
    Medusa("medusa"),
    Minotaurus("medusa"),
    Troll("troll"),
    Ogre("ogre"),
    HumanoidSkeleton("skeleton"),
    Ethereal("ethereal"),
    Ghoul("ghoul"),
    RatMan("ratman"),
    Rabbit("rabbit"),
    Wolf("wolf"),
    Bear("bear"),
    Chimera("chimera"),
    Imp("imp"),
    Demon("demon"),
    Devil("devil"),
    Fox("fox"),
    Pheasant("pheasant"),
    Deer("deer");

    private final String archetype;

    BodyType(String archetype) {
        this.archetype = archetype;
    }

    public String getArchetype() {
        return archetype;
    }
}
