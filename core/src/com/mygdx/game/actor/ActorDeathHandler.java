package com.mygdx.game.actor;

import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.attack.AttackController;
import com.mygdx.game.registry.ActorRegistry;
import com.mygdx.game.registry.AnimationRegistry;
import com.mygdx.game.registry.EffectRegistry;
import com.mygdx.game.registry.SkillFocusRegistry;

public class ActorDeathHandler {

    public static final ActorDeathHandler INSTANCE = new ActorDeathHandler();

    public void kill(Actor actor) {
        ActorRegistry.INSTANCE.getActors(actor.getCurrentMap()).remove(actor);
        AnimationRegistry.INSTANCE.remove(actor);
        ActorMovementHandler.INSTANCE.clearPath(actor);
        AttackController.INSTANCE.clearAttackingHistory(actor);
        EffectRegistry.INSTANCE.removeAll(actor);
        SkillFocusRegistry.INSTANCE.remove(actor);
    }

    private ActorDeathHandler() {
    }
}
