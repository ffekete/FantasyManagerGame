package com.mygdx.game.item.buildertool;

import com.mygdx.game.item.AbstractItem;
import com.mygdx.game.item.Item;

public class Hammer extends AbstractItem implements Item {

    @Override
    public String getDescription() {
        return "Pain old hammer";
    }

    @Override
    public String getName() {
        return "Hammer";
    }
}
