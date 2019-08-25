package com.mygdx.game.actor;

import com.mygdx.game.item.Item;
import com.mygdx.game.logic.actor.ActorMovementHandler;
import com.mygdx.game.logic.attack.AttackController;
import com.mygdx.game.registry.*;

public class ActorDeathHandler {

    public static final ActorDeathHandler INSTANCE = new ActorDeathHandler();

    public void kill(Actor actor) {
        ActorRegistry.INSTANCE.remove(actor.getCurrentMap(), actor);
        AnimationRegistry.INSTANCE.remove(actor);
        ActorMovementHandler.INSTANCE.clearPath(actor);
        AttackController.INSTANCE.clearAttackingHistory(actor);
        EffectRegistry.INSTANCE.removeAll(actor);
        SkillFocusRegistry.INSTANCE.remove(actor);

        for(Item item : actor.drop()) {
            ItemRegistry.INSTANCE.add(actor.getCurrentMap(), item);
        }

        if(HouseRegistry.INSTANCE.getOwnedHouses().containsKey(actor)) {
            HouseRegistry.INSTANCE.getEmptyHouses().add(HouseRegistry.INSTANCE.getOwnedHouses().get(actor));
            HouseRegistry.INSTANCE.getOwnedHouses().remove(actor);
        }
    }

    private ActorDeathHandler() {
    }
}
