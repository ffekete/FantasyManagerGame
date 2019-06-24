package com.mygdx.game.item.projectile.manager;

import com.mygdx.game.item.projectile.Projectile;
import com.mygdx.game.registry.ProjectileRegistry;

public class ProjectileManager {
    public static final ProjectileManager INSTANCE = new ProjectileManager();

    private final ProjectileRegistry projectileRegistry = ProjectileRegistry.INSTANCE;

    // for performance tuning
    private Projectile projectile;

    private ProjectileManager() {
    }

    public void update() {

        for (int i = 0; i < projectileRegistry.getProjectiles().size(); i++) {
            projectile = projectileRegistry.getProjectiles().get(i);
            if (!projectile.isFinished()) {
                projectile.update();
            } else {
                projectile.finish();
                projectileRegistry.remove(projectile);
            }
        }
    }
}
