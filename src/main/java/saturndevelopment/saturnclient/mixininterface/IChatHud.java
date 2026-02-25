/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.mixininterface;

import net.minecraft.text.Text;

public interface IChatHud {
    void meteor$add(Text message, int id);
}
