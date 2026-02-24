/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.events.entity.player;

import saturndevelopment.saturnclient.events.Cancellable;

/**
 * Some of our other injections coming from {@link net.minecraft.client.MinecraftClient#doItemUse()}
 * (e.g. InteractItemEvent) are called twice because the method loops over the Mainhand and the Offhand. This event is
 * only called once, before any interaction logic is called.
 */
public class DoItemUseEvent extends Cancellable {
    private static final DoItemUseEvent INSTANCE = new DoItemUseEvent();

    public static DoItemUseEvent get() {
        return INSTANCE;
    }
}
