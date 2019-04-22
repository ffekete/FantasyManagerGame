package com.mygdx.game.actor.inventory;

import com.mygdx.game.item.Bread;
import com.mygdx.game.item.Food;
import com.mygdx.game.item.Item;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InventoryUnitTest {

    @Test
    public void addTest() {
        Inventory inventory = new Inventory();
        Food food = new Bread();
        inventory.add(food);

        assertThat(inventory.has(Item.class), is(true));
        assertThat(inventory.has(Food.class), is(true));
        assertThat(inventory.has(Bread.class), is(true));
    }
}
