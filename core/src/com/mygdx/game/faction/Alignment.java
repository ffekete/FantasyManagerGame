package com.mygdx.game.faction;

import java.util.Arrays;
import java.util.List;

public enum Alignment {
    FRIENDLY(),
    ENEMY(),
    NEUTRAL(),
    FACTION(FRIENDLY, ENEMY);

    private List<Alignment> enemies;

    static {
        FRIENDLY.setEnemies(ENEMY, FACTION);
        ENEMY.setEnemies(FRIENDLY, FACTION);

    }

    Alignment(Alignment... enemies) {
        this.enemies = Arrays.asList(enemies);
    }

    public void setEnemies(Alignment... enemies) {
        this.enemies = Arrays.asList(enemies);
    }

    public List<Alignment> getEnemies() {
        return enemies;
    }
}
