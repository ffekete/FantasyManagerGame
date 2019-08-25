package com.mygdx.game.item.component;

import com.mygdx.game.item.AbstractItem;

public class WolfPelt extends AbstractItem implements Component {

    @Override
    public String getDescription() {
        return "Skin of a wolf.";
    }

    @Override
    public String getName() {
        return "Wolf pelt";
    }
}
