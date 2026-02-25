/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import saturndevelopment.saturnclient.commands.Command;
import saturndevelopment.saturnclient.commands.arguments.ModuleArgumentType;
import saturndevelopment.saturnclient.commands.arguments.SettingArgumentType;
import saturndevelopment.saturnclient.commands.arguments.SettingValueArgumentType;
import saturndevelopment.saturnclient.gui.GuiThemes;
import saturndevelopment.saturnclient.gui.WidgetScreen;
import saturndevelopment.saturnclient.gui.tabs.TabScreen;
import saturndevelopment.saturnclient.gui.tabs.Tabs;
import saturndevelopment.saturnclient.gui.tabs.builtin.ConfigTab;
import saturndevelopment.saturnclient.gui.tabs.builtin.HudTab;
import saturndevelopment.saturnclient.settings.Setting;
import saturndevelopment.saturnclient.systems.config.Config;
import saturndevelopment.saturnclient.systems.modules.Module;
import saturndevelopment.saturnclient.utils.Utils;
import saturndevelopment.saturnclient.utils.player.ChatUtils;
import net.minecraft.command.CommandSource;

public class SettingCommand extends Command {
    public SettingCommand() {
        super("settings", "Allows you to view and change module settings.", "s");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        // Open hud screen
        builder.then(
            literal("hud")
                .executes(context -> {
                    TabScreen screen = Tabs.get(HudTab.class).createScreen(GuiThemes.get());
                    screen.parent = null;

                    Utils.screenToOpen = screen;
                    return SINGLE_SUCCESS;
                })
        );

        // Open config screen
        builder.then(
            literal("config")
                .executes(context -> {
                    TabScreen screen = Tabs.get(ConfigTab.class).createScreen(GuiThemes.get());
                    screen.parent = null;

                    Utils.screenToOpen = screen;
                    return SINGLE_SUCCESS;
                })
        );

        // View or change config settings
        builder.then(
            literal("config").then(
                argument("setting", SettingArgumentType.create())
                    .executes(context -> {
                        // Get setting value
                        Setting<?> setting = SettingArgumentType.get(context, Config.get().settings);

                        ChatUtils.infoPrefix("Config", "Setting (highlight)%s(default) is (highlight)%s(default).", setting.title, setting.get());

                        return SINGLE_SUCCESS;
                    }).suggests((ctx, suggestionsBuilder) ->
                        SettingArgumentType.listSuggestions(suggestionsBuilder, Config.get().settings)
                    )
                    .then(
                        argument("value", SettingValueArgumentType.create())
                            .executes(context -> {
                                // Set setting value
                                Setting<?> setting = SettingArgumentType.get(context, Config.get().settings);
                                String value = SettingValueArgumentType.get(context);

                                if (setting.parse(value)) {
                                    ChatUtils.infoPrefix("Config", "Setting (highlight)%s(default) changed to (highlight)%s(default).", setting.title, value);
                                }

                                return SINGLE_SUCCESS;
                            }).suggests((context, suggestionsBuilder) ->
                                SettingValueArgumentType.listSuggestions(context, suggestionsBuilder, Config.get().settings)
                            )
                    )
            )
        );

        // Open module screen
        builder.then(
            argument("module", ModuleArgumentType.create())
                .executes(context -> {
                    Module module = context.getArgument("module", Module.class);

                    WidgetScreen screen = GuiThemes.get().moduleScreen(module);
                    screen.parent = null;

                    Utils.screenToOpen = screen;
                    return SINGLE_SUCCESS;
                })
        );

        // View or change module settings
        builder.then(
            argument("module", ModuleArgumentType.create())
                .then(
                    argument("setting", SettingArgumentType.create())
                    .executes(context -> {
                        // Get setting value
                        Setting<?> setting = SettingArgumentType.get(context);

                        ModuleArgumentType.get(context).info("Setting (highlight)%s(default) is (highlight)%s(default).", setting.title, setting.get());

                        return SINGLE_SUCCESS;
                    })
                    .then(
                        argument("value", SettingValueArgumentType.create())
                            .executes(context -> {
                                // Set setting value
                                Setting<?> setting = SettingArgumentType.get(context);
                                String value = SettingValueArgumentType.get(context);

                                if (setting.parse(value)) {
                                    ModuleArgumentType.get(context).info("Setting (highlight)%s(default) changed to (highlight)%s(default).", setting.title, value);
                                }

                                return SINGLE_SUCCESS;
                            })
                    )
                )
        );
    }
}
