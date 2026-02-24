/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.systems.modules.misc;

import saturndevelopment.saturnclient.systems.modules.Categories;
import saturndevelopment.saturnclient.systems.modules.Module;

public class BetterBeacons extends Module {
    public BetterBeacons() {
        super(Categories.Misc, "better-beacons", "Select effects unaffected by beacon level.");
    }
}
