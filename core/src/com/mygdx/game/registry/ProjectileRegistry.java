package com.mygdx.game.registry;

import com.mygdx.game.item.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

public class ProjectileRegistry {

    public static final ProjectileRegistry INSTANCE = new ProjectileRegistry();

    private final List<Projectile> projectiles;

    private ProjectileRegistry() {
        projectiles = new ArrayList<>();
    }

    public List<Projectile> getProjectiles() {

        return projectiles;
    }

    public void add(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void remove(Projectile projectile) {
        projectiles.remove(projectile);
    }
}
