/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.systems.modules.player;

import saturndevelopment.saturnclient.systems.modules.Categories;
import saturndevelopment.saturnclient.systems.modules.Module;

public class LiquidInteract extends Module {
    public LiquidInteract() {
        super(Categories.Player, "liquid-interact", "Allows you to interact with liquids.");
    }
}
