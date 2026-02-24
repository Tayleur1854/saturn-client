/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import saturndevelopment.saturnclient.renderer.MeshUniforms;
import saturndevelopment.saturnclient.systems.modules.Modules;
import saturndevelopment.saturnclient.systems.modules.misc.InventoryTweaks;
import saturndevelopment.saturnclient.utils.render.postprocess.ChamsShader;
import saturndevelopment.saturnclient.utils.render.postprocess.OutlineUniforms;
import saturndevelopment.saturnclient.utils.render.postprocess.PostProcessShader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static saturndevelopment.saturnclient.SaturnClient.mc;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Inject(method = "flipFrame", at = @At("TAIL"))
    private static void meteor$flipFrame(CallbackInfo info) {
        MeshUniforms.flipFrame();
        PostProcessShader.flipFrame();
        ChamsShader.flipFrame();
        OutlineUniforms.flipFrame();

        if (Modules.get() == null || mc.player == null) return;
        if (Modules.get().get(InventoryTweaks.class).frameInput()) ((MinecraftClientAccessor) mc).meteor$handleInputEvents();
    }
}
