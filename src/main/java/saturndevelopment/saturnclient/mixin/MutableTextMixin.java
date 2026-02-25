/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.mixin;

import saturndevelopment.saturnclient.mixininterface.IText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Language;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MutableText.class)
public abstract class MutableTextMixin implements IText {
    @Shadow
    private @Nullable Language language;

    @Override
    public void meteor$invalidateCache() {
        this.language = null;
    }
}
