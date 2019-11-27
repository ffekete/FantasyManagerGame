package com.mygdx.game.actor;

public enum BodyType {

    Humanoid("humanoid"),
    Goblin("goblin"),
    Orc("orc"),
    HumanoidSkeleton("skeleton"),
    Rabbit("rabbit"),
    Wolf("wolf");

    private final String archetype;

    BodyType(String archetype) {
        this.archetype = archetype;
    }

    public String getArchetype() {
        return archetype;
    }
}
