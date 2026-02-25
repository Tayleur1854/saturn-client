/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import saturndevelopment.saturnclient.commands.Command;
import saturndevelopment.saturnclient.renderer.Fonts;
import saturndevelopment.saturnclient.systems.Systems;
import saturndevelopment.saturnclient.systems.friends.Friend;
import saturndevelopment.saturnclient.systems.friends.Friends;
import saturndevelopment.saturnclient.utils.network.Capes;
import saturndevelopment.saturnclient.utils.network.MeteorExecutor;
import net.minecraft.command.CommandSource;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", "Reloads many systems.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            warning("Reloading systems, this may take a while.");

            Systems.load();
            Capes.init();
            Fonts.refresh();
            MeteorExecutor.execute(() -> Friends.get().forEach(Friend::updateInfo));

            return SINGLE_SUCCESS;
        });
    }
}
