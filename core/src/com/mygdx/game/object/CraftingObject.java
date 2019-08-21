package com.mygdx.game.object;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Craftable;
import com.mygdx.game.item.category.Category;
import com.mygdx.game.object.furniture.Furniture;

public interface CraftingObject extends InteractiveObject, Furniture {

    boolean isFinished();

    void start(Class<? extends Craftable> craftable);

    Float getProgress();

    void addProgress(float amount);

    Craftable finish();

    Class<? extends Category> getMaxTier();

    void cancel();

    Actor getUser();

    Craftable getItem();

}
