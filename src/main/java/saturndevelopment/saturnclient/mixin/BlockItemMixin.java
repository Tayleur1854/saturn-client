/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.mixin;

import saturndevelopment.saturnclient.SaturnClient;
import saturndevelopment.saturnclient.events.entity.player.PlaceBlockEvent;
import saturndevelopment.saturnclient.systems.modules.Modules;
import saturndevelopment.saturnclient.systems.modules.world.NoGhostBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
    @Shadow
    protected abstract BlockState getPlacementState(ItemPlacementContext context);

    @Inject(method = "place(Lnet/minecraft/item/ItemPlacementContext;Lnet/minecraft/block/BlockState;)Z", at = @At("HEAD"), cancellable = true)
    private void onPlace(ItemPlacementContext context, BlockState state, CallbackInfoReturnable<Boolean> info) {
        if (!context.getWorld().isClient()) return;

        if (SaturnClient.EVENT_BUS.post(PlaceBlockEvent.get(context.getBlockPos(), state.getBlock())).isCancelled()) {
            info.setReturnValue(true);
        }
    }

    @ModifyVariable(
        method = "place(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/util/ActionResult;",
        ordinal = 1,
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
        )
    )
    private BlockState modifyState(BlockState state, ItemPlacementContext context) {
        var noGhostBlocks = Modules.get().get(NoGhostBlocks.class);

        if (noGhostBlocks.isActive() && noGhostBlocks.placing.get()) {
            return getPlacementState(context);
        }

        return state;
    }
}
