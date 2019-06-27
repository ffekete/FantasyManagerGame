package com.mygdx.game.logic.selector;

import com.mygdx.game.actor.Actor;
import com.mygdx.game.item.Item;
import com.mygdx.game.registry.VisibilityMapRegistry;

import java.util.List;

public class ItemSelector {

    public Item findClosestItem(Actor actor, List<Item> Items, Integer maxDistance, Class<? extends Item> clazz) {
        Item selectedItem = null;
        Item item;
        int x = actor.getX();
        int y = actor.getY();

        float minDistance = Float.MAX_VALUE;
        for(int i = 0; i < Items.size(); i++) {
            item = Items.get(i);
            if(!clazz.isAssignableFrom(item.getClass())) {
                continue;
            }
            int a = item.getX();
            int b = item.getY();

            if(!VisibilityMapRegistry.INSTANCE.getFor(actor.getCurrentMap()).getValue(a,b).contains(actor)) {
                continue;
            }

            float distance = Math.abs(x-a)*Math.abs(x-a) + Math.abs(y-b) * Math.abs(y-b);
            if(distance < minDistance) {
                selectedItem = item;
                minDistance = distance;
            }
        }
        return minDistance > maxDistance*maxDistance ? null : selectedItem;
    }

}
